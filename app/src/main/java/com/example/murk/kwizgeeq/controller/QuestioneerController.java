package com.example.murk.kwizgeeq.controller;

import android.view.View;
import android.view.Window;

import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.view.QuestioneerView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerController implements Controller, Observer{

    private QuestioneerView view;
    private KwizGeeQ model;

    public QuestioneerController(QuestioneerView view) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
    }

    public void onCreate() {
        model.activeQuestionIndex = 0; //TODO remove when firePlayQuiz() is implemented correctly
        view.updateQuestioneer(model.activeQuiz, model.activeQuestionIndex + 1, model.activeQuiz.getQuestions().size());
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        //TODO (Statistics) change to relevant activity and save relevant information if isFinished()
    }

    public void answerSelected(View view, Window window, NavigatableActivity activity){
        if(model.checkAnswerIsCorrect((Answer)(view.getTag()))){
            this.view.flashCorrectAnswer(view, window, activity);
            //TODO (Statistics) save relevant information
        } else{
            this.view.flashIncorrectAnswer(view, window, activity);
            //TODO (Statistics) save relevant information
        }
    }

    public void finishQuestion(NavigatableActivity activity){
        if(model.activeQuestionIndex + 1 == model.activeQuiz.getQuestions().size()) {
            view.destroyActivity(activity);
        }
        else {
            model.incActiveQuestion();
            view.updateQuestioneer(KwizGeeQ.getInstance().activeQuiz, model.activeQuestionIndex + 1, model.activeQuiz.getQuestions().size());
        }
    }

    public void update(Observable o, Object arg) {
        if(arg instanceof NavigatableActivity){
            finishQuestion((NavigatableActivity) arg);
        }
    }
}
