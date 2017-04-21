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

    private TextView quizLabel;
    private TextView questNumLabel;
    private TextView questLabel;
    private TextView progressNumbers;
    private ProgressBar progressBar;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;

    //Temporary variables
    private int curQuest;
    private int totQuest;
    private UserQuiz testQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar); // (for Custom theme)
        setContentView(R.layout.activity_questioneer);

        quizLabel = (TextView) findViewById(R.id.quizLabel);
        questNumLabel = (TextView) findViewById(R.id.questNumLabel);
        questLabel = (TextView) findViewById(R.id.questLabel);
        progressNumbers = (TextView) findViewById(R.id.progressNumbers);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        answer1 = (Button) findViewById(R.id.answerButton1);
        answer2 = (Button) findViewById(R.id.answerButton2);
        answer3 = (Button) findViewById(R.id.answerButton3);
        answer4 = (Button) findViewById(R.id.answerButton4);

        //Temporary variables
        testQuiz = new UserQuiz("Test Quiz",null);
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
        progressBar.setMax(totQuest);
        quizLabel.setText(testQuiz.getName());

        updateQuestioneer();
    }

    public void answerSelected(View view){
        Button selectedButton = (Button)view;
        if(curQuest == totQuest) {
            finishQuiz();
        }
        else {
            updateQuestioneer();
        }
    }

    private void updateQuestioneer(){
        //Method will use activeQuiz from KwizGeeQ when it is finished instead of testQuiz
        curQuest++;
        Iterator answerIterator = testQuiz.getQuestions().get(curQuest-1).answerIterator(true);
        questNumLabel.setText("Question " + curQuest);
        questLabel.setText(((UserQuestion)testQuiz.getQuestions().get(curQuest-1)).getQuestionStr());
        progressNumbers.setText(curQuest + " / " + totQuest);
        progressBar.setProgress(curQuest);
        answer1.setText((String)((Answer)answerIterator.next()).getData());
        answer2.setText((String)((Answer)answerIterator.next()).getData());
        answer3.setText((String)((Answer)answerIterator.next()).getData());
        answer4.setText((String)((Answer)answerIterator.next()).getData());
    }

    private void finishQuiz(){
        //TODO
    }
}
