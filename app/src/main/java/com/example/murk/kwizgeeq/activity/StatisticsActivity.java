package com.example.murk.kwizgeeq.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.StatisticsController;
import com.example.murk.kwizgeeq.view.StatisticsView;

public class StatisticsActivity extends AppCompatActivity {

    private StatisticsController controller;
    private StatisticsView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_view);

        view = new StatisticsView((TextView)findViewById(R.id.quizLabel), (TextView)findViewById(R.id.answersLabel), (ProgressBar)findViewById(R.id.answersProgressBar));
        controller = new StatisticsController(view, this, getIntent().getIntExtra("quizIndex", 0));
        controller.onCreate();
    }
}
