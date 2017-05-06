package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;

import java.util.Iterator;
import java.util.Observable;

/**
 * Created by Are on 04/05/2017.
 */

public class QuestioneerView extends Observable{

    private TextView quizLabel;
    private TextView questNumLabel;
    private TextView questLabel;
    private TextView progressNumbers;
    private ProgressBar progressBar;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;

    private KwizGeeQ model;


    public QuestioneerView(TextView quizLabel, TextView questNumLabel, TextView questLabel, TextView progressNumbers, ProgressBar progressBar, Button answerButton1, Button answerButton2, Button answerButton3, Button answerButton4) {
        this.quizLabel = quizLabel;
        this.questNumLabel = questNumLabel;
        this.questLabel = questLabel;
        this.progressNumbers = progressNumbers;
        this.progressBar = progressBar;
        this.answerButton1 = answerButton1;
        this.answerButton2 = answerButton2;
        this.answerButton3 = answerButton3;
        this.answerButton4 = answerButton4;
        this.model = KwizGeeQ.getInstance();
    }

    public void flashCorrectAnswer(View view, Window window, NavigatableActivity activity) {
        flashAnswer(view, window, activity, Color.GREEN);
    }

    public void flashIncorrectAnswer(View view, Window window, NavigatableActivity activity) {
        flashAnswer(view, window, activity, Color.RED);
    }

    private void flashAnswer(View view, final Window window, final NavigatableActivity activity, int color) {
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
        setUntouchable(window, true);
        new CountDownTimer(2250, 2250){
            public void onTick(long l){

            }
            public void onFinish(){
                setUntouchable(window, false);
                setChanged();
                notifyObservers(activity);
            }
        }.start();
    }

    private void setUntouchable(Window window, boolean untouchable){
        if(untouchable){
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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

    public void destroyActivity(NavigatableActivity activity){
        activity.finish();
    }

    public void onBackPressed(final NavigatableActivity activity){
        new AlertDialog.Builder((Activity) activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Quiz")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        destroyActivity(activity);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}