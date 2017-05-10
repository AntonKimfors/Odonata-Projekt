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

        UserQuiz quiz1 = new UserQuiz("Testquiz 1", new Color());
        KwizGeeQ.getInstance().quizzList.add(quiz1);
        UserQuestion question1 = new UserQuestion("Vad är svaret på fråga nummer 1?", null, 0, 0, null);
        question1.addAnswer(new Answer(true, "Sant"));
        question1.addAnswer(new Answer(false, "Falskt"));
        question1.addAnswer(new Answer(false, "Falskt"));
        question1.addAnswer(new Answer(false, "Falskt"));
        UserQuestion question2 = new UserQuestion("Vad är svaret på fråga nummer 2?", null, 0, 0, null);
        question2.addAnswer(new Answer(true, "Sant"));
        question2.addAnswer(new Answer(false, "Falskt"));
        question2.addAnswer(new Answer(false, "Falskt"));
        question2.addAnswer(new Answer(false, "Falskt"));
        UserQuestion question3 = new UserQuestion("Vad är svaret på fråga nummer 3?", null, 0, 0, null);
        question3.addAnswer(new Answer(true, "Sant"));
        question3.addAnswer(new Answer(false, "Falskt"));
        question3.addAnswer(new Answer(false, "Falskt"));
        question3.addAnswer(new Answer(false, "Falskt"));
        quiz1.addQuestion(question1);
        quiz1.addQuestion(question2);
        quiz1.addQuestion(question3);

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
