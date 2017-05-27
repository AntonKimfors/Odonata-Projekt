package com.example.murk.kwizgeeq.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.GlobalStatisticsController;
import com.example.murk.kwizgeeq.view.GlobalStatisticsView;

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
