package com.example.murk.kwizgeeq.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.StatisticsController;
import com.example.murk.kwizgeeq.databinding.ActivityStatisticsViewBinding;
import com.example.murk.kwizgeeq.view.StatisticsView;

public class StatisticsActivity extends AppCompatActivity {

    private StatisticsController controller;
    private StatisticsView view;
    private ActivityStatisticsViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statistics_view);

        view = new StatisticsView((TextView)findViewById(R.id.quizLabel), (TextView)findViewById(R.id.answersLabel), (ProgressBar)findViewById(R.id.answersProgressBar), (Button)findViewById(R.id.retryIncorrectButton), (Button)findViewById(R.id.retryAllButton), (Button)findViewById(R.id.doneButton));
        controller = new StatisticsController(view, this, QuestioneerActivity.class);
        controller.onCreate();
        binding.setController(controller);
    }

    protected void onDestroy(){
        controller.onDestroy();
        super.onDestroy();
    }

}
