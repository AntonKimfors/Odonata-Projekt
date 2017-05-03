package com.example.murk.kwizgeeq;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.murk.kwizgeeq.model.*;
import com.wrapper.spotify.models.User;

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

    public void nextButtonAction(View view){
        saveQuestion();
        addMoreQuestions();
    }

    public void doneButtonAction(View view){
        saveQuestion();
        endAddOfQuestions();
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

    private void saveQuestion() {
        EditText questionText = (EditText) findViewById(R.id.questionText);
        String questionString = questionText.getText().toString();

        presenter.createQuestion(questionString);

        EditText correctText = (EditText) findViewById(R.id.correctText);
        presenter.addStringAnswer(correctText.getText().toString(),true);

        EditText wrongText1 = (EditText) findViewById(R.id.wrongText1);
        presenter.addStringAnswer(wrongText1.getText().toString(),false);

        EditText wrongText2 = (EditText) findViewById(R.id.wrongText2);
        presenter.addStringAnswer(wrongText2.getText().toString(),false);

        EditText wrongText3 = (EditText) findViewById(R.id.WrongText3);
        presenter.addStringAnswer(wrongText3.getText().toString(),false);
    }

    public void addMoreQuestions(){
        Intent intent = new Intent(CreateQuestionView.this,CreateQuestionView.class);

        if(presenter.getCurrent()!=null){
            intent.putExtra("selectedQuiz",getQuiz());
        }

        startActivity(intent);
    }

    public void endAddOfQuestions(){
        Intent intent = new Intent(CreateQuestionView.this,CreateQuiz.class);

        if(presenter.getCurrent()!=null){
            intent.putExtra("selectedQuiz",getQuiz());
        }

        startActivity(intent);
    }
}
