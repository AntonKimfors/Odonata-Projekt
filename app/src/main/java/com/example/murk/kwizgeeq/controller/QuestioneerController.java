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

    private NavigatableActivity activity;

    private int quizIndex;
    private int questionIndex;

    public QuestioneerController(QuestioneerView view, NavigatableActivity activity, int quizIndex) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.activity = activity;
        this.quizIndex = quizIndex;
        this.questionIndex = 0;
    }

    public void onCreate() {
        view.updateQuestioneer(model.activeQuiz, model.activeQuestionIndex + 1, model.activeQuiz.getQuestions().size());
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        //TODO (Statistics) change to relevant activity and save relevant information if isFinished()
    }

    public void answerSelected(View view){
        if(model.checkAnswerIsCorrect((Answer)(view.getTag()))){
            this.view.flashCorrectAnswer(view);
            //TODO (Statistics) save relevant information
        } else{
            this.view.flashIncorrectAnswer(view);
            //TODO (Statistics) save relevant information
        }
    }

    public void finishQuestion(){
        if(model.activeQuestionIndex + 1 == model.activeQuiz.getQuestions().size()) {
            view.destroyActivity(activity);
        }
        else {
            model.incActiveQuestion();
            view.updateQuestioneer(KwizGeeQ.getInstance().activeQuiz, model.activeQuestionIndex + 1, model.activeQuiz.getQuestions().size());
        }
    }

    public void update(Observable o, Object arg) {
        if(arg == "question done"){
            finishQuestion();
        }
    }
}
