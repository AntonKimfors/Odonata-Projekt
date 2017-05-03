package com.example.murk.kwizgeeq.presenter;

import android.view.View;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.view.QuestioneerView;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerPresenter implements Presenter{

    private QuestioneerView view;
    private KwizGeeQ model;

    public QuestioneerPresenter(QuestioneerView view) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
    }

    public void onCreate() {
        model.activeQuestionIndex = 0; //TODO remove when firePlayQuiz() is implemented correctly
        this.view.updateQuestioneer(model.activeQuiz, model.activeQuestionIndex + 1, model.activeQuiz.getQuestions().size());
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        //TODO (Statistics) change to relevant activity
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
            view.finishQuiz();
        }
        else {
            model.incActiveQuestion();
            view.updateQuestioneer(KwizGeeQ.getInstance().activeQuiz, model.activeQuestionIndex + 1, model.activeQuiz.getQuestions().size());
        }
    }

}
