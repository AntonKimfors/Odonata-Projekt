package com.example.murk.kwizgeeq;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;

public class Questioneer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar); // (for Custom theme)
        setContentView(R.layout.activity_questioneer);

        UserQuiz testQuiz = new UserQuiz("Test Quiz",null);
        UserQuestion testQuestion1 = new UserQuestion("Question1?",null,null,null);
        UserQuestion testQuestion2 = new UserQuestion("Question2?",null,null,null);
        UserQuestion testQuestion3 = new UserQuestion("Question3?",null,null,null);
        Answer testCorrectAnswer = new Answer(true,"Right");
        Answer testWrongAnswer = new Answer(false,"Wrong");
        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion2);
        testQuiz.addQuestion(testQuestion3);
        testQuestion1.addAnswer(testCorrectAnswer);
        testQuestion2.addAnswer(testCorrectAnswer);
        testQuestion3.addAnswer(testCorrectAnswer);
        testQuestion1.addAnswer(testWrongAnswer);
        testQuestion2.addAnswer(testWrongAnswer);
        testQuestion3.addAnswer(testWrongAnswer);

        TextView quizLabel = (TextView) findViewById(R.id.quizLabel);
        quizLabel.setText(testQuiz.getName());
    }

    public void answerSelected(View view){
        Button answerButton = (Button) findViewById(view.getId());
        answerButton.setText("Pressed");
    }
}
