package com.example.murk.kwizgeeq.controller;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.example.murk.kwizgeeq.utils.ImageFileHandler;
import com.example.murk.kwizgeeq.view.CreateQuestionView;
import com.example.murk.kwizgeeq.model.*;

import java.io.File;
import java.util.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class CreateQuestionController implements Controller, Observer{

    private CreateQuestionView createQuestionView;
    private UserQuestion userQuestion;

    private Context currentContext;
    private File imageStorageDir;

    private String imagePath;

    private int quizIndex;
    private int questionIndex;

    public CreateQuestionController(CreateQuestionView createQuestionView, Context currentContext,
                                    File imageStorageDir, int quizIndex, int questionIndex){
        this.createQuestionView = createQuestionView;
        this.currentContext = currentContext;
        this.imageStorageDir = imageStorageDir;
        this.quizIndex = quizIndex;
        this.questionIndex = questionIndex;
        userQuestion = (UserQuestion)KwizGeeQ.getInstance().getQuestion(quizIndex,questionIndex);
    }

    public void onCreate(){

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    createQuestionView.highlightField(v);
                } else {
                    createQuestionView.normalizeField(v);
                }

            }
        };

        createQuestionView.addOnFocusChangeListener(onFocusChangeListener);
    }

    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public void nextButtonAction(View view){
        if(!checkRequiredFields()){
            createQuestionView.flashEmpty();
        } else{
            saveQuestion();
            int nextQuestionIndex = questionIndex +1;
            createQuestionView.addMoreQuestions(quizIndex,nextQuestionIndex);
        }
    }

    public void doneButtonAction(View view){
        if(!checkRequiredFields()){
            createQuestionView.flashEmpty();
        } else{
            saveQuestion();
            createQuestionView.endAddOfQuestions();
        }
    }

    public void mediaButtonAction(View view){
        Uri imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);
        imagePath = imageUri.toString();

        createQuestionView.takePhoto(imageUri);
    }

    public void imageCreated(){
        if(imagePath!=null){
            userQuestion.setImagePath(imagePath);
        }
    }

    private boolean checkRequiredFields(){
        String questionText = createQuestionView.getQuestionString();
        String correctText = createQuestionView.getCorrectString();
        String wrong1text = createQuestionView.getWrong1String();
        String wrong2text = createQuestionView.getWrong2String();
        String wrong3text = createQuestionView.getWrong3String();

        //UserQuestion current = model.getQuestion()

        if(questionText.isEmpty() || correctText.isEmpty() || wrong1text.isEmpty() ||
                wrong2text.isEmpty() || wrong3text.isEmpty()){
            return false;
        }

        return true;
    }

    private void saveQuestion(){
        userQuestion.setQuestionText(createQuestionView.getQuestionString());

        userQuestion.clearAnswers();
        userQuestion.addAnswer(createQuestionView.getCorrectString(),true);

        userQuestion.addAnswer(createQuestionView.getWrong1String(),false);
        userQuestion.addAnswer(createQuestionView.getWrong2String(),false);
        userQuestion.addAnswer(createQuestionView.getWrong3String(),false);
    }

    @Override
    public void update(Observable o, Object arg) {
        return;
    }
}
