package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.StatisticsView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 09/05/2017.
 */

public class StatisticsController implements Observer {

    private StatisticsView view;
    private Activity currentActivity;

    private UserQuiz quiz;

    public StatisticsController(StatisticsView view, Activity currentActivity) {
        this.view = view;
        this.currentActivity = currentActivity;
        this.quiz = (UserQuiz) currentActivity.getIntent().getSerializableExtra("quiz");
        if (currentActivity.getIntent().getBooleanExtra("allCorrect", false)) {
            view.disableReplayByIndexButton();
        }
        view.updateStatistics(quiz);
    }

    public void backSelected(View view){
        currentActivity.setResult(currentActivity.RESULT_CANCELED);
        currentActivity.finish();
    }

    public void retryAllSelected(View view){
        retry(false);
    }

    public void retryIncorrectSelected(View view){
        retry(true);
    }

    private void retry(boolean onlyIncorrect){
        Intent intent = currentActivity.getIntent();
        intent.putExtra("replayByIndex", onlyIncorrect);
        currentActivity.setResult(currentActivity.RESULT_OK, intent);
        currentActivity.finish();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
