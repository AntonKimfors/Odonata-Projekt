package com.example.murk.kwizgeeq.model;

import com.example.murk.kwizgeeq.events.EventBusWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 */

public class Question implements Serializable {

    private final List<Answer> answers;
    private int wrongAnswerCount;
    private int correctAnswerCount;
    transient com.google.common.eventbus.EventBus eventBus;
    private String questionText;
    private String imagePath;
    private double xPosition;
    private double yPosition;
    private String audioPath;

    public Question() {
        eventBus = EventBusWrapper.BUS;
        answers = new ArrayList<>();

        wrongAnswerCount = 0;

        correctAnswerCount = 0;
    }

    public Iterator<Answer> answerIterator(boolean shuffled) {
        List<Answer> answerList = new ArrayList<>(answers);
        if(shuffled){
            Collections.shuffle(answerList);
        }

        return answerList.iterator();
    }


    public void addAnswer(String data, boolean isCorrect, AnswerType answerType){
        Answer answer = new Answer(isCorrect,data,answerType);
        if(answer == null)
            throw new NullPointerException();

        if(answers.add(answer)){
            if(answer.isCorrect())
                correctAnswerCount++;
            else
                wrongAnswerCount++;
        }

        if(answerType == AnswerType.IMAGE){
            eventBus.post(this);
        }
    }

    public void removeAnswer(Answer answer){
        if(answer == null)
            throw new NullPointerException();

        if(answers.remove(answer)){
            if(answer.isCorrect())
                correctAnswerCount--;
            else
                wrongAnswerCount--;

        }

        throw new IllegalArgumentException("Element not in list");
    }


    public void clearAnswers(){
        correctAnswerCount = 0;
        wrongAnswerCount = 0;
        answers.clear();
    }

    public boolean checkNumberOfCorrect(int excepted){
        return excepted == correctAnswerCount;
    }

    public boolean checkNumberOfWrong(int expected){
        return expected == wrongAnswerCount;
    }

    public boolean checkNumberOfAnswers(int excepted){
        return excepted == wrongAnswerCount + correctAnswerCount;
    }

    public ArrayList<Answer> getOrderedList(){
        //Return a array list of the answers with the correct answer at index 0
        ArrayList<Answer> newOrderedList = new ArrayList<Answer>();
        //TODO: put the correct answer at index 0;
        newOrderedList = new ArrayList<Answer>(this.answers);

        return newOrderedList;
    }

    @Override
    public int hashCode() {
        return answers.hashCode();
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        eventBus.post(this);
    }

    public void setPosition(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        eventBus.post(this);
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
        eventBus.post(this);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Question && super.equals(obj)){
            Question o = (Question) obj;

            if(questionText !=null){
                if(o.getQuestionText()!=null){
                    if(!questionText.equals(o.getQuestionText()))
                        return false;
                } else {
                    return false;
                }
            }

            if(imagePath !=null){
                if(o.getImagePath()!=null){
                    if(!imagePath.equals(o.getImagePath()))
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
        sb.append("Question: ").append(questionText).append(super.toString());
        return sb.toString();
    }
}
