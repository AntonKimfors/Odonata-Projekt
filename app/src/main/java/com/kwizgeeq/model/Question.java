package com.kwizgeeq.model;

import com.kwizgeeq.events.EventBusWrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 *
 * @author Henrik Håkansson
 * revised by Are Ehnberg, Marcus Olsson Lindvärn and Anton Kimfors
 */

public class Question implements Serializable {

    private final List<Answer> answers;
    private int wrongAnswerCount;
    private int correctAnswerCount;
    private String questionText;
    private String imagePath;

    public Question() {
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

        if(answers.add(answer)){
            if(answer.isCorrect())
                correctAnswerCount++;
            else
                wrongAnswerCount++;
        }

        if(answerType == AnswerType.IMAGE){
            EventBusWrapper.BUS.post(this);
        }
    }

    public void clearAnswers(){
        correctAnswerCount = 0;
        wrongAnswerCount = 0;
        answers.clear();
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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        EventBusWrapper.BUS.post(this);
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
