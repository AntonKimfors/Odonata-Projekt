package com.kwizgeeq.model;

import android.app.Activity;

import com.kwizgeeq.events.EventBusWrapper;
import com.kwizgeeq.storageUtilities.GlobalStatisticsDataSoruce;
import com.kwizgeeq.storageUtilities.KwizGeeQDataSource;

import java.io.Serializable;
import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 *
 * @author Anton Kimfors
 * revised by Henrik Håkansson, Are Ehnberg and Marcus Olsson Lindvärn
 */

public class KwizGeeQ implements Serializable{

    private ArrayList<UserQuiz> userQuizList;
    private Statistics globalStatistics;
    private  KwizGeeQDataSource mKwizGeeQDataSource;
    private GlobalStatisticsDataSoruce mGlobalStatisticsDataSoruce;

    public KwizGeeQ(Activity mainActivity){
        userQuizList = new ArrayList<UserQuiz>();
        globalStatistics = new Statistics();
        mKwizGeeQDataSource = new KwizGeeQDataSource(mainActivity.getApplicationContext());
        mGlobalStatisticsDataSoruce = new GlobalStatisticsDataSoruce(mainActivity.getApplicationContext());
        getDataFromDatabase();
    }

    public ArrayList<UserQuiz> getUserQuizList(){
        return userQuizList;
    }

    public void setUserQuizList(ArrayList<UserQuiz> userQuizList){
        this.userQuizList = userQuizList;
    }

    public void removeQuiz(int quizIndex){
        userQuizList.remove(quizIndex);
        EventBusWrapper.BUS.post(this);
        saveQuizDataToDatabase();
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }

    public void addQuiz(UserQuiz quiz) {
        userQuizList.add(quiz);
        EventBusWrapper.BUS.post(this);
        saveQuizDataToDatabase();
    }

    public void replaceQuiz(int quizIndex, UserQuiz quiz) {
        userQuizList.set(quizIndex,quiz);
        EventBusWrapper.BUS.post(this);
        saveQuizDataToDatabase();

    }

    private void saveQuizDataToDatabase() {
        ArrayList<UserQuiz> tmpQuizList = new ArrayList<>(userQuizList);
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }

    private void saveStatisticsDataToDatabase() {
        mGlobalStatisticsDataSoruce.open();
        mGlobalStatisticsDataSoruce.insertGlobalStats(globalStatistics);
        mGlobalStatisticsDataSoruce.close();
    }

    private void getDataFromDatabase() {
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList(this);
        mKwizGeeQDataSource.close();

        mGlobalStatisticsDataSoruce.open();
        this.globalStatistics = mGlobalStatisticsDataSoruce.updateCurrentStatsWithDatabaseStats();
        mGlobalStatisticsDataSoruce.close();
    }

    public void updateGlobalStatistics(UserQuiz quiz){
        quiz.getCurrentTempStatistics().mergeInto(globalStatistics);
        quiz.resetCurrentTempStatistics();
        EventBusWrapper.BUS.post(this);
        saveStatisticsDataToDatabase();
    }
}
