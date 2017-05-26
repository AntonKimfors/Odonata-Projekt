package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.databinding.ActivityQuizListBinding;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.example.murk.kwizgeeq.view.QuizListView;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity {

    public KwizGeeQDataSource mKwizGeeQDataSource;
    private QuizListController controller;
    private QuizListView view;

    private int editQuizRequestCode = 1;
    private int createQuizRequestCode = 2;
    private int questioneerRequestCode = 3;

    //private ArrayList<String> mQuizNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);

        ActivityQuizListBinding binding;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz_list);


        //mKwizGeeQDataSource = new KwizGeeQDataSource(QuizListActivity.this);

        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab),
                editQuizRequestCode, createQuizRequestCode, questioneerRequestCode);
        controller = new QuizListController(view, this, this);

        binding.setController(controller);
        view.addObserver(controller);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == createQuizRequestCode && resultCode == RESULT_OK){
            controller.addQuiz(data.getSerializableExtra("quiz"));
        }

        if(requestCode == editQuizRequestCode && resultCode == RESULT_OK){
            controller.replaceQuiz(data.getSerializableExtra("quiz"), data.getIntExtra("quizIndex",0));
        }

        if(requestCode == questioneerRequestCode && resultCode == RESULT_OK) {
            controller.replaceQuiz(data.getSerializableExtra("quiz"), data.getIntExtra("quizIndex",0));
            controller.updateGlobalStatistics(data.getSerializableExtra("quiz"));
        }
    }
    


}
