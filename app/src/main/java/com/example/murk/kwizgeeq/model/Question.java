package com.example.murk.kwizgeeq.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Question<T> {

    //the correct answer
    private Answer<T> correctAnswer;

    //set of incorrect answers
    private final Set<Answer<T>> incorrectAnswers;

    //contastant for number of answers
    private static final int NumberOfAnswers = 4;

    public Question() {
        correctAnswer = null;
        incorrectAnswers = new HashSet<>(3);
    }

    /**
     * Creates an iterator that iterates all the answers in a random order
     * @return iterator over answers if question is valid, if not return null
     */
    public Iterator<Answer<T>> answerIterator() {
        if(isValid()){
            //create lsit of all answers
            List<Answer<T>> answerList = new ArrayList<>(incorrectAnswers);
            answerList.add(correctAnswer);
            //shuffle order
            Collections.shuffle(answerList);
            return answerList.iterator();
        }
        return null;
    }

    /**
     * Iterates all incorrect answers, independent whether the Question is valid or not
     * @return
     */
    public Iterator<Answer<T>> incorrectAnswerIterator(){
        List<Answer<T>> incorrectAnswerList = new ArrayList<>(incorrectAnswers);
        return incorrectAnswerList.iterator();
    }

    public Answer<T> getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean setCorrectAnswer(T answer){
        if(answer == null)
            return false;

        correctAnswer = new Answer<T>(true,answer);
        return true;
    }

    public Set<Answer<T>> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    /**
     * Add a new unique incorrect answer
     * @param answer the incorrect answer to add
     * @return true if insert successful
     */
    public boolean addIncorrectAnswer(T answer) {
        if(incorrectAnswers.size()<NumberOfAnswers-1)
            return incorrectAnswers.add(new Answer(false,answer));
        return false;
    }

    /**
     * Removes an incorrect answer
     * @param answer true if removal successful
     */
    public boolean removeIncorrectAnswer(Answer answer){
        return incorrectAnswers.remove(answer);
    }

    /**
     * Determines whether the Queation can be used or not
     * @return true if number of answers is 4
     */
    public boolean isValid(){
        return (incorrectAnswers.size() == 3 && correctAnswer != null) ? true : false;
    }

    /**
     * @param obj the object to compare with
     * @return true if lists of answers are equal, independent of order
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(obj instanceof Question){
            Question o = (Question) obj;

            return incorrectAnswers.equals(o.getIncorrectAnswers()) && correctAnswer.equals(o.getCorrectAnswer());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return incorrectAnswers.hashCode() + correctAnswer.hashCode();
    }

}
