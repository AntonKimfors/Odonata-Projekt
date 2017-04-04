package com.example.murk.kwizgeeq.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 */

class Question {
    private List<Answer> answers;
    private String question;

    public Question(String question) {
        answers = new ArrayList<>();
        this.question = question;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Iterator<Answer> answerIterator() {
        shuffleAnswers();
        return answers.iterator();
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void removeAnswer(Answer answer){
        answers.remove(answer);
    }

    private void shuffleAnswers(){
        Collections.shuffle(answers);
    }
}
