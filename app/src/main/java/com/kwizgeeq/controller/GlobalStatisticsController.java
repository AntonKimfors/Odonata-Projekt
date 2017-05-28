package com.kwizgeeq.controller;

import android.app.Activity;

import com.kwizgeeq.model.Statistics;
import com.kwizgeeq.view.GlobalStatisticsView;

/**
 * Created by Are on 27/05/2017.
 *
 * * @author Are Ehnberg
 * revised by Marcus Olsson Lindvärn, Anton Kimfors, Henrik Håkansson
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
