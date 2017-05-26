package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.murk.kwizgeeq.controller.EditQuizController;

import com.example.murk.kwizgeeq.databinding.ActivityEditQuizBinding;
import com.example.murk.kwizgeeq.view.*;
import com.example.murk.kwizgeeq.R;

import java.io.Serializable;
import java.util.ArrayList;

public class EditQuizActivity extends ListActivity {
    private EditQuizController controller;
    private EditQuizView view;
    private int quizIndex;
    private final int questionListRequestCode = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        //int index = getIntent().getIntExtra("quizIndex", 0);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Serializable quiz = bundle.getSerializable("quiz");
        quizIndex = intent.getIntExtra("quizIndex",0);


        ActivityEditQuizBinding binding;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_quiz);
        view = new EditQuizView(EditQuestionActivity.class, quiz, getListView(), this, this,
                questionListRequestCode, quizIndex);


        controller = new EditQuizController(view, quiz);
        binding.setController(controller);
        view.addObserver(controller);
    }

    @Override
    public void onBackPressed() {
        controller.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //controller.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == questionListRequestCode && resultCode == RESULT_OK){
            if(data.getSerializableExtra("questions")!=null){
                Serializable questions = data.getSerializableExtra("questions");
                controller.setQuestionList(questions);
            }
        }
    }


}