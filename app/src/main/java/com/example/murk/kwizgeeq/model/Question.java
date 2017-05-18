package com.example.murk.kwizgeeq.model;

import com.example.murk.kwizgeeq.events.BusWrapper;
import com.google.common.eventbus.EventBus;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Question implements Serializable {

    private final List<Answer> answers;

    private int wrongAnswerCount;

    private int correctAnswerCount;

    EventBus eventBus;

    public Question() {
        eventBus = BusWrapper.BUS;
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

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            throw new NullPointerException();

        if(obj instanceof Question){
            Question o = (Question) obj;

            return answers.equals(o.answers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return answers.hashCode();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int i=1;
        for(Answer a: answers){
            sb.append("Answer ").append(i).append(": ").append(a.toString()).append(System.lineSeparator());
            i++;
        }

        return sb.toString();
    }

}
