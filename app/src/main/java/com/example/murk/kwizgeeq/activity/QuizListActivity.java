package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.database.Cursor;

import android.databinding.DataBindingUtil;

import android.graphics.Color;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.databinding.ActivityEditQuizBinding;
import com.example.murk.kwizgeeq.databinding.ActivityQuizListBinding;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.example.murk.kwizgeeq.utils.KwizGeeQSQLiteHelper;
import com.example.murk.kwizgeeq.view.QuizListView;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity {

    public KwizGeeQDataSource mKwizGeeQDataSource; //TODO: Make it private. Take a look on Data for MVC!
    private QuizListController controller;
    private QuizListView view;
    //private ArrayList<String> mQuizNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);

        ActivityQuizListBinding binding;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz_list);


        mKwizGeeQDataSource = new KwizGeeQDataSource(QuizListActivity.this);

        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab), mKwizGeeQDataSource);
        controller = new QuizListController(view, this, this);

        binding.setController(controller);
        view.addObserver(controller);
        controller.onCreate();

    }


    //TODO: TROR KLAR - for sql i alla fall
    @Override
    protected void onPause() {
        super.onPause();

        ArrayList<Quiz> tmpQuizList= KwizGeeQ.getInstance().getQuizList();
        mKwizGeeQDataSource.open();
        int i = tmpQuizList.size();
        Quiz g = tmpQuizList.get(0);
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList();
        mKwizGeeQDataSource.close();
    }
    


}
