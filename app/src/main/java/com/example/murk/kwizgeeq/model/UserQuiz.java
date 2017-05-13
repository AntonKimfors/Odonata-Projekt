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

    @Override
    protected Question createQuestion() {
        return new UserQuestion();
    }

    private UserQuestion getRequestedUserQuestion(int questionIndex){
        if(questionIndex<=questions.size()){

            if(questionIndex==questions.size()){
                UserQuestion userQuestion = new UserQuestion();
                questions.add(userQuestion);
                return userQuestion;
            } else {
                return (UserQuestion) questions.get(questionIndex);
            }

        } else{
            throw new IndexOutOfBoundsException("Requested question index too large. " +
                    "Only modification of existing or putting new question on end are allowed.");
        }
    }

    public UserQuiz copyUserQuiz(){
        UserQuiz clonedQuiz = new UserQuiz(this.getName(),this.getListColor());
        for(Question question :getQuestions()){
            clonedQuiz.addQuestion(question);
        }
        return clonedQuiz;
    }


}
