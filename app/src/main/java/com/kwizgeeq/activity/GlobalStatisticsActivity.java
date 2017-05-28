package com.kwizgeeq.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kwizgeeq.R;
import com.kwizgeeq.controller.GlobalStatisticsController;
import com.kwizgeeq.view.GlobalStatisticsView;

/*
* @author Are Ehnberg
* revised by Marcus Olsson Lindvärn, Anton Kimfors, Henrik Håkansson
*/

public class GlobalStatisticsActivity extends AppCompatActivity {

    private GlobalStatisticsController controller;
    private GlobalStatisticsView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_statistics);

        view = new GlobalStatisticsView(this);
        controller = new GlobalStatisticsController(this, view);
    }

}
