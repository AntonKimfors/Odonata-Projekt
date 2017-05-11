package com.example.murk.kwizgeeq.model;

import java.io.Serializable;

/**
 * Created by Murk on 2017-04-05.
 */

public class UserQuiz extends Quiz {
	
	public UserQuiz(String name, int listColor) {
		super(name, listColor);
	}

    @Override
    Question createQuestion() {
        return new UserQuestion();
    }

    public void setUserQuestionString(int questionIndex, String questionString){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setQuestionText(questionString);
    }

    public void setUserQuestionImagePath(int questionIndex, String questionImagePath){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setImagePath(questionImagePath);
    }

    public void setUserQuestionPosition(int questionIndex, double x, double y){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setxPosition(x);
        userQuestion.setyPosition(y);
    }

    public void setUserQuestionAudioPath(int questionIndex, String audioPath){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setAudioPath(audioPath);
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
