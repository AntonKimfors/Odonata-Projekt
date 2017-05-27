package com.example.murk.kwizgeeq.model;

import java.io.Serializable;

/**
 * Created by Henrik on 15/05/2017.
 */

public enum AnswerType implements Serializable {
    TEXT,IMAGE,AUDIO;



    @Override
    public String toString() {
        switch(this) {
            case TEXT: return "TEXT";
            case IMAGE: return "IMAGE";
            default: throw new IllegalArgumentException();
        }
    }
}
