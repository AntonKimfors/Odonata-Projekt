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

    //set of all answers
    private final Set<Answer<T>> answers;

    public Question() {
        answers = new HashSet<>(4);
    }

    /**
     * Shuffles the order of answers and returns iterator
     * @return iterator over answers if question is valid, if not return null
     */
    public Iterator<Answer<T>> answerIterator() {
        if(isValid()){
            //shuffle order of answers
            List<Answer<T>> answerList = new ArrayList<>(answers);
            Collections.shuffle(answerList);
            return answerList.iterator();
        }
        return null;
    }

    public Set<Answer<T>> getAnswers(){
        return answers;
    }

    /**
     * Add a new unique answer to the answer list
     * @param answer
     * @return true if insert successful
     */
    public boolean addAnswer(Answer<T> answer) {
        if(answers.size()<4)
            return answers.add(answer);
        return false;
    }

    /**
     * Removes an answer and adds a null object in end of list
     * @param answer
     */
    public boolean removeAnswer(Answer answer){
        return answers.remove(answer);
    }

    /**
     * Determines whether the Queation can be used or not
     * @return true if number of answers is 4
     */
    public boolean isValid(){
        return (answers.size()==4) ? true : false;
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

            return answers.equals(o.getAnswers());
        }
        return false;
    }
}
