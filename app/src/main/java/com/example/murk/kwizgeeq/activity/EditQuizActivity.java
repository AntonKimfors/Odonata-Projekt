package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.EditText;

import com.example.murk.kwizgeeq.controller.EditQuizController;

import com.example.murk.kwizgeeq.databinding.ActivityEditQuizBinding;

import com.example.murk.kwizgeeq.view.*;
import com.example.murk.kwizgeeq.R;

import java.util.ArrayList;

public class EditQuizActivity extends ListActivity {


    private EditQuizController controller;
    private EditQuizView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);

        setContentView(R.layout.activity_edit_quiz);
        int index = getIntent().getIntExtra("quizIndex", 0);


        ActivityEditQuizBinding binding;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_quiz);
        view = new EditQuizView(EditQuestionActivity.class,index, getListView(), this, this, (EditText) findViewById(R.id.etQuizLabel),(FloatingActionButton) findViewById(R.id.fab),(Button) findViewById(R.id.btnColorPicker));




        controller = new EditQuizController(view,index);
        binding.setController(controller);
        view.addObserver(controller);
        controller.onCreate();

    }

    @Override
    public void onBackPressed(){
        controller.saveQuizName();
        view.reloadView();
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