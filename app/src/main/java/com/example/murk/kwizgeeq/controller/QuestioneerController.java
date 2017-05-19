package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.QuestioneerView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerController implements Controller, Observer{

    private QuestioneerView view;
    private KwizGeeQ model;
    private Class<? extends Activity> switchActivityClass;
    private Activity currentActivity;
    private ArrayList<Integer> outReplayIndexList;
    private ArrayList<Integer> inReplayIndexList;

    private UserQuiz quiz;
    private int questionIndex;
    private int currentQuestion;
    private int quizSize;
    private boolean playingByIndex;
    private int REPLAY__REQUEST_CODE = 1;

    public QuestioneerController(Activity activity, QuestioneerView view) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = activity;
        this.outReplayIndexList = new ArrayList<>();
        this.inReplayIndexList = new ArrayList<>();
        this.quiz = (UserQuiz) activity.getIntent().getSerializableExtra("quiz");
        this.questionIndex = 0;
        this.currentQuestion = 1;
        this.playingByIndex = false;
    }

    public void onCreate() {
        updateQuestionIndex();
        updateQuizSize();
        quiz.resetCurrentTempStatistics();
        view.updateQuizRelatedItems(quiz.getName(), quizSize, quiz.getListColor());
        view.updateQuestioneer((UserQuestion) quiz.getQuestion(questionIndex), currentQuestion, quizSize);
        quiz.getCurrentTempStatistics().startTimer();
    }

    //TODO: Anpassa vad som spara
    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {
        /*if(currentActivity.isFinishing()) {
            Intent intent = new Intent(currentActivity, switchActivityClass);
            intent.putExtra("quizIndex", quizIndex);
            intent.putExtra("outReplayIndexList", outReplayIndexList);
            currentActivity.startActivity(intent);
        }*/
    }

    private void updateQuestionIndex(){
        if(!playingByIndex) {
            questionIndex = currentQuestion-1;
        } else{
            questionIndex = inReplayIndexList.get(currentQuestion-1);
        }
    }

    private void updateQuizSize(){
        if(!playingByIndex){
            this.quizSize = quiz.getSize();
        } else {
            this.quizSize = inReplayIndexList.size();
        }
    }

    public void setSwitchActivityClass(Class<? extends Activity> activityClass){
        this.switchActivityClass = activityClass;
    }

    public void answerSelected(View view){
        quiz.getCurrentTempStatistics().incQuestionCount();
        if(((Answer)view.getTag()).isCorrect()){
            quiz.getCurrentTempStatistics().incAnswerCorrectCount();
            this.view.flashCorrectAnswer(view);
        } else{
            outReplayIndexList.add(questionIndex);
            quiz.getCurrentTempStatistics().incAnswerIncorrectCount();
            this.view.flashIncorrectAnswer(view);
        }
    }

    public void finishQuestion(){
        if(currentQuestion == quizSize) {
            quiz.getCurrentTempStatistics().stopTimer();
            quiz.getCurrentTempStatistics().incQuizCount();
            quiz.updateBestStatistics();
            if(!playingByIndex){
                model.updateGlobalStatistics(quiz);
            }
            Intent intent = new Intent(currentActivity, switchActivityClass);
            intent.putExtra("quiz", quiz);
            intent.putExtra("allCorrect", outReplayIndexList.isEmpty());
            currentActivity.startActivityForResult(intent, REPLAY__REQUEST_CODE);
        }
        else {
            currentQuestion++;
            updateQuestionIndex();
            view.updateQuestioneer((UserQuestion) quiz.getQuestion(questionIndex), currentQuestion, quizSize);
        }
    }

    public void update(Observable o, Object arg) {
        if(arg == "question done"){
            finishQuestion();
        }
    }

    private void replayQuestions(){
        questionIndex = 0;
        currentQuestion = 1;
        outReplayIndexList.clear();
        onCreate();
    }

    private void replayQuestionsByIndex(){
        playingByIndex = true;
        currentQuestion = 1;
        inReplayIndexList = (ArrayList)outReplayIndexList.clone();
        outReplayIndexList.clear();
        onCreate();
    }

    public void onActivityResult(int requestCode, Intent data){
        if(requestCode == REPLAY__REQUEST_CODE){
            if(data.getBooleanExtra("replayByIndex", false)){
                replayQuestionsByIndex();
            } else {
                replayQuestions();
            }
        } else {
            view.closeQuestioneer();
        }
    }
}
