package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.utils.KwizGeeQSQLiteHelper;
import com.example.murk.kwizgeeq.utils.QuizDataSource;
import com.example.murk.kwizgeeq.view.QuizListView;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity {

    private QuizListController controller;
    private QuizListView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_quiz_list);

        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab));
        controller = new QuizListController(view, this, this);
        view.addObserver(controller);
        controller.onCreate();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //controller.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
       // controller.onResume();


        try{
        KwizGeeQSQLiteHelper dataSource = new KwizGeeQSQLiteHelper(this);
        }catch (Exception e){

        }
    }
}
