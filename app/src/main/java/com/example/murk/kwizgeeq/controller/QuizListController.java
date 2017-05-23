

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

public class QuizListController implements Controller, Observer{

    private QuizListView quizListView;
    private KwizGeeQ model;
    private Context context;
    private Activity currentActivity;
    private List<UserQuiz> quizList;
    public static KwizGeeQDataSource mKwizGeeQDataSource;
    //private Activity currentActivity;

    public QuizListController(QuizListView view, Context context, Activity currentActivity) {
        this.quizListView = view;
        this.context = context;
        this.currentActivity = currentActivity;
        model = KwizGeeQ.getInstance();
        quizList = model.getUserQuizList();
        mKwizGeeQDataSource = new KwizGeeQDataSource(context);

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int quizIndex, long id) {
                quizListView.startQuestioneer(quizIndex);
            }
        };

        view.setOnListItemClickedListener(onItemClickListener);

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int quizIndex, long id) {
                quizListView.showAlertDialog();
                quizListView.setAlertDialogPositiveListener(getPositiveListener(quizIndex));
                quizListView.setAlertDialogNegativeListener(getNegativeListener(quizIndex));
                quizListView.setAlertDialogNeutralListener(getDismissListener());

                return true;
            }

        };
        view.setOnItemLongClickListener(itemLongClickListener);

        View.OnClickListener createQuizListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quizTitle = quizListView.getQuizName();
                int color = quizListView.getmSelectedColor();
                UserQuiz newQuiz = new UserQuiz(quizTitle, color);
                quizListView.createNewQuiz(newQuiz);
            }
        };
        quizListView.setmCreateQuizOnClickListener(createQuizListener);

        ColorPickerSwatch.OnColorSelectedListener colorPickerListener =
                new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                quizListView.setmSelectedColor(color);
                quizListView.updatePickColorBackground();
            }

        };
        quizListView.setColorPickerListener(colorPickerListener);

        View.OnClickListener mCancelListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                quizListView.dismissDialog();
            }
        };
        quizListView.setmCancelOnClickListener(mCancelListener);

        View.OnClickListener mPickColorListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                quizListView.showColorDialog();
            }
        };
        quizListView.setmPickColorListener(mPickColorListener);


    };


    public static void saveCurrentData() {
        ArrayList<UserQuiz> tmpQuizList = KwizGeeQ.getInstance().getUserQuizList();
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }

    public static void readCurrentData() {
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList();
        mKwizGeeQDataSource.close();
    }

    private DialogInterface.OnClickListener getPositiveListener(final int quizIndex){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizListView.editQuiz(quizList.get(quizIndex),quizIndex);
            }
        };
    }

    private DialogInterface.OnClickListener getNegativeListener(final int quizIndex){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizList.remove(quizIndex);
            }
        };
    }

    private DialogInterface.OnClickListener getDismissListener(){
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quizListView.dismissDialog();
            }
        };
    }

    public void onClickAction(View view) {
        this.quizListView.fabPressed();
    }


    @Override
    public void onCreate() {

    }

    //TODO: Spara onClick istället??
    @Override
    public void onPause() {
        saveCurrentData();
    }

    @Override
    public void onResume() {
        readCurrentData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void addQuiz(Serializable quiz) {
        if(quiz instanceof UserQuiz){
            quizList.add((UserQuiz)quiz);
        }
    }

    public void replaceQuiz(Serializable quiz, int quizIndex) {
        if(quiz instanceof UserQuiz){
            quizList.set(quizIndex,(UserQuiz)quiz);
        }
    }
}

