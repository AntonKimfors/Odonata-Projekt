package com.example.murk.kwizgeeq.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Question {
    private List<Answer> answers;

    int numberOfAnswers;

    public Question() {
        answers = new ArrayList<>();
        numberOfAnswers = 0;
    }

    public Iterator<Answer> answerIterator() {
        shuffleAnswers();
        return answers.iterator();
    }

    public List<Answer> getAnswers(){
        return answers;
    }

    public void addAnswer(Answer answer) {
        if(numberOfAnswers<5){
            answers.add(answer);
            numberOfAnswers++;
        }
    }

    public void removeAnswer(Answer answer){
        answers.remove(answer);
    }

    private void shuffleAnswers(){
        Collections.shuffle(answers);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(obj instanceof Question){
            Question o = (Question) obj;
            if(answers.equals(o.getAnswers())){
                return true;
            }
        }
        return false;
    }
}
