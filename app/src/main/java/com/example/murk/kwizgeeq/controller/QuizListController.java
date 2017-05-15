package com.example.murk.kwizgeeq.controller;

import android.graphics.Color;
import android.graphics.PointF;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.QuizListView;
import com.google.gson.Gson;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListController implements Controller, Observer{

    private QuizListView view;
    private KwizGeeQ model;

    public QuizListController(QuizListView view){
        this.view = view;
        model = KwizGeeQ.getInstance();
        //int quiz = model.createUserQuiz("Testquiz",Color.argb(255,100,100,100));
        /*model.setUserQuestionText(quiz,0,"Vad är svaret på fråga nummer 1?");
        model.addTextAnswer(quiz,0,"sant",true);
        model.addTextAnswer(quiz,0,"falskt",false);
        model.addTextAnswer(quiz,0,"falskt",false);
        model.addTextAnswer(quiz,0,"falskt",false);
        model.setUserQuestionText(quiz,1,"Vad är svaret på fråga nummer 2?");
        model.addTextAnswer(quiz,1,"sant",true);
        model.addTextAnswer(quiz,1,"falskt",false);
        model.addTextAnswer(quiz,1,"falskt",false);
        model.addTextAnswer(quiz,1,"falskt",false);
        model.setUserQuestionText(quiz,2,"Vad är svaret på fråga nummer 3?");
        model.addTextAnswer(quiz,2,"sant",true);
        model.addTextAnswer(quiz,2,"falskt",false);
        model.addTextAnswer(quiz,2,"falskt",false);
        model.addTextAnswer(quiz,2,"falskt",false);*/

    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {
        //TODO: Try saving the data

        Gson gson = new Gson();



    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
