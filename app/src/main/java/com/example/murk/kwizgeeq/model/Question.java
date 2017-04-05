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

public abstract class Question {

    //the list of answers to question, size should always be 4
    private final List<Answer> answers;

    //keeps track of number of inserted answers
    private int numberOfAnswers;

    public Question() {
        answers = new ArrayList<>(4);
        numberOfAnswers = 0;
    }

    /**
     * Shuffles the order of answers and returns iterator
     * @return iterator over answers if question is valid, if not return null
     */
    public Iterator<Answer> answerIterator() {
        if(isValid()){
            //shuffle order of answers
            Collections.shuffle(answers);
            return answers.iterator();
        }
        return null;
    }

    public List<Answer> getAnswers(){
        return answers;
    }

    /**
     * Add a new unique answer to the answer list
     * @param answer
     * @return true if insert successful
     */
    public boolean addAnswer(Answer answer) {
        //check if element already exists
        if(answers.contains(answer)){
            return false;
        }

        //add answer last in list
        if(numberOfAnswers<4){
            answers.set(numberOfAnswers,answer);
            numberOfAnswers++;
            return true;
        }

        return false;
    }

    /**
     * Removes an answer and adds a null object in end of list
     * @param answer
     */
    public boolean removeAnswer(Answer answer){
        if(!answers.contains(answer))
            return false;
        int index = answers.indexOf(answer);
        //size should always be 4
        int size = answers.size();

        //move all answer elements after the answer to remove one position forward
        for(int i=index;i<answers.size()-2;i++){
            answers.set(i,answers.get(i+1));
        }

        //add null to last position
        answers.set(size-1,null);

        numberOfAnswers--;

        return true;
    }

    /**
     *
     * @return true if numberOfAnswers is 4
     */
    public boolean isValid(){
        return (numberOfAnswers==4) ? true : false;
    }

    /**
     * @param obj
     * @return true if lists of answers are equal, independent of order
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(obj instanceof Question){
            Question o = (Question) obj;

            Set<Answer> s1 = new HashSet<>(answers);
            Set<Answer> s2 = new HashSet<>(o.getAnswers());

            return s1.equals(s2);
        }
        return false;
    }
}
