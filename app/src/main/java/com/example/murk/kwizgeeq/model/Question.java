package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Question<T> {

    private final List<Answer<T>> answers;

    private int wrongAnswerCount;

    private int correctAnswerCount;

    public Question() {
        answers = new ArrayList<>();

        wrongAnswerCount = 0;

        correctAnswerCount = 0;
    }

    /**
     * Creates an iterator over all the answers in a randomized order
     * @return iterator over answers if question is valid, if not return null
     */
    public Iterator<Answer<T>> shuffledAnswerIterator() {
        List<Answer<T>> shuffleList = new ArrayList<>(answers);
        Collections.shuffle(shuffleList);

        return shuffleList.iterator();
    }

    public boolean addCorrectAnswer(T data){
        if(data == null)
            throw new NullPointerException();

        if(answers.add(new Answer<T>(false, data))){
            correctAnswerCount++;
            return true;
        }

        throw new IllegalArgumentException();
    }

    public boolean addWrongAnswer(T data) {
        if(data == null)
            throw new NullPointerException();

        if(answers.add(new Answer<T>(true, data))){
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
