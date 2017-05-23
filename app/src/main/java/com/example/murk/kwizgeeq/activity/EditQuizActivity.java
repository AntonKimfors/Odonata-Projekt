package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.murk.kwizgeeq.controller.EditQuizController;

import com.example.murk.kwizgeeq.databinding.ActivityEditQuizBinding;

import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.*;
import com.example.murk.kwizgeeq.R;

import java.util.ArrayList;
import java.util.List;

public class EditQuizActivity extends ListActivity {
    private EditQuizController controller;
    private EditQuizView view;
    private final int questionListRequestCode = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        //int index = getIntent().getIntExtra("quizIndex", 0);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        UserQuiz quiz = (UserQuiz) bundle.getSerializable("quiz");


        ActivityEditQuizBinding binding;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_quiz);
        view = new EditQuizView(EditQuestionActivity.class, quiz, getListView(), this, this,
                questionListRequestCode);


        controller = new EditQuizController(view, quiz);
        binding.setController(controller);
        view.addObserver(controller);
        controller.onCreate();

    }

    @Override
    public void onBackPressed() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == questionListRequestCode && resultCode == RESULT_OK){
            if(data.getSerializableExtra("questions")!=null){
                ArrayList<Question> questions = (ArrayList)data.getSerializableExtra("questions");
                controller.setQuestionList(questions);
            }
        }
    }


}