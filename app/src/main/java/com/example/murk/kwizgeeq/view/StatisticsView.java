package com.example.murk.kwizgeeq.view;

import android.os.Build;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.model.KwizGeeQ;

/**
 * Created by Are on 09/05/2017.
 */

public class StatisticsView {

    private KwizGeeQ model;

    private TextView quizLabel;
    private TextView answersLabel;
    private ProgressBar answersProgressBar;

    public StatisticsView(TextView quizLabel, TextView answersLabel, ProgressBar answersProgressBar) {
        this.model = KwizGeeQ.getInstance();
        this.quizLabel = quizLabel;
        this.answersLabel = answersLabel;
        this.answersProgressBar = answersProgressBar;
    }

    public void updateStatistics(int quizIndex){
        quizLabel.setText(model.getQuiz(quizIndex).getName());
        answersProgressBar.setMax(model.getCurrentQuizStatistics().getQuestionCount());
        answersProgressBar.setProgress(model.getCurrentQuizStatistics().getAnswerCorrectCount());
        answersLabel.setText("Correct: " + answersProgressBar.getProgress() + " / " + answersProgressBar.getMax());
    }

}
