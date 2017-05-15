package com.example.murk.kwizgeeq.model;

import android.net.Uri;

import java.util.Observer;

/**
 * Created by Murk on 2017-04-05.
 */

public class UserQuiz extends Quiz {
	
	public UserQuiz(String name, int listColor) {
		super(name, listColor);
	}

    /*
    public UserQuiz copyUserQuiz(){
        UserQuiz clonedQuiz = new UserQuiz(this.getName(),this.getListColor());
        for(Question question :getQuestions()){
            clonedQuiz.addQuestion(question);
        }
        return clonedQuiz;
    }*/


}
