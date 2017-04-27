package com.example.murk.kwizgeeq.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Question<T> implements Serializable {

    private final List<Answer<T>> answers;

    private int wrongAnswerCount;

    private int correctAnswerCount;

    public Question() {
        answers = new ArrayList<>();

        wrongAnswerCount = 0;

        correctAnswerCount = 0;
    }

    public Iterator<Answer<T>> answerIterator(boolean shuffled) {
        List<Answer<T>> answerList = new ArrayList<>(answers);
        if(shuffled){
            Collections.shuffle(answerList);
        }

        return answerList.iterator();
    }

    public boolean addAnswer(Answer<T> answer){
        if(answer == null)
            throw new NullPointerException();

        if(answers.add(answer)){
            if(answer.isCorrect())
                correctAnswerCount++;
            else
                wrongAnswerCount++;
            return true;
        }

        throw new IllegalArgumentException();
    }

    public boolean removeAnswer(Answer<T> answer){
        if(answer == null)
            throw new NullPointerException();

        if(answers.remove(answer)){
            if(answer.isCorrect())
                correctAnswerCount--;
            else
                wrongAnswerCount--;

            return true;
        }

        throw new IllegalArgumentException("Element not in list");
    }

    public boolean checkNumberOfCorrect(int excepted){
        return excepted == correctAnswerCount;
    }

    public boolean checkNumberOfWrong(int expected){
        return expected == wrongAnswerCount;
    }

    public boolean checkNumberOfQuestions(int excepted){
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



}
