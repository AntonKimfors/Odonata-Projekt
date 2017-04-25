package com.example.murk.kwizgeeq;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.murk.kwizgeeq.model.*;

public class CreateQuestion extends AppCompatActivity {

    private KwizGeeQ kwizGeeQ;
    private UserQuiz activeQuiz;
    private int activeQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        kwizGeeQ = KwizGeeQ.getInstance();
        activeQuiz = (UserQuiz)kwizGeeQ.activeQuiz;
        activeQuestionIndex = kwizGeeQ.activeQuestionIndex;
    }

    public void nextButtonAction(View view){
        Question question = getQuestionFromInput();

        if(activeQuestionIndex > activeQuiz.getQuestions().size()){
            activeQuiz.addQuestion(question);
            kwizGeeQ.activeQuestionIndex++;
        }

        activeQuiz.addQuestion(question);

        addMoreQuestions();
    }

    public void doneButtonAction(View view){
        activeQuiz.addQuestion(getQuestionFromInput());
        toCreateQuiz();
    }

    private UserQuestion getQuestionFromInput() {
        EditText questionText = (EditText) findViewById(R.id.questionText);
        String questionString = questionText.getText().toString();
        UserQuestion question = new UserQuestion(questionString,null,null,null);

        EditText correctText = (EditText) findViewById(R.id.correctText);
        addStringAnswer(question,correctText,true);

        EditText wrongText1 = (EditText) findViewById(R.id.wrongText1);
        addStringAnswer(question,wrongText1,false);

        EditText wrongText2 = (EditText) findViewById(R.id.wrongText2);
        addStringAnswer(question,wrongText2,false);

        EditText wrongText3 = (EditText) findViewById(R.id.WrongText3);
        addStringAnswer(question,wrongText3,false);

        return question;
    }

    private void addStringAnswer(Question<String> question, EditText text, boolean isCorrect){
        String textString = text.getText().toString();
        if(!textString.equals("")){
            Answer<String> answer = new Answer(isCorrect,textString);
            question.addAnswer(answer);
        }
    }

    private void addMoreQuestions(){
        Intent intent = new Intent(CreateQuestion.this,CreateQuestion.class);
        startActivity(intent);
    }

    private void toCreateQuiz(){
        Intent intent = new Intent(CreateQuestion.this,CreateQuiz.class);
        startActivity(intent);
    }
}
