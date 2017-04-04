package com.example.murk.kwizgeeq.model;


import android.graphics.PointF;
import android.media.Image;

/**
 * Created by Henrik on 04/04/2017.
 */

public class QuestionFactory {
    private static QuestionFactory INSTANCE = new QuestionFactory();

    private  QuestionFactory(){}

    public QuestionFactory getInstance(){
        return INSTANCE;
    }

    public Question createUserQuestion(String questionStr, Image questionImg, PointF point){
        return new UserQuestion();
    }

    public Question createSpotifyQuestion(){
        return null;
    }

}
