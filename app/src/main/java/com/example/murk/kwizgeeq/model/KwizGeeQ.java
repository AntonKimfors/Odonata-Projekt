package com.example.murk.kwizgeeq.model;

import android.app.Activity;

import com.example.murk.kwizgeeq.events.EventBusWrapper;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;

import java.io.Serializable;
import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ implements Serializable{

    private ArrayList<UserQuiz> userQuizList;
    private Statistics globalStatistics;
    private static KwizGeeQ singletonInstance = null;
    private  KwizGeeQDataSource mKwizGeeQDataSource;

    public KwizGeeQ(Activity mainActivity){
        userQuizList = new ArrayList<UserQuiz>();
        globalStatistics = new Statistics();
        mKwizGeeQDataSource = new KwizGeeQDataSource(mainActivity.getApplicationContext());
        getDataFromDatabase();
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
        saveDataToDatabase();
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
        saveDataToDatabase();
    }

    public void replaceQuiz(int quizIndex, UserQuiz quiz) {
        userQuizList.set(quizIndex,quiz);
        EventBusWrapper.BUS.post(this);
        saveDataToDatabase();

    }



    public void saveDataToDatabase() {
        ArrayList<UserQuiz> tmpQuizList = new ArrayList<>(userQuizList);
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }


    public void getDataFromDatabase() {
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList(this);
        mKwizGeeQDataSource.close();
    }

    public void updateGlobalStatistics(UserQuiz quiz){
        quiz.getCurrentTempStatistics().mergeInto(globalStatistics);
        EventBusWrapper.BUS.post(this);
    }
}
