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

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;

import java.util.Iterator;
import java.util.Observable;

/**
 * Created by Are on 04/05/2017.
 */

public class QuestioneerView extends Observable{

    private Window window;
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
    private Activity activity;


    public QuestioneerView(Activity activity, Window window, TextView quizLabel, TextView questNumLabel, TextView questLabel, TextView progressNumbers, ProgressBar progressBar, Button answerButton1, Button answerButton2, Button answerButton3, Button answerButton4) {
        this.window = window;
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
        this.activity = activity;
    }

    public void flashCorrectAnswer(View view) {
        flashAnswer(view, Color.GREEN);
    }

    public void flashIncorrectAnswer(View view) {
        flashAnswer(view, Color.RED);
    }

    private void flashAnswer(View view, int color) {
        ColorDrawable f1 = new ColorDrawable(color);
        ColorDrawable f2 = new ColorDrawable(Color.parseColor("#ffd6d7d7")); //TODO change to use background colour
        AnimationDrawable a = new AnimationDrawable();
        a.addFrame(f2, 250);
        a.addFrame(f1, 250);
        a.addFrame(f2, 250);
        a.addFrame(f1, 1000);
        a.addFrame(f2, 0);
        a.setOneShot(true);
        view.setBackground(a);
        a.start();
        waitForFlashAnimation();
    }

    private void waitForFlashAnimation(){
        setWindowUntouchable(true);
        new CountDownTimer(2250, 2250){
            public void onTick(long l){

            }
            public void onFinish(){
                setWindowUntouchable(false);
                setChanged();
                notifyObservers("question done");
            }
        }.start();
    }

    private void setWindowUntouchable(boolean untouchable){
        if(untouchable){
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void updateQuizRelatedItems(int quizIndex){
        quizLabel.setText(model.getQuiz(quizIndex).getName());
        progressBar.setMax(model.getQuiz(quizIndex).getQuestions().size());
    }

    public void updateQuestioneer(int quizIndex, int questionIndex){
        Question question = model.getQuiz(quizIndex).getQuestion(questionIndex);
        Iterator answerIterator = question.answerIterator(true);

        questNumLabel.setText("Question " + (questionIndex + 1));
        questLabel.setText(question.toString());
        progressNumbers.setText((questionIndex + 1) + " / " + model.getQuiz(quizIndex).getQuestions().size());
        progressBar.setProgress((questionIndex + 1));
        answerButton1.setTag(answerIterator.next());
        answerButton2.setTag(answerIterator.next());
        answerButton3.setTag(answerIterator.next());
        answerButton4.setTag(answerIterator.next());
        answerButton1.setText((String)((Answer)answerButton1.getTag()).getData());
        answerButton2.setText((String)((Answer)answerButton2.getTag()).getData());
        answerButton3.setText((String)((Answer)answerButton3.getTag()).getData());
        answerButton4.setText((String)((Answer)answerButton4.getTag()).getData());
    }

    public void closeQuestioneer(){
        activity.finish();
    }

    public void showCloseQuizDialog(){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Quiz")
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeQuestioneer();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
