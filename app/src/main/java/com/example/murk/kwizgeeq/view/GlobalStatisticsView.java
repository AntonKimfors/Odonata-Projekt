package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Statistics;

/**
 * Created by Are on 27/05/2017.
 */

public class GlobalStatisticsView {

    private Activity activity;

    private TextView quizCountLabel;
    private TextView questionCountLabel;
    private TextView answerCorrectCountLabel;
    private TextView answerIncorrectCountLabel;
    private TextView percentageLabel;
    private ProgressBar answersProgressBar;
    private TextView timeSpentLabel;

    public GlobalStatisticsView(Activity activity) {
        this.activity = activity;

        this.quizCountLabel = (TextView) activity.findViewById(R.id.quizCountLabel);
        this.questionCountLabel = (TextView) activity.findViewById(R.id.questionCountLabel);
        this.answerCorrectCountLabel = (TextView) activity.findViewById(R.id.answerCorrectCountLabel);
        this.answerIncorrectCountLabel = (TextView) activity.findViewById(R.id.answerIncorrectCountLabel);
        this.percentageLabel = (TextView) activity.findViewById(R.id.percentageLabel);
        this.answersProgressBar = (ProgressBar) activity.findViewById(R.id.globalAnswersProgressBar);
        this.timeSpentLabel = (TextView) activity.findViewById(R.id.timeSpentLabel);
    }

    public void setupStatistics(Statistics statistics){
        quizCountLabel.setText(statistics.getQuizCount() + " quizzes completed");
        questionCountLabel.setText(statistics.getQuestionCount() + " questions answered");
        answerCorrectCountLabel.setText("Correct: " + statistics.getAnswerCorrectCount());
        answerIncorrectCountLabel.setText("Wrong: " + statistics.getAnswerIncorrectCount());
        if(statistics.getBestPercentage() == 0){
            percentageLabel.setTextColor(activity.getResources().getColor(R.color.colorIncorrectAnswer));
        } else {
            percentageLabel.setTextColor(activity.getResources().getColor(R.color.colorCorrectAnswer));
        }
        percentageLabel.setText(statistics.getBestPercentage() + "%");
        answersProgressBar.setMax(statistics.getQuestionCount());
        answersProgressBar.setProgress(statistics.getAnswerCorrectCount());
        timeSpentLabel.setText(statistics.getSecondsSpent() + " seconds wasted");
    }


}
