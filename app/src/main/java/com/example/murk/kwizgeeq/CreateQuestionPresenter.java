package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class CreateQuestionPresenter {

    private CreateQuestionView view;
    private UserQuiz quiz;
    private Question previous;

    private Question current;

    public void onCreate() {

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
        current = new UserQuestion(questionStr,null,null,null);
        quiz.addQuestion(current);
    }

    public void addStringAnswer(String answerStr, boolean isCorrect){
        if(current!=null && !answerStr.equals("")){
            Answer<String> answer = new Answer(isCorrect,answerStr);
            current.addAnswer(answer);
        }
    }
}
