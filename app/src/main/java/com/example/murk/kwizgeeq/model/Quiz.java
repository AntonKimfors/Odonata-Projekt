package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Quiz implements Iterable{
    protected ArrayList<Question> questions;
    private int listColor;
    private String name;


    public Quiz(String name,int listColor) {
        questions = new ArrayList<>();
        this.name = name;
        this.listColor = listColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getListColor() {
        return listColor;
    }

    public void setListColor(int listColor) {
        this.listColor = listColor;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void removeQuestion(int questionIndex){
        questions.remove(questionIndex);
    }

    public int getSize(){
        return questions.size();
    }

    public Question getQuestion(int questionIndex){
        if(questionIndex<=questions.size()){

            if(questionIndex==questions.size()){
                Question question = createQuestion();
                questions.add(question);
                return question;
            } else {
                return questions.get(questionIndex);
            }

        } else{
            throw new IndexOutOfBoundsException("Requested question index too large. " +
                    "Only modification of existing or putting new question on end are allowed.");
        }
    }

    protected abstract Question createQuestion();


    @Override
    public Iterator iterator() {
        return questions.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Question q:questions){
            sb.append(q.toString());
        }

        return sb.toString();
    }
}
