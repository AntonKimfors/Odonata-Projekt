package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ {

    private ArrayList<Quiz> quizList;
    private ArrayList<Statistics> quizStatisticsList;
    private Statistics globalStatistics;
    private Statistics currentQuizStatistics;
    private static KwizGeeQ singletonInstance = null;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
        }

        return singletonInstance;
    }

    private KwizGeeQ(){
        quizList = new ArrayList<Quiz>();
        quizStatisticsList = new ArrayList<Statistics>();
        globalStatistics = new Statistics();
    }

    public void startQuiz(){
        currentQuizStatistics = new Statistics();
    }

    public void endQuiz(){
        currentQuizStatistics.mergeInto(globalStatistics);
    }

    public Statistics getCurrentQuizStatistics() {
        return currentQuizStatistics;
    }

    public ArrayList<Quiz> getQuizList(){
        return quizList;
    }

    public ArrayList<Statistics> getQuizStatisticsList() {
        return quizStatisticsList;
    }

    public void updateQuizStatistics(int quizIndex) {
        int oldCorrectCount = quizStatisticsList.get(quizIndex).getAnswerCorrectCount();
        int newCorrectCount = currentQuizStatistics.getAnswerCorrectCount();

        if (newCorrectCount > oldCorrectCount) {
            currentQuizStatistics.mergeInto(quizStatisticsList.get(quizIndex));
        } else if (newCorrectCount == oldCorrectCount) {
            if (currentQuizStatistics.getSecondsSpent() > quizStatisticsList.get(quizIndex).getSecondsSpent()) {
                currentQuizStatistics.mergeInto(quizStatisticsList.get(quizIndex));
            }
        }
    }

    public Quiz getQuiz(int quizIndex){
        if(quizIndex<quizList.size()){
            return quizList.get(quizIndex);
        }

        throw new IndexOutOfBoundsException("Quiz on index "+ quizIndex +" does not exist.");
    }

    public ArrayList<Question> getQuestionList(int quizIndex){
        if(quizIndex < quizList.size()-1)
            return quizList.get(quizIndex).getQuestions();
        else
            throw new IndexOutOfBoundsException("Quiz on index "+ quizIndex +" does not exist.");
    }

    public void removeQuiz(Quiz quiz){
        quizList.remove(quiz);
    }

    public String getQuizName(int quizIndex){
        return quizList.get(quizIndex).getName();
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }
}
