package com.example.murk.kwizgeeq.model;

import java.io.Serializable;
import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ implements Serializable{

    private ArrayList<UserQuiz> userQuizList;
    private Statistics globalStatistics;
    private static KwizGeeQ singletonInstance = null;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
        }

        return singletonInstance;
    }

    private KwizGeeQ(){
        userQuizList = new ArrayList<UserQuiz>();
        globalStatistics = new Statistics();
    }

    public void updateGlobalStatistics(UserQuiz quiz){
        quiz.getCurrentTempStatistics().mergeInto(globalStatistics);
    }

    public ArrayList<UserQuiz> getUserQuizList(){
        return userQuizList;
    }

    public void setUserQuizList(ArrayList<UserQuiz> userQuizList){
        this.userQuizList = userQuizList;
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
