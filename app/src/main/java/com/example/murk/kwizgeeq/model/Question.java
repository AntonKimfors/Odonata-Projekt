package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Question<T> {

    //the correct answer
    private Answer<T> correctAnswer;

    //set of incorrect answers
    private final Set<Answer<T>> wrongAnswers;

    //constant for number of answers
    private static final int NumberOfAnswers = 4;

    public Question() {
        correctAnswer = null;
        wrongAnswers = new HashSet<>(3);
    }

    /**
     * Creates an iterator over all the answers in a randomized order
     * @return iterator over answers if question is valid, if not return null
     */
    public Iterator<Answer<T>> answerIterator() {
        if(isValid()){
            //create list of all answers
            List<Answer<T>> answerList = new ArrayList<>(wrongAnswers);
            answerList.add(correctAnswer);
            //shuffle order
            Collections.shuffle(answerList);
            return answerList.iterator();
        }
        return null;
    }

    /**
     * Create an iterator over all wrong answers,
     * not dependent whether the Question is valid or not
     * @return Iterator over wrong answers, order is unspecified
     */
    public Iterator<Answer<T>> wrongAnswerIterator(){
        List<Answer<T>> wrongAnswerList = new ArrayList<>(wrongAnswers);
        return wrongAnswerList.iterator();
    }

    /**
     *
     * @return The correct that is set to correct.
     */
    public Answer<T> getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Creates a new correct answer and sets it to correct
     * @param data the data held by the new answer
     * @return true if data is not null
     */
    public boolean setCorrectAnswer(T data){
        if(data == null)
            return false;

        correctAnswer = new Answer<T>(true,data);
        return true;
    }

    /**
     * Return the set of wrong answers, even if Question is not valid
     * @return
     */
    public Set<Answer<T>> getWrongAnswers() {
        return wrongAnswers;
    }

    /**
     * Add a new unique wrong answer
     * @param data the wrong answer data to add (e.g. a string)
     * @return true if insert successful
     */
    public boolean addWrongAnswer(T data) {
        if(data == null)
            throw new NullPointerException();
        if(wrongAnswers.size()<NumberOfAnswers-1)
            return wrongAnswers.add(new Answer(false,data));
        return false;
    }

    /**
     * Removes an wrong answer
     * @param data true if removal successful
     */
    public boolean removeWrongAnswer(T data){
        if(data == null)
            throw new NullPointerException();

        return wrongAnswers.remove(new Answer<T>(false,data));
    }

    /**
     * Determines whether the Queation can be used or not
     * @return true if number of answers is 4
     */
    public boolean isValid(){
        return (wrongAnswers.size() == (NumberOfAnswers - 1) && correctAnswer != null);
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

            return wrongAnswers.equals(o.getWrongAnswers()) && correctAnswer.equals(o.getCorrectAnswer());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return wrongAnswers.hashCode() + 31*correctAnswer.hashCode();
    }

}
