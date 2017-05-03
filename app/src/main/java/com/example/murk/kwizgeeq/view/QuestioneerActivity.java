package com.example.murk.kwizgeeq.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.presenter.QuestioneerPresenter;

import java.util.Iterator;

public class QuestioneerActivity extends AppCompatActivity implements QuestioneerView{

    private TextView quizLabel;
    private TextView questNumLabel;
    private TextView questLabel;
    private TextView progressNumbers;
    private ProgressBar progressBar;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;

    private QuestioneerPresenter presenter; //TODO perhaps switch to observable instead?

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
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

        presenter = new QuestioneerPresenter(this);
        presenter.onCreate();
    }

    protected void onDestroy(){
        super.onDestroy();
        presenter.onDestroy();
    }

    public void answerSelected(View view){
        presenter.answerSelected(view);
    }

    public void flashCorrectAnswer(View view) {
        flashAnswer(view, Color.GREEN);
    }

    public void flashIncorrectAnswer(View view) {
        flashAnswer(view, Color.RED);
    }

    private void flashAnswer(View view, int color) {
        ColorDrawable f1 = new ColorDrawable(color);
        ColorDrawable f2 = new ColorDrawable(Color.parseColor("#ffd6d7d7"));
        AnimationDrawable a = new AnimationDrawable();
        a.addFrame(f2, 250);
        a.addFrame(f1, 250);
        a.addFrame(f2, 250);
        a.addFrame(f1, 1000);
        a.addFrame(f2, 0);
        a.setOneShot(true);
        view.setBackground(a);
        a.start();
        setUntouchable(true);
        new CountDownTimer(2250, 2250){
            public void onTick(long l){

            }
            public void onFinish(){
                setUntouchable(false);
                presenter.finishQuestion();
            }
        }.start();
    }

    private void setUntouchable(boolean untouchable){
        if(untouchable){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void updateQuestioneer(Quiz quiz, int currentQuestion, int totalQuestions){
        Iterator answerIterator = quiz.getQuestions().get(currentQuestion-1).answerIterator(true);
        quizLabel.setText(quiz.getName());
        questNumLabel.setText("Question " + currentQuestion);
        questLabel.setText(((UserQuestion)quiz.getQuestions().get(currentQuestion-1)).getQuestionStr());
        progressNumbers.setText(currentQuestion + " / " + totalQuestions);
        progressBar.setMax(totalQuestions);
        progressBar.setProgress(currentQuestion);
        answerButton1.setTag(answerIterator.next());
        answerButton2.setTag(answerIterator.next());
        answerButton3.setTag(answerIterator.next());
        answerButton4.setTag(answerIterator.next());
        answerButton1.setText((String)((Answer)answerButton1.getTag()).getData());
        answerButton2.setText((String)((Answer)answerButton2.getTag()).getData());
        answerButton3.setText((String)((Answer)answerButton3.getTag()).getData());
        answerButton4.setText((String)((Answer)answerButton4.getTag()).getData());
    }

    public void finishQuiz(){
        finish();
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Quiz")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
