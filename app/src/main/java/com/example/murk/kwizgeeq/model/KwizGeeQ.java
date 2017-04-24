package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.List;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ {

    public List<Quiz> quizzList;
    public Quiz activeQuiz;
    private static KwizGeeQ singletonInstance;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
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
