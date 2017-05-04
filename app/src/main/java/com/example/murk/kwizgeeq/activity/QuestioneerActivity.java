package com.example.murk.kwizgeeq.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuestioneerController;
import com.example.murk.kwizgeeq.view.QuestioneerView;

public class QuestioneerActivity extends AppCompatActivity implements NavigatableActivity{

    private QuestioneerController controller;
    private QuestioneerView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_questioneer);

        view = new QuestioneerView((TextView)findViewById(R.id.quizLabel), (TextView)findViewById(R.id.questNumLabel), (TextView)findViewById(R.id.questLabel), (TextView)findViewById(R.id.progressNumbers), (ProgressBar)findViewById(R.id.progressBar), (Button)findViewById(R.id.answerButton1), (Button)findViewById(R.id.answerButton2), (Button)findViewById(R.id.answerButton3), (Button)findViewById(R.id.answerButton4));
        controller = new QuestioneerController(view);
        view.addObserver(controller);
        controller.onCreate();
    }

    protected void onDestroy(){
        controller.onDestroy();
        super.onDestroy();
    }

    public void answerSelected(View view){
        controller.answerSelected(view, getWindow(), this);
    }

    @Override
    public void onBackPressed(){
        view.onBackPressed(this);
    }
}
