package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ {

    public static ArrayList<Quiz> quizzList;
    public static Quiz activeQuiz;
    public static int activeQuestionIndex;
    public static KwizGeeQ singletonInstance;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            activeQuestionIndex = 1;
            singletonInstance = new KwizGeeQ();
            quizzList = new ArrayList<Quiz>();
        }

        return singletonInstance;
    }

    public void addNewQuiz(String name, Color color){
        quizzList.add(new UserQuiz(name, color));
    };

    public void firePlayQuiz(Quiz quiz){
        activeQuiz = quiz;

    };

    public void fireEditQuiz(Quiz quiz){
        activeQuiz = quiz;
    };


}
