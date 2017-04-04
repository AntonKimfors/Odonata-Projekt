package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 */

public class Quiz {
    private List<Question> questions;
    private Color listColor;
    private String name;

    public Quiz(String name,Color listColor) {
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(String questionStr) {
        QuestionFactory factory = QuestionFactory.getInstance();
        questions.add(factory.createUserQuestion(questionStr,null,null));
    }

    public void removeQuestion(Question question){
        questions.remove(question);
    }
}
