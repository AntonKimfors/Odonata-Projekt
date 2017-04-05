package com.example.murk.kwizgeeq.model;

import android.graphics.PointF;
import android.media.Image;

/**
 * Created by Henrik on 04/04/2017.
 */

public class UserQuestion extends Question {
    private String questionStr;
    private Image questionImg;
    private PointF point;

    public UserQuestion(String questionStr, Image questionImg, PointF point) {
        if(questionStr == null)
            questionStr = null;
        else
            this.questionStr = questionStr;

        if(questionImg == null)
            questionImg = null;
        else
            this.questionImg = questionImg;

        if(point == null)
            point = null;
        else
            this.point = point;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public Image getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(Image questionImg) {
        this.questionImg = questionImg;
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
