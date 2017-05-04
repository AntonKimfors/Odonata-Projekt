package com.example.murk.kwizgeeq;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.murk.kwizgeeq.model.*;
import com.wrapper.spotify.models.User;

import java.util.Iterator;
import java.util.List;

public class CreateQuestionView extends AppCompatActivity {

    private CreateQuestionPresenter presenter;

    public CreateQuestionView(){
        presenter = new CreateQuestionPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_question);

        presenter.onCreate();
    }


    public void setQuestionString(String s){
        EditText questionText = (EditText) findViewById(R.id.questionText);
        questionText.setText(s, TextView.BufferType.EDITABLE);
    }

    public void setCorrectStringAnswer(String correctString){
        EditText correct = (EditText) findViewById(R.id.correctText);
        correct.setText(correctString, TextView.BufferType.EDITABLE);
    }

    public void setWrongStringAnswer(String wrongString){
        EditText wrong1 = (EditText) findViewById(R.id.wrongText1);
        EditText wrong2 = (EditText) findViewById(R.id.wrongText2);
        EditText wrong3 = (EditText) findViewById(R.id.wrongText2);

        if(wrong1.getText().toString().equals("")){
            wrong1.setText(wrongString, TextView.BufferType.EDITABLE);
        } else if(wrong2.getText().toString().equals("")){
            wrong2.setText(wrongString, TextView.BufferType.EDITABLE);
        } else if(wrong3.getText().toString().equals("")){
            wrong3.setText(wrongString, TextView.BufferType.EDITABLE);
        }
    }


    public void mediaButtonAction(View view){

    }

    public void nextButtonAction(View view){
        presenter.nextButtonAction();
    }

    public void doneButtonAction(View view){
        presenter.doneButtonAction();
    }

    /**
     * Så här tänkte jag först att det skulle se ut om vi använder Serializable
     * @return
     */
    public UserQuiz getQuiz(){
        return (UserQuiz)getIntent().getSerializableExtra("selectedQuiz");
    }

    /**
     * Så här skulle man göra om vi skickar runt index
     * @return
     */
    public int getQuizIndex(){
        return getIntent().getIntExtra("quizIndex",0);
    }

    public int getQuestionIndex(){
        return getIntent().getIntExtra("questionIndex",0);
    }


    public String getQuestionString(){
        EditText questionText = (EditText) findViewById(R.id.questionText);
        return questionText.getText().toString();
    }

    public String getCorrectString(){
        EditText correctText = (EditText) findViewById(R.id.correctText);
        return correctText.getText().toString();
    }

    public String getWrong1String(){
        EditText wrongText1 = (EditText) findViewById(R.id.wrongText1);
        return wrongText1.getText().toString();
    }

    public String getWrong2String(){
        EditText wrongText2 = (EditText) findViewById(R.id.wrongText2);
        return wrongText2.getText().toString();
    }

    public String getWrong3String(){
        EditText wrongText3 = (EditText) findViewById(R.id.WrongText3);
        return wrongText3.getText().toString();
    }


    public void addMoreQuestions(int quizIndex, int questionIndex){
        Intent intent = new Intent(CreateQuestionView.this,CreateQuestionView.class);
        intent.putExtra("quizIndex",quizIndex);
        intent.putExtra("questionIndex",questionIndex);
        startActivity(intent);
    }

    public void endAddOfQuestions(){
        Intent intent = new Intent(CreateQuestionView.this,QuizList.class);
        startActivity(intent);
    }
}
