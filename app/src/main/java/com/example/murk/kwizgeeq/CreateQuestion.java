package com.example.murk.kwizgeeq;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.murk.kwizgeeq.model.*;

public class CreateQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_create_question);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        //TODO: get active quiz from KwizGeeQ
        final UserQuiz activeQuiz;

        Button nextQuestionButton = (Button) findViewById(R.id.nextQuestionButton);

        Button doneButton = (Button) findViewById(R.id.doneButton);

        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText questionText = (EditText) findViewById(R.id.questionText);
                String questionString = questionText.getText().toString();
                Question<String> question = new UserQuestion(questionString,null,null,null);

                EditText correctText = (EditText) findViewById(R.id.correctText);
                addStringAnswer(question,correctText,true);

                EditText wrongText1 = (EditText) findViewById(R.id.wrongText1);
                addStringAnswer(question,wrongText1,false);

                EditText wrongText2 = (EditText) findViewById(R.id.wrongText2);
                addStringAnswer(question,wrongText2,false);

                EditText wrongText3 = (EditText) findViewById(R.id.WrongText3);
                addStringAnswer(question,wrongText3,false);

                //TODO: add question to active quiz

                setContentView(R.layout.activity_create_question);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_quiz_list);
            }
        });

    }

    private void addStringAnswer(Question<String> question, EditText text, boolean isCorrect){
        String textString = text.getText().toString();
        if(!textString.equals("")){
            Answer<String> answer = new Answer<String>(isCorrect,textString);
            question.addAnswer(answer);
        }
    }
}
