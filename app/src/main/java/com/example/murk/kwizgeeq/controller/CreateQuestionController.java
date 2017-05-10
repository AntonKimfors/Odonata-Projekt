package com.example.murk.kwizgeeq.controller;

import android.view.View;

import com.example.murk.kwizgeeq.view.CreateQuestionView;
import com.example.murk.kwizgeeq.model.*;

import java.util.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class CreateQuestionController implements Controller, Observer{

    private CreateQuestionView createQuestionView;
    private KwizGeeQ model;

    private int quizIndex;
    private int questionIndex;

    public CreateQuestionController(CreateQuestionView createQuestionView, int quizIndex, int questionIndex){
        this.createQuestionView = createQuestionView;
        this.quizIndex = quizIndex;
        this.questionIndex = questionIndex;
        model = KwizGeeQ.getInstance();
    }

    public void onCreate(){
        if(questionIndex < model.getQuizSize(quizIndex)){
            createQuestionView.setTextFields(quizIndex,questionIndex);
        }

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
        createQuestionView.takePhoto();
    }

    public void imageCreated(String imagePath){
        model.setUserQuestionImagePath(quizIndex,questionIndex,imagePath);
    }

    private boolean checkRequiredFields(){
        String questionText = createQuestionView.getQuestionString();
        String correctText = createQuestionView.getCorrectString();
        String wrong1text = createQuestionView.getWrong1String();
        String wrong2text = createQuestionView.getWrong2String();
        String wrong3text = createQuestionView.getWrong3String();

        if(questionText.isEmpty() || correctText.isEmpty() || wrong1text.isEmpty() ||
                wrong2text.isEmpty() || wrong3text.isEmpty()){
            return false;
        }

        return true;
    }

    private void saveQuestion(){
        model.setUserQuestionText(quizIndex, questionIndex, createQuestionView.getQuestionString());

        model.addTextAnswer(quizIndex,questionIndex, createQuestionView.getCorrectString(),true);

        model.addTextAnswer(quizIndex,questionIndex, createQuestionView.getWrong1String(),false);
        model.addTextAnswer(quizIndex,questionIndex, createQuestionView.getWrong2String(),false);
        model.addTextAnswer(quizIndex,questionIndex, createQuestionView.getWrong3String(),false);
    }

    @Override
    public void update(Observable o, Object arg) {
        return;
    }
}
