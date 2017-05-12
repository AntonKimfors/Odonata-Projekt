package com.example.murk.kwizgeeq.model;

import org.apache.commons.lang.time.StopWatch;

/**
 * Created by Are on 12/05/2017.
 */

//TODO possibly add separate SpotifyStatistics which extends this when SpotifyQuiz is implemented.

public class Statistics {

    private int quizCount;
    private int questionCount;
    private int answerCorrectCount;
    private int answerIncorrectCount;
    private long secondsSpent;

    private StopWatch stopWatch;

    public Statistics(){
        this.quizCount = 0;
        this.questionCount = 0;
        this.answerCorrectCount = 0;
        this.answerIncorrectCount = 0;
        this.stopWatch = new StopWatch();
    }

    public void startTimer(){
        stopWatch.start();
    }

    public void stopTimer(){
        stopWatch.stop();
        secondsSpent = stopWatch.getTime() / 1000;
    }

    public void mergeWith(Statistics otherStat){
        otherStat.quizCount += this.quizCount;
        otherStat.questionCount += this.questionCount;
        otherStat.answerCorrectCount += this.answerCorrectCount;
        otherStat.answerIncorrectCount += this.answerIncorrectCount;
    }

    public void incQuizCount(){
        quizCount++;
    }

    public void incQuestionCount(){
        questionCount++;
    }

    public void incAnswerCorrectCount(){
        answerCorrectCount++;
    }

    public void incAnswerIncorrectCount(){
        answerIncorrectCount++;
    }

    public int getQuizCount(){
        return quizCount;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public int getAnswerCorrectCount() {
        return answerCorrectCount;
    }

    public int getAnswerIncorrectCount() {
        return answerIncorrectCount;
    }

    public long getSecondsSpent(){
        return secondsSpent;
    }

}
