

package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;


import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;

import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;

import com.example.murk.kwizgeeq.view.QuizListView;

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
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int questionIndex, long id) {
                quizListView.openLongPressDialog(questionIndex);
                return true;
            }

        };
        view.setOnItemLongClickListener(itemLongClickListener);
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

