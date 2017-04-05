package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.List;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ {

    public List<Quiz> quizzList;
    public Quiz activeQuiz;


    public void addNewQuiz(Color color){};

    public void firePlayQuiz(Quiz quiz){};

    public void fireEditQuiz(Quiz quiz){};
}
