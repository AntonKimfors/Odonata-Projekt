package com.example.murk.kwizgeeq.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuestioneerController;
import com.example.murk.kwizgeeq.databinding.ActivityQuestioneerBinding;
import com.example.murk.kwizgeeq.view.QuestioneerView;

public class QuestioneerActivity extends AppCompatActivity{

    private QuestioneerController controller;
    private QuestioneerView view;
    private ActivityQuestioneerBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_questioneer);

        view = new QuestioneerView(this, getWindow(), (TextView)findViewById(R.id.quizLabel), (TextView)findViewById(R.id.questLabel), (TextView)findViewById(R.id.progressNumbers), (ProgressBar)findViewById(R.id.progressBar), (Button)findViewById(R.id.answerButton1), (Button)findViewById(R.id.answerButton2), (Button)findViewById(R.id.answerButton3), (Button)findViewById(R.id.answerButton4));
        controller = new QuestioneerController(this, view);
        controller.onCreate();
        controller.setSwitchActivityClass(StatisticsActivity.class);

        view.addObserver(controller);
        binding.setController(controller);
    }

    protected void onDestroy(){
        controller.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        view.showCloseQuizDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            controller.onActivityResult(requestCode, data);
        } else {
            finish();
        }
    }
}
