package com.example.murk.kwizgeeq.model;

import com.example.murk.kwizgeeq.events.EventBusWrapper;

import java.io.Serializable;
import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ implements Serializable{

    private ArrayList<UserQuiz> userQuizList;
    private Statistics globalStatistics;
    private static KwizGeeQ singletonInstance = null;

    /*
    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
        }

        return singletonInstance;
    }*/

    public KwizGeeQ(){
        userQuizList = new ArrayList<UserQuiz>();
        globalStatistics = new Statistics();
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

    public void removeQuiz(int quizIndex){
        userQuizList.remove(quizIndex);
        EventBusWrapper.BUS.post(this);
    }

    public String getQuizName(int quizIndex){
        return userQuizList.get(quizIndex).getName();
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }

    public void addQuiz(UserQuiz quiz) {
        userQuizList.add(quiz);
        EventBusWrapper.BUS.post(this);
    }

    public void replaceQuiz(int quizIndex, UserQuiz quiz) {
        userQuizList.set(quizIndex,quiz);
        EventBusWrapper.BUS.post(this);
    }

    public void updateGlobalStatistics(UserQuiz quiz){
        quiz.getCurrentTempStatistics().mergeInto(globalStatistics);
        EventBusWrapper.BUS.post(this);
    }
}
