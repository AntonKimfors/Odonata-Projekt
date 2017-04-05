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
        if(obj instanceof UserQuestion && super.equals(obj)){
            UserQuestion o = (UserQuestion) obj;

            //check if Question string are equal
            if(questionStr!=null){
                if(o.getQuestionStr()!=null){
                    if(!questionStr.equals(o.getQuestionStr()))
                        return false;
                } else {
                    return false;
                }
            }

            //check if Question images are equal
            if(questionImg!=null){
                if(o.getQuestionImg()!=null){
                    if(!questionImg.equals(o.getQuestionImg()))
                        return false;
                } else {
                    return false;
                }
            }

            //check if Question points are equal
            if(point!=null){
                if(o.getPoint()!=null){
                    if(!point.equals(o.getPoint()))
                        return false;
                } else {
                    return false;
                }
            }

            //all variables are equal
            return true;
        }
        return false;
    }
}
