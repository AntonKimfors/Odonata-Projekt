package com.example.murk.kwizgeeq.controller;

import android.graphics.Color;
import android.graphics.PointF;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.QuizListView;

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

        UserQuiz quiz1 = new UserQuiz("Spsh", new Color());
        KwizGeeQ.getInstance().quizzList.add(quiz1);
        UserQuestion question1 = new UserQuestion("quantos anos tienes?", null, 4, 5.0, null);
        question1.addAnswer(new Answer(true, "4"));
        question1.addAnswer(new Answer(false, "42"));
        question1.addAnswer(new Answer(false, "24"));
        question1.addAnswer(new Answer(false, "2"));
        quiz1.addQuestion(question1);

    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

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
