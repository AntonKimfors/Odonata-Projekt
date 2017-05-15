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

    private EditQuestionView editQuestionView;
    private UserQuestion userQuestion;

    private Context currentContext;
    private File imageStorageDir;

    private String imagePath;

    private int quizIndex;
    private int questionIndex;

    public EditQuestionController(EditQuestionView editQuestionView, Context currentContext,
                                  File imageStorageDir, int quizIndex, int questionIndex){
        this.editQuestionView = editQuestionView;
        this.currentContext = currentContext;
        this.imageStorageDir = imageStorageDir;
        this.quizIndex = quizIndex;
        this.questionIndex = questionIndex;

        getUserQuestion();
    }

    public void onCreate(){

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
    }

    private void getUserQuestion(){
        KwizGeeQ model = KwizGeeQ.getInstance();
        Quiz quiz = model.getQuiz(quizIndex);
        if(quiz.getSize()>=questionIndex){
            userQuestion = new UserQuestion();
            quiz.addQuestion(userQuestion);
        } else{
            Question question = quiz.getQuestion(questionIndex);
            if(question instanceof UserQuestion){
                userQuestion = (UserQuestion) question;
            }
        }
    }

    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public void nextButtonAction(View view){
        if(!checkRequiredFields()){
            editQuestionView.flashEmpty();
        } else{
            saveQuestion();
            int nextQuestionIndex = questionIndex +1;
            editQuestionView.addMoreQuestions(quizIndex,nextQuestionIndex);
        }
    }

    public void doneButtonAction(View view){
        if(isAllFieldsEmpty()){
            removeQuestion();
            editQuestionView.endAddOfQuestions();
        }
        else if(!checkRequiredFields()){
            editQuestionView.flashEmpty();
        } else{
            saveQuestion();
            editQuestionView.endAddOfQuestions();
        }
    }

    private void removeQuestion(){
        KwizGeeQ model = KwizGeeQ.getInstance();
        Quiz quiz = model.getQuiz(quizIndex);
        List<Question> questions = quiz.getQuestions();
        questions.remove(userQuestion);
    }

    public void mediaButtonAction(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        imagePath = imageUri.toString();

        editQuestionView.takePhoto(imageUri);
    }

    public void imageCreated(){
        if(imagePath!=null){
            userQuestion.setImagePath(imagePath);
        }
    }

    private boolean checkRequiredFields(){
        String questionText = editQuestionView.getQuestionString();
        String correctText = editQuestionView.getCorrectString();
        String wrong1text = editQuestionView.getWrong1String();
        String wrong2text = editQuestionView.getWrong2String();
        String wrong3text = editQuestionView.getWrong3String();

        //UserQuestion current = model.getQuestion()

        if(questionText.isEmpty() || correctText.isEmpty() || wrong1text.isEmpty() ||
                wrong2text.isEmpty() || wrong3text.isEmpty()){
            return false;
        }

        return true;
    }

    private boolean isAllFieldsEmpty(){
        String questionText = editQuestionView.getQuestionString();
        String correctText = editQuestionView.getCorrectString();
        String wrong1text = editQuestionView.getWrong1String();
        String wrong2text = editQuestionView.getWrong2String();
        String wrong3text = editQuestionView.getWrong3String();

        //UserQuestion current = model.getQuestion()

        if(questionText.isEmpty() && correctText.isEmpty() && wrong1text.isEmpty() &&
                wrong2text.isEmpty() && wrong3text.isEmpty()){
            return true;
        }

        return false;
    }

    private void saveQuestion(){
        userQuestion.setQuestionText(editQuestionView.getQuestionString());

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
