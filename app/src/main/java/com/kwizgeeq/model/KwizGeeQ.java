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

    private ArrayList<Quiz> quizList;
    private Statistics globalStatistics;
    private  KwizGeeQDataSource mKwizGeeQDataSource;
    private GlobalStatisticsDataSoruce mGlobalStatisticsDataSoruce;

    public KwizGeeQ(Activity mainActivity){
        quizList = new ArrayList<Quiz>();
        globalStatistics = new Statistics();
        mKwizGeeQDataSource = new KwizGeeQDataSource(mainActivity.getApplicationContext());
        mGlobalStatisticsDataSoruce = new GlobalStatisticsDataSoruce(mainActivity.getApplicationContext());
        getDataFromDatabase();
    }

    public ArrayList<Quiz> getQuizList(){
        return quizList;
    }

    public void setQuizList(ArrayList<Quiz> quizList){
        this.quizList = quizList;
    }

    public void removeQuiz(int quizIndex){
        quizList.remove(quizIndex);
        EventBusWrapper.BUS.post(this);
        saveQuizDataToDatabase();
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }

    public void addQuiz(Quiz quiz) {
        quizList.add(quiz);
        EventBusWrapper.BUS.post(this);
        saveQuizDataToDatabase();
    }

    public void replaceQuiz(int quizIndex, Quiz quiz) {
        quizList.set(quizIndex,quiz);
        EventBusWrapper.BUS.post(this);
        saveQuizDataToDatabase();

    }

    private void saveQuizDataToDatabase() {
        ArrayList<Quiz> tmpQuizList = new ArrayList<>(quizList);
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

    public void updateGlobalStatistics(Quiz quiz){
        quiz.getCurrentTempStatistics().mergeInto(globalStatistics);
        quiz.resetCurrentTempStatistics();
        EventBusWrapper.BUS.post(this);
        saveStatisticsDataToDatabase();
    }
}
