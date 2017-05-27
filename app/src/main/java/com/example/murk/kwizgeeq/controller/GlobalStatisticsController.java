package com.example.murk.kwizgeeq.controller;

import android.app.Activity;

import com.example.murk.kwizgeeq.model.Statistics;
import com.example.murk.kwizgeeq.view.GlobalStatisticsView;

/**
 * Created by Are on 27/05/2017.
 */

public class GlobalStatisticsController {

    private GlobalStatisticsView view;
    private Activity activity;

    public GlobalStatisticsController(Activity activity, GlobalStatisticsView view) {
        this.activity = activity;
        this.view = view;
        view.setupStatistics((Statistics)activity.getIntent().getSerializableExtra("statistics"));
    }

}
