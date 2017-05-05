package com.example.murk.kwizgeeq.controller;

import android.content.Context;

import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.view.CreateQuestionView;
import com.example.murk.kwizgeeq.model.*;

import java.util.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class CreateQuestionController implements Controller, Observer{

    private CreateQuestionView view;
    private KwizGeeQ model;

    public CreateQuestionController(CreateQuestionView view){
        this.view = view;
        model = KwizGeeQ.getInstance();
    }

    public void setTextFields(int quizIndex, int questionIndex) {
        if(questionIndex < model.getQuizSize(quizIndex)){
            view.setTextFields(quizIndex,questionIndex);
        }
    }

    public void onCreate(){

    }

    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public void nextButtonAction(int quizIndex, int questionIndex, NavigatableActivity oldActivity,
                                 Context context){
        saveQuestion(quizIndex,questionIndex);
        view.addMoreQuestions(quizIndex,questionIndex++,oldActivity,context);
    }

    public void doneButtonAction(int quizIndex, int questionIndex, NavigatableActivity oldActivity,
                                 Context context){
        saveQuestion(quizIndex, questionIndex);
        view.endAddOfQuestions(oldActivity,context);
    }

    private void saveQuestion(int quizIndex, int questionIndex){
        model.createUserQuestion(quizIndex, questionIndex, view.getQuestionString(),null,0,0,null);

        addStringAnswer(quizIndex,questionIndex,view.getCorrectString(),true);

        addStringAnswer(quizIndex,questionIndex,view.getWrong1String(),false);
        addStringAnswer(quizIndex,questionIndex,view.getWrong2String(),false);
        addStringAnswer(quizIndex,questionIndex,view.getWrong3String(),false);
    }

    private void addStringAnswer(int quizIndex, int questionIndex, String answerStr,
                                 boolean isCorrect){
        if(!answerStr.equals("")){
            Answer<String> answer = new Answer(isCorrect,answerStr);
            model.addAnswer(quizIndex,questionIndex,answer);
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
