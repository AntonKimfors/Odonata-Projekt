package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.os.Bundle;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.view.QuizListView;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity implements NavigatableActivity{

    private QuizListController controller;
    private QuizListView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_quiz_list);

        view = new QuizListView(getListView(), this,this);
        controller = new QuizListController(view);
        view.addObserver(controller);
        controller.onCreate();

    }


}
