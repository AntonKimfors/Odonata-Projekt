package com.example.murk.kwizgeeq.model;

/**
 * Created by Henrik on 04/04/2017.
 */

public class Answer<T>  {
    boolean isCorrect;
    private T answer;

    public T getAnswer(){
        return answer;
    }

    public Answer(boolean isCorrect, T answer) {
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(obj instanceof Answer){
            Answer<T> o = (Answer<T>) obj;
            return answer.equals(o.getAnswer()) ? true : false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return answer.hashCode();
    }
}
