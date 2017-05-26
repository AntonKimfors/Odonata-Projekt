

package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;


import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;

import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;

import com.example.murk.kwizgeeq.view.QuizListView;

import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;



/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListController implements Observer{

    private final AdapterView.OnItemLongClickListener itemLongClickListener;
    private final View.OnClickListener createQuizListener;
    private final ColorPickerSwatch.OnColorSelectedListener colorPickerListener;
    private final View.OnClickListener mCancelListener;
    private final View.OnClickListener mPickColorListener;

    private QuizListView quizListView;
    private KwizGeeQ kwizGeeQ;
    private Context context;
    private Activity currentActivity;
    private List<UserQuiz> quizList;
    public static KwizGeeQDataSource mKwizGeeQDataSource;
    //private Activity currentActivity;

    public QuizListController(QuizListView view, Context context, Activity currentActivity) {
        this.kwizGeeQ = new KwizGeeQ();
        this.quizListView = view;
        this.context = context;
        this.currentActivity = currentActivity;
        quizList = kwizGeeQ.getUserQuizList();
        mKwizGeeQDataSource = new KwizGeeQDataSource(context);

        quizListView.setQuestions((ArrayList)quizList);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int quizIndex, long id) {
                quizListView.startQuestioneer(quizList.get(quizIndex), quizIndex);
            }
        };

        view.setOnListItemClickedListener(onItemClickListener);

        itemLongClickListener = new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int quizIndex, long id) {
                quizListView.setAlertDialogPositiveListener(getPositiveListener(quizIndex));
                quizListView.setAlertDialogNegativeListener(getNegativeListener(quizIndex));
                quizListView.setAlertDialogNeutralListener(getDismissListener());
                quizListView.showAlertDialog();

                return true;
            }

        };
        view.setOnItemLongClickListener(itemLongClickListener);

        createQuizListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quizTitle = quizListView.getQuizName();
                int color = quizListView.getmSelectedColor();
                UserQuiz newQuiz = new UserQuiz(quizTitle, color);
                quizListView.createNewQuiz(newQuiz);
                quizListView.dismissCreationDialog();
            }
        };
        quizListView.setmCreateQuizOnClickListener(createQuizListener);

        colorPickerListener = new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                quizListView.setmSelectedColor(color);
                quizListView.updatePickColorBackground();
            }

        };
        quizListView.setColorPickerListener(colorPickerListener);

        mCancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizListView.dismissCreationDialog();
            }
        };
        quizListView.setmCancelOnClickListener(mCancelListener);

        mPickColorListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                quizListView.showColorDialog();
            }
        };
        quizListView.setmPickColorListener(mPickColorListener);


    };


    public void saveCurrentData() {
        ArrayList<UserQuiz> tmpQuizList = new ArrayList<>(quizList);
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }

    public void readCurrentData() {
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList();
        mKwizGeeQDataSource.close();
    }

    private DialogInterface.OnClickListener getPositiveListener(final int quizIndex){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizListView.editQuiz(quizList.get(quizIndex),quizIndex);
                quizListView.dismissAlertDialog();
            }
        };
    }

    private DialogInterface.OnClickListener getNegativeListener(final int quizIndex){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kwizGeeQ.removeQuiz(quizIndex);
            }
        };
    }

    private DialogInterface.OnClickListener getDismissListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizListView.dismissAlertDialog();
            }
        };
    }

    public void onClickAction(View view) {
        this.quizListView.fabPressed();
    }

    //TODO: Spara onClick istället??
    public void onPause() {
        saveCurrentData();
    }

    public void onResume() {
        readCurrentData();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void addQuiz(Serializable quiz) {
        if(quiz instanceof UserQuiz){
            kwizGeeQ.addQuiz((UserQuiz)quiz);
        }
    }

    public void replaceQuiz(Serializable quiz, int quizIndex) {
        if(quiz instanceof UserQuiz){
            kwizGeeQ.replaceQuiz(quizIndex,(UserQuiz)quiz);
        }
    }

    public void updateGlobalStatistics(Serializable quiz){
        if (quiz instanceof UserQuiz){
            kwizGeeQ.updateGlobalStatistics((UserQuiz)quiz);
        }
    }
}

