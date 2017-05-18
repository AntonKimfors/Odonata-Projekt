package com.example.murk.kwizgeeq.model;

import java.io.Serializable;

/**
 * Created by Henrik on 04/04/2017.
 */

public class Answer implements Serializable {
    private final boolean isCorrect;
    private final String data;
    private final AnswerType answerType;

    public String getData() {
        return data;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public AnswerType getAnswerType(){
        return answerType;
    }

    public Answer(boolean isCorrect, String data, AnswerType answerType) {
        this.isCorrect = isCorrect;
        this.data = data;
        this.answerType = answerType;
    }

    /**
     * Objects are equal if the data data is equal
     * @param obj
     * @return true is data
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(obj instanceof Answer){
            return data.equals(((Answer)obj).data) && isCorrect == ((Answer)obj).isCorrect &&
                    answerType.equals(((Answer)obj).answerType);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString(){
        Boolean pointerBoolean = (Boolean)isCorrect;
        return data.toString() + " - " + pointerBoolean.toString();
    }
}
