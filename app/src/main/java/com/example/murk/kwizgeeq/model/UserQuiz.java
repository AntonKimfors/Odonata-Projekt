package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

/**
 * Created by Murk on 2017-04-05.
 */

public class UserQuiz extends Quiz {
	
	public UserQuiz(String name, Color listColor) {
		super(name, listColor);
	}

    public void addQuestion(Question question) {
    	getQuestions().add(question);
    }

    public void removeQuestion(Question question){
        getQuestions().remove(question);
        
    }
    public UserQuiz copyUserQuiz(){
        UserQuiz clonedQuiz = new UserQuiz(this.getName(),this.getListColor());
        for(Question question :getQuestions()){
            clonedQuiz.addQuestion(question);
        }
        return clonedQuiz;
    }


}
