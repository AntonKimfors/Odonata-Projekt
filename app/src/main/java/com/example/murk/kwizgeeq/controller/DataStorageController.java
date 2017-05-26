package com.example.murk.kwizgeeq.controller;

import android.content.Context;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;

import java.util.ArrayList;


/**
 * Created by akimfors on 2017-05-26.
 */

public class DataStorageController {

    private static KwizGeeQ kwizGeeQ;
    private static KwizGeeQDataSource mKwizGeeQDataSource;

    protected DataStorageController(KwizGeeQ kwizGeeQ, Context context){
        this.kwizGeeQ = kwizGeeQ;
         mKwizGeeQDataSource = new KwizGeeQDataSource(context, kwizGeeQ);

    }


    public static void saveDataToDatabase() {
        ArrayList<UserQuiz> tmpQuizList = new ArrayList<>(kwizGeeQ.getUserQuizList());
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }


    public static void getDataFromDatabase() {
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList();
        mKwizGeeQDataSource.close();
    }
}
