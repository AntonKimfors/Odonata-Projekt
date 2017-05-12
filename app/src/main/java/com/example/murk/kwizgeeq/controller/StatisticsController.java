package com.example.murk.kwizgeeq.controller;

import android.app.Activity;

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

    private int quizIndex;

    public StatisticsController(StatisticsView view, Activity currentActivity, int quizIndex) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = currentActivity;
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

    @Override
    public void update(Observable o, Object arg) {

    }
}
