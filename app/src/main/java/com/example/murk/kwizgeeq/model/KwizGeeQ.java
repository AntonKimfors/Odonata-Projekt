package com.example.murk.kwizgeeq.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ implements Serializable{

    private ArrayList<UserQuiz> userQuizList;
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
        userQuizList = new ArrayList<UserQuiz>();
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

    public ArrayList<UserQuiz> getUserQuizList(){
        return userQuizList;
    }

    public void setUserQuizList(ArrayList<UserQuiz> userQuizList){
        this.userQuizList = userQuizList;
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

    public UserQuiz getQuiz(int quizIndex){
        if(quizIndex< userQuizList.size()){
            return userQuizList.get(quizIndex);
        }

        throw new IndexOutOfBoundsException("UserQuiz on index "+ quizIndex +" does not exist.");
    }

    public ArrayList<Question> getQuestionList(int quizIndex){
        if(quizIndex < userQuizList.size()-1)
            return userQuizList.get(quizIndex).getQuestions();
        else
            throw new IndexOutOfBoundsException("UserQuiz on index "+ quizIndex +" does not exist.");
    }

    public void removeQuiz(UserQuiz userQuiz){
        userQuizList.remove(userQuiz);
    }

    public String getQuizName(int quizIndex){
        return userQuizList.get(quizIndex).getName();
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }
}
