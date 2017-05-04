package com.example.murk.kwizgeeq.model;

/**
 * Created by Henrik on 04/04/2017.
 */

public class UserQuestion extends Question {
    private String questionStr;
    private String questionImg;
    private double xPosition;
    private double yPosition;
    private String audioFile;

    public UserQuestion(String questionStr, String questionImg, double xPosition, double yPosition, String audioFile) {
        this.questionStr = questionStr;
        this.questionImg = questionImg;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.audioFile = audioFile;
    }

    public String getQuestionStr() {
        return questionStr;
    }

    public void setQuestionStr(String questionStr) {
        this.questionStr = questionStr;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
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

            if(xPosition != o.xPosition){
                return false;
            }

            if(yPosition != yPosition){
                return false;
            }

            //all variables are equal
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Question: ").append(questionStr)
                .append(System.lineSeparator()).append(super.toString());
        return sb.toString();
    }
}
