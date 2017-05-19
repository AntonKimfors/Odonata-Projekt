package com.example.murk.kwizgeeq.view;

import android.os.Build;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;

/**
 * Created by Are on 09/05/2017.
 */

public class StatisticsView {

    private KwizGeeQ model;

    private TextView quizLabel;
    private TextView answersLabel;
    private ProgressBar answersProgressBar;
    private Button replayByIndexButton;
    private Button replayAllButton;
    private Button doneButton;

    public StatisticsView(TextView quizLabel, TextView answersLabel, ProgressBar answersProgressBar, Button replayByIndexButton, Button replayAllButton, Button doneButton) {
        this.model = KwizGeeQ.getInstance();
        this.quizLabel = quizLabel;
        this.answersLabel = answersLabel;
        this.answersProgressBar = answersProgressBar;
        this.replayByIndexButton = replayByIndexButton;
        this.replayAllButton = replayAllButton;
        this.doneButton = doneButton;
    }

    public void updateStatistics(UserQuiz quiz){
        quizLabel.setText(quiz.getName());
        answersProgressBar.setMax(quiz.getCurrentTempStatistics().getQuestionCount());
        answersProgressBar.setProgress(quiz.getCurrentTempStatistics().getAnswerCorrectCount());
        answersLabel.setText("Correct: " + answersProgressBar.getProgress() + " / " + answersProgressBar.getMax());
    }

    public void disableReplayByIndexButton(){
        replayByIndexButton.setEnabled(false);
    }

}
