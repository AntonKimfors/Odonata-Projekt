package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.view.StatisticsView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 09/05/2017.
 */

public class StatisticsController implements Controller, Observer {

    private StatisticsView view;
    private KwizGeeQ model;
    private Activity currentActivity;
    private Class<? extends Activity> switchActivityClass;

    private int quizIndex;

    public StatisticsController(StatisticsView view, Activity currentActivity, Class<? extends Activity> switchActivityClass, int quizIndex) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = currentActivity;
        this.switchActivityClass = switchActivityClass;
        this.quizIndex = quizIndex;
    }

    public void onCreate() {
        view.updateStatistics(quizIndex);
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {

    }

    public void backSelected(View view){
        currentActivity.finish();
    }
    
    //TODO fix this
    public void retryAllSelected(View view){
        Intent intent = new Intent(currentActivity, switchActivityClass);
        intent.putExtra("quizIndex", quizIndex);
        currentActivity.startActivity(intent);
        //currentActivity.finish();
    }

    public void retryIncorrectSelected(View view){
        //TODO create and run new quiz with only incorrect questions in it
        currentActivity.finish();
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
