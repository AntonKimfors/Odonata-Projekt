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
    }

    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public void nextButtonAction(View view){
        saveQuestion();
        int nextQuestionIndex = questionIndex +1;
        createQuestionView.addMoreQuestions(quizIndex,nextQuestionIndex);
    }

    public void doneButtonAction(View view){
        saveQuestion();
        createQuestionView.endAddOfQuestions();
    }

    public void mediaButtonAction(View view){
        createQuestionView.takePhoto();
    }

    private void saveQuestion(){
        model.createUserQuestion(quizIndex, questionIndex, createQuestionView.getQuestionString(),null,0,0,null);

        model.addStringAnswer(quizIndex,questionIndex, createQuestionView.getCorrectString(),true);

        model.addStringAnswer(quizIndex,questionIndex, createQuestionView.getWrong1String(),false);
        model.addStringAnswer(quizIndex,questionIndex, createQuestionView.getWrong2String(),false);
        model.addStringAnswer(quizIndex,questionIndex, createQuestionView.getWrong3String(),false);
    }

    @Override
    public void update(Observable o, Object arg) {
        return;
    }
}
