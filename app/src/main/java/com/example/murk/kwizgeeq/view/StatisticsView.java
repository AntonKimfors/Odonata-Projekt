package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Statistics;

/**
 * Created by Are on 09/05/2017.
 */

public class StatisticsView {

    private TextView quizLabel;
    private TextView answersLabel;
    private ProgressBar answersProgressBar;
    private Button retryIncorrectButton;
    private Button retryAllButton;
    private Button doneButton;

    public StatisticsView(Activity activity) {
        this.quizLabel = (TextView) activity.findViewById(R.id.quizLabel);
        this.answersLabel = (TextView) activity.findViewById(R.id.answersLabel);
        this.answersProgressBar = (ProgressBar) activity.findViewById(R.id.answersProgressBar);
        this.retryIncorrectButton = (Button) activity.findViewById(R.id.retryIncorrectButton);
        this.retryAllButton = (Button) activity.findViewById(R.id.retryAllButton);
        this.doneButton = (Button) activity.findViewById(R.id.doneButton);
    }

    public void updateStatistics(String quizName, Statistics statistics){
        quizLabel.setText(quizName);
        answersProgressBar.setMax(statistics.getQuestionCount());
        answersProgressBar.setProgress(statistics.getAnswerCorrectCount());
        answersLabel.setText("Correct: " + answersProgressBar.getProgress() + " / " + answersProgressBar.getMax());
    }

    public void disableReplayByIndexButton(){
        retryIncorrectButton.setEnabled(false);
    }

}
