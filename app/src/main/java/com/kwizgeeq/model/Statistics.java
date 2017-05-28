package com.kwizgeeq.model;

import org.apache.commons.lang.time.StopWatch;

import java.io.Serializable;

/**
 * Created by Are on 12/05/2017.
 *
 * * @author Are Ehnberg
 * revised by Marcus Olsson Lindvärn, Anton Kimfors, Henrik Håkansson
 */

//TODO possibly add separate SpotifyStatistics which extends this when SpotifyQuiz is implemented.

public class Statistics implements Serializable{

    private int quizCount;
    private int questionCount;
    private int answerCorrectCount;
    private int answerIncorrectCount;
    private int secondsSpent;

    private transient StopWatch stopWatch;

    public Statistics(){
        this.quizCount = 0;
        this.questionCount = 0;
        this.answerCorrectCount = 0;
        this.answerIncorrectCount = 0;
        this.secondsSpent = 0;
        this.stopWatch = new StopWatch();
    }

    public Statistics(int quizCount, int questionCount, int answerCorrectCount, int answerIncorrectCount, int secondsSpent){
        this.quizCount = quizCount;
        this.questionCount = questionCount;
        this.answerCorrectCount = answerCorrectCount;
        this.answerIncorrectCount = answerIncorrectCount;
        this.secondsSpent = secondsSpent;
        this.stopWatch = new StopWatch();
    }

    public void startTimer(){
        stopWatch.start();
    }

    public void stopTimer(){
        stopWatch.stop();
        secondsSpent = (int)(stopWatch.getTime() / 1000);
    }

    public void mergeInto(Statistics otherStat){
        otherStat.quizCount += this.quizCount;
        otherStat.questionCount += this.questionCount;
        otherStat.answerCorrectCount += this.answerCorrectCount;
        otherStat.answerIncorrectCount += this.answerIncorrectCount;
        otherStat.secondsSpent += this.secondsSpent;
    }

    public void copy(Statistics copyStatistics){
        this.quizCount = copyStatistics.getQuizCount();
        this.questionCount = copyStatistics.getQuestionCount();
        this.answerCorrectCount = copyStatistics.getAnswerCorrectCount();
        this.answerIncorrectCount = copyStatistics.getAnswerIncorrectCount();
        this.secondsSpent = copyStatistics.getSecondsSpent();
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

    public int getSecondsSpent(){
        return secondsSpent;
    }

    public int getCorrectAnswerPercentage(){
        if (answerCorrectCount == 0)
            return 0;
        else if (answerIncorrectCount == 0)
            return 100;
        else
            return (int)((answerCorrectCount * 100.0f) / questionCount);
    }

}
