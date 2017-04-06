package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Quiz implements Iterable{
    private List<Question> questions;
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

    public List<Question> getQuestions() {
        return questions;
    }


    @Override
    public Iterator iterator() {
        return questions.iterator();
    }
    

}
