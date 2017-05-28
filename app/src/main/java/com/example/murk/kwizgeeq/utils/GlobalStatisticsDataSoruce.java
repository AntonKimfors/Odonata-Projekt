package com.example.murk.kwizgeeq.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.Statistics;
import com.example.murk.kwizgeeq.model.UserQuiz;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-28.
 */

public class GlobalStatisticsDataSoruce {

    private static Context mContext;
    private static GlobalStatisticsSQLiteHelper globalStatisticsSQLiteHelper;
    private SQLiteDatabase mDatabase;


    public GlobalStatisticsDataSoruce(Context context){
        mContext = context;
        globalStatisticsSQLiteHelper = new GlobalStatisticsSQLiteHelper(context);
    }

    // + open
    public void open() throws SQLException {
        mDatabase = globalStatisticsSQLiteHelper.getWritableDatabase();
    }

    // + close
    public void close(){
        mDatabase.close();
    }

    // + insert
    public void insertQuizes() {
        //deleteAll();

        mDatabase.beginTransaction();
        //try
        mDatabase.setTransactionSuccessful();
        //finally
            mDatabase.endTransaction();

    } // - end insertQuizes


    // + select
    public Cursor selectAllStatistics(){
        Cursor cursor =  mDatabase.query(
                GlobalStatisticsSQLiteHelper.TABLE_STATISTICS,
                new String[] {},  //Column names
                null, //where clause
                null, //where params
                null, //Grop by
                null, //having
                null //orderBy
        );

        return cursor;
    }
    // + update


    public Cursor selectAllQuestions(){
        Cursor cursor =  mDatabase.query(
                KwizGeeQSQLiteHelper.ANNOTATIONS_TABLE,
                new String[] {KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_TITLE, BaseColumns._ID, KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_CORRECT_ANSWER,
                        KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1, KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2,
                        KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3, KwizGeeQSQLiteHelper.COLUMN_FOREIGN_KEY_QUIZ, KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_ANSWER_TYPE, KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_PICTURE},  //Column names
                null, //where clause
                null, //where params
                null, //Grop by
                null, //having
                null //orderBy
        );
        return cursor;

    }


    public void updateList(KwizGeeQ mKwizGeeQ){
        updateCurrentStatsWithDatabaseStats(mKwizGeeQ);

    }



    public void updateCurrentStatsWithDatabaseStats(KwizGeeQ mKwizGeeq){
        Cursor cursor = selectAllStatistics();


        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            cursor.moveToNext();
        }

    }



// + delete
    public void deleteAll() {

    }
}

