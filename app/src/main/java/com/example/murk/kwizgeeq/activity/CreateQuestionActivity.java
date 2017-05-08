package com.example.murk.kwizgeeq.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.murk.kwizgeeq.*;
import com.example.murk.kwizgeeq.controller.*;
import com.example.murk.kwizgeeq.view.*;

/**
 * Created by Henrik on 05/05/2017.
 */

public class CreateQuestionActivity extends AppCompatActivity implements NavigatableActivity{

    private CreateQuestionView view;
    private CreateQuestionController controller;

    private int quizIndex;
    private int questionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_create_question);
        System.out.println("CreateQuestionActivity");
        quizIndex = getIntent().getIntExtra("quizIndex",0);
        questionIndex = getIntent().getIntExtra("questionIndex",0);

        view = new CreateQuestionView((EditText)findViewById(R.id.questionText),
                (EditText)findViewById(R.id.correctText),(EditText)findViewById(R.id.wrongText1),
                (EditText)findViewById(R.id.wrongText2),(EditText)findViewById(R.id.wrongText3));

        controller = new CreateQuestionController(view);
        controller.setTextFields(quizIndex,questionIndex);

        view.addObserver(controller);
    }

    public void mediaButtonAction(View view){
        //dispatchTakePictureIntent();
    }

    public void nextButtonAction(View view){
        controller.nextButtonAction(quizIndex,questionIndex,this, view.getContext());
    }

    public void doneButtonAction(View view){
        controller.doneButtonAction(quizIndex,questionIndex,this, view.getContext());
    }
}
