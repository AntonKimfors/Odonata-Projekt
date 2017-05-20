

package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;


import com.example.murk.kwizgeeq.model.AnswerType;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.model.Statistics;
import com.example.murk.kwizgeeq.model.UserQuestion;

import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.FileUtilites;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.example.murk.kwizgeeq.utils.StorageUtils;

import com.example.murk.kwizgeeq.view.QuizListView;

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
    public final KwizGeeQDataSource mKwizGeeQDataSource;
    //private Activity currentActivity;

    public QuizListController(QuizListView view, Context context, Activity currentActivity){
        this.quizListView = view;
        this.context = context;
        this.currentActivity = currentActivity;
        model = KwizGeeQ.getInstance();
        mKwizGeeQDataSource = new KwizGeeQDataSource(context);


       /* UserQuiz testQuiz = new UserQuiz("Test Quiz 1", Color.BLUE);
        UserQuestion testQuestion1 = new UserQuestion();
        UserQuestion testQuestion2 = new UserQuestion();
        UserQuestion testQuestion3 = new UserQuestion();
        testQuestion1.setQuestionText("This is question number 1");
        testQuestion2.setQuestionText("This is question number 2");
        testQuestion3.setQuestionText("This is question number 3");
        testQuestion1.addAnswer("Correct Answer", true, AnswerType.TEXT);
        testQuestion1.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion1.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion1.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion2.addAnswer("Correct Answer", true, AnswerType.TEXT);
        testQuestion2.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion2.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion2.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion3.addAnswer("Correct Answer", true, AnswerType.TEXT);
        testQuestion3.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion3.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuestion3.addAnswer("Incorrect Answer", false, AnswerType.TEXT);
        testQuiz.addQuestion(testQuestion1);
        testQuiz.addQuestion(testQuestion2);
        testQuiz.addQuestion(testQuestion3);


        //model.getQuizList().add(testQuiz);
        //model.getQuizStatisticsList().add(new Statistics());

        model.getQuizList().add(testQuiz);
        model.getQuizStatisticsList().add(new Statistics());*/


    public void saveCurrentData(){
        ArrayList<Quiz> tmpQuizList= KwizGeeQ.getInstance().getQuizList();
        mKwizGeeQDataSource.open();
        int i = tmpQuizList.size();
        Quiz g = tmpQuizList.get(0);
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }

    public void readCurrentData(){
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList();
        mKwizGeeQDataSource.close();


        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int quizIndex, long id) {
                quizListView.changeView(quizIndex);
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

    }

    public void onClickAction(View view) {
        this.quizListView.fabPressed();
    }


    @Override
    public void onCreate() {

    }

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


}

