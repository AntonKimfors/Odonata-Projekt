package com.example.murk.kwizgeeq;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;

import java.util.Iterator;

public class Questioneer extends AppCompatActivity {

    private int curQuest;
    private int totQuest;

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
        testQuestion1.addAnswer(testWrongAnswer);
        testQuestion1.addAnswer(testWrongAnswer);
        testQuestion2.addAnswer(testWrongAnswer);
        testQuestion2.addAnswer(testWrongAnswer);
        testQuestion2.addAnswer(testWrongAnswer);
        testQuestion3.addAnswer(testWrongAnswer);
        testQuestion3.addAnswer(testWrongAnswer);
        testQuestion3.addAnswer(testWrongAnswer);

        curQuest = 0;
        totQuest = testQuiz.getQuestions().size();
        updateQuestioneer(testQuiz);
    }

    public void answerSelected(View view){
        Button answerButton = (Button) findViewById(view.getId());
        answerButton.setText("Pressed");
    }

    public void updateQuestioneer(Quiz quiz){
        curQuest++;
        Iterator answerIterator = ((UserQuestion)quiz.getQuestions().get(curQuest)).answerIterator(true);

        TextView quizLabel = (TextView) findViewById(R.id.quizLabel);
        TextView questNumLabel = (TextView) findViewById(R.id.questNumLabel);
        TextView questLabel = (TextView) findViewById(R.id.questLabel);
        TextView progressNumbers = (TextView) findViewById(R.id.progressNumbers);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Button answer1 = (Button) findViewById(R.id.answerButton1);
        Button answer2 = (Button) findViewById(R.id.answerButton2);
        Button answer3 = (Button) findViewById(R.id.answerButton3);
        Button answer4 = (Button) findViewById(R.id.answerButton4);

        quizLabel.setText(quiz.getName());
        questNumLabel.setText("Question " + curQuest);
        questLabel.setText(((UserQuestion)quiz.getQuestions().get(curQuest)).getQuestionStr());
        progressNumbers.setText(curQuest + " / " + totQuest);
        progressBar.setMax(totQuest);
        progressBar.setProgress(curQuest);
        answer1.setText((String)((Answer)answerIterator.next()).getData());
        answer2.setText((String)((Answer)answerIterator.next()).getData());
        answer3.setText((String)((Answer)answerIterator.next()).getData());
        answer4.setText((String)((Answer)answerIterator.next()).getData());
    }
}
