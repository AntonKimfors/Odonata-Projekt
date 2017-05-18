package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.AnswerType;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuestion;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Observable;

/**
 * Created by Are on 04/05/2017.
 */

public class QuestioneerView extends Observable{

    private Window window;
    private TextView quizLabel;
    private TextView questLabel;
    private TextView progressNumbers;
    private ImageView questImage;
    private ProgressBar progressBar;
    private Button answerButton1;
    private Button answerButton2;
    private Button answerButton3;
    private Button answerButton4;

    private KwizGeeQ model;
    private Activity activity;

    public QuestioneerView(Activity activity, Window window, TextView quizLabel, TextView questLabel, TextView progressNumbers, ImageView questImage, ProgressBar progressBar, Button answerButton1, Button answerButton2, Button answerButton3, Button answerButton4) {
        this.window = window;
        this.quizLabel = quizLabel;
        this.questLabel = questLabel;
        this.progressNumbers = progressNumbers;
        this.questImage = questImage;
        this.progressBar = progressBar;
        this.answerButton1 = answerButton1;
        this.answerButton2 = answerButton2;
        this.answerButton3 = answerButton3;
        this.answerButton4 = answerButton4;
        this.model = KwizGeeQ.getInstance();
        this.activity = activity;
    }

    public void flashCorrectAnswer(View view) {
        flashAnswer(view, activity.getResources().getColor(R.color.colorCorrectAnswer, null));
    }

    public void flashIncorrectAnswer(View view) {
        flashAnswer(view, activity.getResources().getColor(R.color.colorIncorrectAnswer, null));
    }

    private void flashAnswer(View view, int color) {
        ColorDrawable f1 = new ColorDrawable(color);
        ColorDrawable f2 = new ColorDrawable(activity.getResources().getColor(R.color.colorQuestioneerButton, null));
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

    public void updateQuizRelatedItems(String quizName, int quizSize){
        quizLabel.setText(quizName);
        progressBar.setMax(quizSize);
    }

    public void updateQuestioneer(int quizIndex, int questionIndex, int currentQuestion, int quizSize){
        UserQuestion question = (UserQuestion)(model.getQuiz(quizIndex).getQuestion(questionIndex));
        Iterator answerIterator = question.answerIterator(true);

        progressNumbers.setText(currentQuestion + " / " + quizSize);
        progressBar.setProgress(currentQuestion);
        questLabel.setText(question.toString());
        if (question.getImagePath() != null){
            try {
                InputStream is = activity.getContentResolver().openInputStream(Uri.parse(question.getImagePath()));
                questImage.setImageDrawable(Drawable.createFromStream(is, question.getImagePath()));
            } catch (FileNotFoundException e) {
                questImage.setImageAlpha(0);
            }
        }
        updateAnswerButtons(answerIterator);
    }

    private void updateAnswerButtons(Iterator answerIterator){
        for(int i = 1; i <= 4; i++){
            Answer answer = (Answer)answerIterator.next();
            switch (i) {
                case 1: answerButton1.setTag(answer);
                case 2: answerButton2.setTag(answer);
                case 3: answerButton3.setTag(answer);
                case 4: answerButton4.setTag(answer);
            }

            if(answer.getAnswerType() == AnswerType.TEXT) {
                switch (i) {
                    case 1:
                        answerButton1.setText(answer.getData());
                    case 2:
                        answerButton2.setText(answer.getData());
                    case 3:
                        answerButton3.setText(answer.getData());
                    case 4:
                        answerButton4.setText(answer.getData());
                }
            } else if (answer.getAnswerType() == AnswerType.IMAGE) {
                try {
                    InputStream is = activity.getContentResolver().openInputStream(Uri.parse(answer.getData()));
                    switch (i) {
                        case 1: answerButton1.setBackground(Drawable.createFromStream(is, answer.getData()));
                        case 2: answerButton2.setBackground(Drawable.createFromStream(is, answer.getData()));
                        case 3: answerButton3.setBackground(Drawable.createFromStream(is, answer.getData()));
                        case 4: answerButton4.setBackground(Drawable.createFromStream(is, answer.getData()));
                    }
                } catch (FileNotFoundException e) {
                    switch (i) {
                        case 1: answerButton1.setText("IMAGE NOT FOUND");
                        case 2: answerButton2.setText("IMAGE NOT FOUND");
                        case 3: answerButton3.setText("IMAGE NOT FOUND");
                        case 4: answerButton4.setText("IMAGE NOT FOUND");
                    }
                }
            }
        }
    }

    public void closeQuestioneer(){
        activity.finish();
    }

    public void showCloseQuizDialog(){
        new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing UserQuiz")
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
