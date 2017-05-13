package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.murk.kwizgeeq.controller.EditQuizController;
import com.example.murk.kwizgeeq.view.*;
import com.example.murk.kwizgeeq.R;

public class EditQuizActivity extends ListActivity {


    private EditQuizController controller;
    private EditQuizView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_edit_quiz);
        int index = getIntent().getIntExtra("quizindex", 0);

        view = new EditQuizView(EditQuestionActivity.class,index, getListView(), this, this, (EditText) findViewById(R.id.etQuizName));
        controller = new EditQuizController(view);
        view.addObserver(controller);
        controller.onCreate();

    }
}