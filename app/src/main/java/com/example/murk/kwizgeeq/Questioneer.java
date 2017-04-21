package com.example.murk.kwizgeeq;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;

import java.util.Iterator;
import java.util.Timer;

public class Questioneer extends AppCompatActivity {

    private TextView quizLabel;
    private TextView questNumLabel;
    private TextView questLabel;
    private TextView progressNumbers;
    private ProgressBar progressBar;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;

    //Temporary variables
    private int curQuest;
    private int totQuest;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Answer answer4;
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
        answerButton1 = (Button) findViewById(R.id.answerButton1);
        answerButton2 = (Button) findViewById(R.id.answerButton2);
        answerButton3 = (Button) findViewById(R.id.answerButton3);
        answerButton4 = (Button) findViewById(R.id.answerButton4);

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
        flashAnswer(selectedButton, Color.GREEN); // TODO insert answer check and select green or red
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        new CountDownTimer(2250, 2250){
            public void onTick(long l){

            }
            public void onFinish(){
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                endQuestion();
            }
        }.start();

    }

    private void endQuestion(){
        if(curQuest == totQuest) {
            finishQuiz();
        }
        else {
            updateQuestioneer();
        }
    }

    private void flashAnswer(Button button, int color) {
        ColorDrawable f1 = new ColorDrawable(color);
        ColorDrawable f2 = new ColorDrawable(Color.parseColor("#ffd6d7d7"));
        AnimationDrawable a = new AnimationDrawable();
        a.addFrame(f2, 250);
        a.addFrame(f1, 250);
        a.addFrame(f2, 250);
        a.addFrame(f1, 250);
        a.addFrame(f2, 250);
        a.addFrame(f1, 1000);
        a.addFrame(f2, 0);
        a.setOneShot(true);
        button.setBackground(a);
        a.start();
    }

    private void updateQuestioneer(){
        //Method will use activeQuiz from KwizGeeQ when it is finished instead of testQuiz
        curQuest++;
        Iterator answerIterator = testQuiz.getQuestions().get(curQuest-1).answerIterator(true);
        questNumLabel.setText("Question " + curQuest);
        questLabel.setText(((UserQuestion)testQuiz.getQuestions().get(curQuest-1)).getQuestionStr());
        progressNumbers.setText(curQuest + " / " + totQuest);
        progressBar.setProgress(curQuest);
        answer1 = (Answer)answerIterator.next();
        answer2 = (Answer)answerIterator.next();
        answer3 = (Answer)answerIterator.next();
        answer4 = (Answer)answerIterator.next();
        answerButton1.setText((String)answer1.getData());
        answerButton2.setText((String)answer2.getData());
        answerButton3.setText((String)answer3.getData());
        answerButton4.setText((String)answer4.getData());
    }

    private void finishQuiz(){
        Intent intent = new Intent(this, QuizList.class);
        startActivity(intent);

        //TODO Add statistics and change to that view instead
    }
}
