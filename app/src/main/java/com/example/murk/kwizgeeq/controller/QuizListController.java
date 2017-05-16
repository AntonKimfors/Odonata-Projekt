

package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.AnswerType;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.Statistics;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.FileUtilites;
import com.example.murk.kwizgeeq.utils.StorageUtils;
import com.example.murk.kwizgeeq.view.QuizListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;



/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListController implements Controller, Observer{

    private QuizListView view;
    private KwizGeeQ model;
    private Context context;
    //private Activity currentActivity;

    public QuizListController(QuizListView view, Context context, Activity currentActivity){
        this.view = view;
        this.context = context;
        //this.currentActivity = currentActivity;
        model = KwizGeeQ.getInstance();

        UserQuiz testQuiz = new UserQuiz("Test Quiz 1", Color.BLUE);
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
        model.getQuizList().add(testQuiz);
        model.getQuizStatisticsList().add(new Statistics());

    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume(){

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }


}

