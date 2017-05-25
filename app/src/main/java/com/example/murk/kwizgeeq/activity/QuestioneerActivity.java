package com.example.murk.kwizgeeq.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuestioneerController;
import com.example.murk.kwizgeeq.databinding.ActivityQuestioneerBinding;
import com.example.murk.kwizgeeq.view.QuestioneerView;

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

    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        view.showCloseQuizDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == statisticsRequestCode) {
            if (resultCode == RESULT_OK) {
                controller.replayResult(data);
            } else {
                setResult(RESULT_OK, data);
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        controller.onResume();
    }

}
