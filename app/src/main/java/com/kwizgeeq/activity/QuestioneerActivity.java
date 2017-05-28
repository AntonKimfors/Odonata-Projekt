package com.kwizgeeq.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kwizgeeq.R;
import com.kwizgeeq.controller.QuestioneerController;
import com.kwizgeeq.databinding.ActivityQuestioneerBinding;
import com.kwizgeeq.view.QuestioneerView;

/*
* @author Are Ehnberg
* revised by Marcus Olsson Lindvärn, Anton Kimfors, Henrik Håkansson
* */

public class QuestioneerActivity extends AppCompatActivity{

    private QuestioneerController controller;
    private QuestioneerView view;
    private ActivityQuestioneerBinding binding;

    private int statisticsRequestCode = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questioneer);
        view = new QuestioneerView(this);
        controller = new QuestioneerController(this, view, statisticsRequestCode);
        controller.setUpQuestioneer();
        controller.setSwitchActivityClass(StatisticsActivity.class);
        view.addObserver(controller);
        binding.setController(controller);
    }

    @Override
    public void onBackPressed(){
        controller.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == statisticsRequestCode) {
            if (resultCode == RESULT_OK) {
                controller.replayResult(data);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

}
