package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.example.murk.kwizgeeq.view.QuizListView;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity {

    public KwizGeeQDataSource mKwizGeeQDataSource; //TODO: Make it private
    private QuizListController controller;
    private QuizListView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_quiz_list);

        mKwizGeeQDataSource = new KwizGeeQDataSource(QuizListActivity.this);

        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab), mKwizGeeQDataSource);
        controller = new QuizListController(view, this, this);
        view.addObserver(controller);
        controller.onCreate();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mKwizGeeQDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mKwizGeeQDataSource.open();
    }
}
