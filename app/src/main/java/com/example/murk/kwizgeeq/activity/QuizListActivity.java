package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.databinding.ActivityQuizListBinding;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.example.murk.kwizgeeq.view.QuizListView;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity {

    public KwizGeeQDataSource mKwizGeeQDataSource;
    private QuizListController controller;
    private QuizListView view;
    private int editQuizRequestCode;
    private int createQuizRequestCode;

    //private ArrayList<String> mQuizNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);

        ActivityQuizListBinding binding;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz_list);


        mKwizGeeQDataSource = new KwizGeeQDataSource(QuizListActivity.this);

        editQuizRequestCode = 1;

        createQuizRequestCode = 2;


        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab),
                mKwizGeeQDataSource, editQuizRequestCode, createQuizRequestCode);
        controller = new QuizListController(view, this, this);

        binding.setController(controller);
        view.addObserver(controller);
        controller.onCreate();

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == createQuizRequestCode && requestCode == RESULT_OK){
            controller.addQuiz(data.getSerializableExtra("Quiz"));
        }

        if(requestCode == editQuizRequestCode && requestCode == RESULT_OK){
            controller.replaceQuiz(data.getSerializableExtra("Quiz"), data.getIntExtra("quizIndex",0));
        }
    }
    


}
