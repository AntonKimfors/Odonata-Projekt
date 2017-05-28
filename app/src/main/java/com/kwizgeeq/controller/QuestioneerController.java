package com.kwizgeeq.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.kwizgeeq.model.Answer;
import com.kwizgeeq.model.Quiz;
import com.kwizgeeq.model.Statistics;

import com.kwizgeeq.view.QuestioneerView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerController implements Observer{

    private QuestioneerView view;
    private Class<? extends Activity> switchActivityClass;
    private Activity currentActivity;
    private ArrayList<Integer> outReplayIndexList;
    private ArrayList<Integer> inReplayIndexList;

    private Statistics currentTempPlayedStatistics;
    private Quiz quiz;
    private int questionIndex;
    private int currentQuestion;
    private int quizSize;
    private boolean playingByIndex;
    public boolean everPlayedByIndex;
    private int statisticsRequestCode;

    public QuestioneerController(Activity activity, QuestioneerView view, int statisticsRequestCode) {
        this.view = view;
        this.currentActivity = activity;
        this.outReplayIndexList = new ArrayList<>();
        this.inReplayIndexList = new ArrayList<>();
        this.quiz = (Quiz) activity.getIntent().getSerializableExtra("quiz");
        this.questionIndex = 0;
        this.currentQuestion = 1;
        this.playingByIndex = false;
        this.everPlayedByIndex = false;
        this.statisticsRequestCode = statisticsRequestCode;
    }

    public void setUpQuestioneer() {
        updateQuestionIndex();
        updateQuizSize();
        view.updateQuizRelatedItems(quiz.getName(), quizSize, quiz.getListColor());
        view.updateQuestioneer(quiz.getQuestion(questionIndex), currentQuestion, quizSize);
        currentTempPlayedStatistics = new Statistics();
        currentTempPlayedStatistics.startTimer();
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
        currentTempPlayedStatistics.incQuestionCount();
        if(((Answer)view.getTag()).isCorrect()){
            currentTempPlayedStatistics.incAnswerCorrectCount();
            this.view.flashCorrectAnswer(view);
        } else{
            outReplayIndexList.add(questionIndex);
            currentTempPlayedStatistics.incAnswerIncorrectCount();
            this.view.flashIncorrectAnswer(view);
        }
    }

    public void finishQuestion(){
        if(currentQuestion == quizSize) {
            currentTempPlayedStatistics.stopTimer();
            currentTempPlayedStatistics.incQuizCount();
            if(!everPlayedByIndex){
                quiz.getCurrentTempStatistics().copy(currentTempPlayedStatistics);
                currentActivity.setResult(currentActivity.RESULT_OK, currentActivity.getIntent());
            }
            Intent intent = new Intent(currentActivity, switchActivityClass);
            intent.putExtra("quizName", quiz.getName());
            intent.putExtra("statistics", currentTempPlayedStatistics);
            intent.putExtra("allCorrect", outReplayIndexList.isEmpty());
            currentActivity.startActivityForResult(intent, statisticsRequestCode);
        }
        else {
            currentQuestion++;
            updateQuestionIndex();
            view.updateQuestioneer(quiz.getQuestion(questionIndex), currentQuestion, quizSize);
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
        setUpQuestioneer();
    }

    private void replayQuestionsByIndex(){
        playingByIndex = true;
        everPlayedByIndex = true;
        currentQuestion = 1;
        inReplayIndexList = (ArrayList)outReplayIndexList.clone();
        outReplayIndexList.clear();
        setUpQuestioneer();
    }

    public void replayResult(Intent data){
        if(data.getBooleanExtra("replayByIndex", false)){
            replayQuestionsByIndex();
        } else {
            replayQuestions();
        }
    }

    public void onBackPressed(){
        view.showCloseQuizDialog(quiz.getName());
    }

}
