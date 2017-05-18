package com.example.murk.kwizgeeq.controller;

import android.graphics.Color;
import android.view.View;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.EditQuizView;
import com.example.murk.kwizgeeq.view.QuizListView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Murk on 2017-05-06.
 */

public class EditQuizController implements Controller, Observer {

    private EditQuizView view;
    private KwizGeeQ model;

    public EditQuizController(EditQuizView view){
        this.view = view;
        model = KwizGeeQ.getInstance();

    }



    @Override
    public void onCreate() {

    }
    public void onClickAction(View view){
        this.view.fabPressed();
    }

    //TODO: Anpassa vad som spara
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


