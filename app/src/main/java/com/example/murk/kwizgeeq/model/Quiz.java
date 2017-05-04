package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Quiz implements Iterable{
    private ArrayList<Question> questions;
    private Color listColor;
    private String name;


    public Quiz(String name,Color listColor) {
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

    public Color getListColor() {
        return listColor;
    }

    public void setListColor(Color listColor) {
        this.listColor = listColor;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * eventuellt behövs inte denna metod
     */
    public void addQuestion(Question question){
        questions.add(question);
    }

    /**
     * bättre att använda den här om varje presenter vet vilket index den är kopplad till
     * @param index
     * @param question
     */
    public void addQuestionOnIndex(int index, Question question){
        if(index>=questions.size()){
            questions.add(question);
        } else {
            questions.set(index,question);
        }
    }

    public void addAnswer(int questionIndex, Answer answer){
        questions.get(questionIndex).addAnswer(answer);
    }

    public void removeQuestion(Question question){
        getQuestions().remove(question);
    }

    public int getSize(){
        return questions.size();
    }

    public Question getQuestion(int index){
        return questions.get(index);
    }


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
