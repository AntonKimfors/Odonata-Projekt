package com.example.murk.kwizgeeq.controller;

import android.view.View;

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
        view.updateQuizRelatedItems(quizIndex);
        view.updateQuestioneer(quizIndex, questionIndex);
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
        if(questionIndex + 1 == model.getAmountOfQuestions(quizIndex)) {
            view.destroyActivity(activity);
        }
        else {
            questionIndex++;
            view.updateQuestioneer(quizIndex, questionIndex);
        }
    }

    public void update(Observable o, Object arg) {
        if(arg == "question done"){
            finishQuestion();
        }
    }
}
