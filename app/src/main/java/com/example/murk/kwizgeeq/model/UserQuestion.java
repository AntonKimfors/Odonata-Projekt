package com.example.murk.kwizgeeq.model;

import android.graphics.PointF;
import android.media.Image;

/**
 * Created by Henrik on 04/04/2017.
 */

public class UserQuestion extends Question {
    private String questionStr;
    private Image questionImg;
    private PointF position;
    private String audioFile;

    public UserQuestion(String questionStr, Image questionImg, PointF position, String audioFile) {
        this.questionStr = questionStr;

        this.questionImg = questionImg;

        this.position = position;

        this.audioFile = audioFile;
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

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UserQuestion && super.equals(obj)){
            UserQuestion o = (UserQuestion) obj;

            if(questionStr!=null){
                if(o.getQuestionStr()!=null){
                    if(!questionStr.equals(o.getQuestionStr()))
                        return false;
                } else {
                    return false;
                }
            }

            if(questionImg!=null){
                if(o.getQuestionImg()!=null){
                    if(!questionImg.equals(o.getQuestionImg()))
                        return false;
                } else {
                    return false;
                }
            }

            if(position !=null){
                if(o.getPosition()!=null){
                    if(!position.equals(o.getPosition()))
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
    @Override
    public String toString(){
        return questionStr;
    }
}
