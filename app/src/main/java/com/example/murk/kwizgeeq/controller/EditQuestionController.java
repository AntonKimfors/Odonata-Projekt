package com.example.murk.kwizgeeq.controller;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CompoundButton;

import com.example.murk.kwizgeeq.utils.ImageFileHandler;
import com.example.murk.kwizgeeq.view.EditQuestionView;
import com.example.murk.kwizgeeq.model.*;

import java.io.File;
import java.util.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class EditQuestionController implements Controller, Observer{

    private final EditQuestionView editQuestionView;
    private UserQuestion userQuestion;

    private Context currentContext;
    private File imageStorageDir;

    private String imagePath;

    private String correctImagePath;
    private String wrong1ImagePath;
    private String wrong2ImagePath;
    private String wrong3ImagePath;


    private int quizIndex;
    private int questionIndex;

    public EditQuestionController(final EditQuestionView editQuestionView, Context currentContext,
                                  File imageStorageDir, int quizIndex, int questionIndex){
        this.editQuestionView = editQuestionView;
        this.currentContext = currentContext;
        this.imageStorageDir = imageStorageDir;
        this.quizIndex = quizIndex;
        this.questionIndex = questionIndex;

        getUserQuestion();

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    editQuestionView.highlightField(v);
                } else {
                    editQuestionView.normalizeField(v);
                }

            }
        };

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editQuestionView.changeAnswerView(isChecked);
            }
        };

        editQuestionView.addOnFocusChangeListener(onFocusChangeListener);
        editQuestionView.setOnCheckedChangeListener(onCheckedChangeListener);
        editQuestionView.setUserQuestion(userQuestion);
    }

    public void onCreate(){


    }

    private void getUserQuestion(){
        KwizGeeQ model = KwizGeeQ.getInstance();
        UserQuiz userQuiz = model.getQuiz(quizIndex);
        if(userQuiz.getSize()>=questionIndex){
            userQuestion = new UserQuestion();
            userQuiz.addQuestion(userQuestion);
        } else{
            Question question = userQuiz.getQuestion(questionIndex);
            if(question instanceof UserQuestion){
                userQuestion = (UserQuestion) question;
            }
        }
    }

    //TODO: Anpassa vad som spara
    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public void nextButtonAction(View view){
        if(!checkQuestionData() || (!editQuestionView.isSwitchChecked() && !checkTextAnswers())
        ||(editQuestionView.isSwitchChecked() && !checkImageAnswers())){
                editQuestionView.flashEmpty();
        } else{
            saveQuestionText();
            if(!editQuestionView.isSwitchChecked()){
                saveTextAnswers();
            }
            int nextQuestionIndex = questionIndex +1;
            editQuestionView.addMoreQuestions(quizIndex,nextQuestionIndex);
        }
    }

    public void doneButtonAction(View view){
        if(isAllFieldsEmpty()){
            removeQuestion();
            removeQuizIfEmpty();
            editQuestionView.endAddOfQuestions();
        }
        else if(!checkQuestionData() || (!editQuestionView.isSwitchChecked() && !checkTextAnswers())
                ||(editQuestionView.isSwitchChecked() && !checkImageAnswers())){
            editQuestionView.flashEmpty();
        } else{
            saveQuestionText();
            if(!editQuestionView.isSwitchChecked()){
                saveTextAnswers();
            }
            editQuestionView.endAddOfQuestions();
        }
    }

    private void removeQuizIfEmpty(){
        KwizGeeQ model = KwizGeeQ.getInstance();
        UserQuiz userQuiz = model.getQuiz(quizIndex);
        if(userQuiz.getSize()==0){
            model.removeQuiz(userQuiz);
        }
    }

    private void removeQuestion(){
        KwizGeeQ model = KwizGeeQ.getInstance();
        UserQuiz userQuiz = model.getQuiz(quizIndex);
        List<Question> questions = userQuiz.getQuestions();
        questions.remove(userQuestion);
    }

    public void mediaButtonAction(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        imagePath = imageUri.toString();

        editQuestionView.takePhoto(imageUri);
    }

    public void correctImageButton(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        correctImagePath = imageUri.toString();

        editQuestionView.takePhoto(imageUri);
    }

    public void wrong1ImageButton(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        wrong1ImagePath = imageUri.toString();

        editQuestionView.takePhoto(imageUri);
    }

    public void wrong2ImageButton(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        wrong2ImagePath = imageUri.toString();

        editQuestionView.takePhoto(imageUri);
    }

    public void wrong3ImageButton(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        wrong3ImagePath = imageUri.toString();

        editQuestionView.takePhoto(imageUri);
    }

    public void imageCreated(){
        if(imagePath!=null){

            if(imagePath!=null){
                userQuestion.setImagePath(imagePath);
            }

            userQuestion.clearAnswers();

            if(correctImagePath!=null){
                userQuestion.addAnswer(correctImagePath,true,AnswerType.IMAGE);
            }

            if(wrong1ImagePath!=null){
                userQuestion.addAnswer(wrong1ImagePath,true,AnswerType.IMAGE);
            }

            if(wrong2ImagePath!=null){
                userQuestion.addAnswer(wrong2ImagePath,true,AnswerType.IMAGE);
            }

            if(wrong3ImagePath!=null){
                userQuestion.addAnswer(wrong3ImagePath,true,AnswerType.IMAGE);
            }
        }
    }

    private boolean checkImageAnswers(){
        return userQuestion.checkNumberOfAnswers(4);
    }

    private boolean checkTextAnswers(){
        String correctText = editQuestionView.getCorrectString();
        String wrong1text = editQuestionView.getWrong1String();
        String wrong2text = editQuestionView.getWrong2String();
        String wrong3text = editQuestionView.getWrong3String();

        if(!correctText.isEmpty() && !wrong1text.isEmpty() && !wrong2text.isEmpty() &&
                !wrong3text.isEmpty()){
            return true;
        }

        return false;
    }

    private boolean checkQuestionData(){
        String questionText = editQuestionView.getQuestionString();
        if(userQuestion.getImagePath()!= null || !questionText.isEmpty()){
            return true;
        }
        return false;
    }

    private boolean isAllFieldsEmpty(){
        String questionText = editQuestionView.getQuestionString();

        if(!editQuestionView.isSwitchChecked()){
            String correctText = editQuestionView.getCorrectString();
            String wrong1text = editQuestionView.getWrong1String();
            String wrong2text = editQuestionView.getWrong2String();
            String wrong3text = editQuestionView.getWrong3String();

            if(questionText.isEmpty() && userQuestion.getImagePath() == null
                    && correctText.isEmpty() && wrong1text.isEmpty() &&
                    wrong2text.isEmpty() && wrong3text.isEmpty()){
                return true;
            }

            return false;
        } else {
            if(questionText.isEmpty() && userQuestion.getImagePath() == null &&
                    userQuestion.checkNumberOfAnswers(0)){
                return true;
            }
            return false;
        }


    }

    private void saveQuestionText(){
        userQuestion.setQuestionText(editQuestionView.getQuestionString());
    }

    private void saveTextAnswers(){
            userQuestion.clearAnswers();
            userQuestion.addAnswer(editQuestionView.getCorrectString(),true,AnswerType.TEXT);

            userQuestion.addAnswer(editQuestionView.getWrong1String(),false,AnswerType.TEXT);
            userQuestion.addAnswer(editQuestionView.getWrong2String(),false,AnswerType.TEXT);
            userQuestion.addAnswer(editQuestionView.getWrong3String(),false,AnswerType.TEXT);
    }

    @Override
    public void update(Observable o, Object arg) {
        return;
    }
}
