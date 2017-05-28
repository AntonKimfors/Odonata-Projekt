package com.kwizgeeq.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kwizgeeq.R;
import com.kwizgeeq.controller.StatisticsController;
import com.kwizgeeq.databinding.ActivityStatisticsViewBinding;
import com.kwizgeeq.view.StatisticsView;

/*
* @author Are Ehnberg
* revised by Marcus Olsson Lindvärn, Anton Kimfors, Henrik Håkansson
*/

public class StatisticsActivity extends AppCompatActivity {

    private StatisticsController controller;
    private StatisticsView view;
    private ActivityStatisticsViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics_view);

        view = new StatisticsView(this);
        controller = new StatisticsController(view, this);
        binding.setController(controller);
    }

}
