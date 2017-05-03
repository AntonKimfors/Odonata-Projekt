package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class CreateQuestionPresenter {

    private CreateQuestionView view;
    private UserQuiz quiz;

    private Question current;

    public CreateQuestionPresenter(CreateQuestionView view){
        this.view = view;
    }

    public void onCreate() {
        quiz = view.getQuiz();
    }

    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public Question getCurrent() {
        return current;
    }

    public void createQuestion(String questionStr){
        if(quiz == null){
            throw new NullPointerException();
        }
        if(current==null){
            current = new UserQuestion(questionStr,null,null,null);
            quiz.addQuestion(current);
        } else {
            //replace question
            int currentIndex = quiz.getQuestions().indexOf(current);
            current = new UserQuestion(questionStr,null,null,null);
            quiz.setQuestion(currentIndex,current);
        }
    }

    public void addStringAnswer(String answerStr, boolean isCorrect){
        if(current!=null && !answerStr.equals("")){
            Answer<String> answer = new Answer(isCorrect,answerStr);
            current.addAnswer(answer);
        }
    }
}
