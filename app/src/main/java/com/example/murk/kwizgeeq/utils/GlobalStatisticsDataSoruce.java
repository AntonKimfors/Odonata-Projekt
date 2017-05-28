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

    //private static Context mContext;
    private static GlobalStatisticsSQLiteHelper globalStatisticsSQLiteHelper;
    private SQLiteDatabase mDatabase;


    public GlobalStatisticsDataSoruce(Context context){
        //mContext = context;
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
    public void insertGlobalStats(Statistics globalStatistics) {
        //deleteAll();

        mDatabase.beginTransaction();
        deleteAll();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_CORRECT, "" + globalStatistics.getAnswerCorrectCount());
            contentValues.put(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_INCORRECT, "" + globalStatistics.getAnswerIncorrectCount());
            contentValues.put(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_QUESTIONCOUNT, "" +globalStatistics.getQuestionCount());
            contentValues.put(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_QUIZCOUNT, "" + globalStatistics.getQuizCount());
            contentValues.put(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_SECONDSSPENT, "" + globalStatistics.getSecondsSpent());
            mDatabase.insert(GlobalStatisticsSQLiteHelper.TABLE_STATISTICS, null, contentValues);
            mDatabase.setTransactionSuccessful();
        }
        finally {
            mDatabase.endTransaction();
        }
    } // - end insertQuizes


    // + select
    private Cursor selectAllStatistics(){
        Cursor cursor =  mDatabase.query(
                GlobalStatisticsSQLiteHelper.TABLE_STATISTICS,
                new String[] {GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_CORRECT, GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_INCORRECT,
                        GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_QUIZCOUNT, GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_QUESTIONCOUNT,
                        GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_SECONDSSPENT },  //Column names
                null, //where clause
                null, //where params
                null, //Grop by
                null, //having
                null //orderBy
        );

        return cursor;
    }
    // + update


    public Statistics updateCurrentStatsWithDatabaseStats() {
        Cursor cursor = selectAllStatistics();

        int statsCorrect = cursor.getColumnIndex(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_CORRECT);
        int statsIncorrect = cursor.getColumnIndex(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_INCORRECT);
        int statsQuestionCount = cursor.getColumnIndex(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_QUESTIONCOUNT);
        int statsQuizCount = cursor.getColumnIndex(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_QUIZCOUNT);
        int statsSecondsSpent = cursor.getColumnIndex(GlobalStatisticsSQLiteHelper.COLUMN_GLOBAL_STATS_SECONDSSPENT);


        cursor.moveToFirst();
        //Statistics tmp = new Statistics();

        try {
            while (!cursor.isAfterLast()) {


                int tmpCorrectAnswer = Integer.parseInt(cursor.getString(statsCorrect));
                int tmpInCorrectAnswer = Integer.parseInt(cursor.getString(statsIncorrect));
                int tmpQuestionCount = Integer.parseInt(cursor.getString(statsQuestionCount));
                int tmpQuizCount = Integer.parseInt(cursor.getString(statsQuizCount));
                int tmpSecondsSpent = Integer.parseInt(cursor.getString(statsSecondsSpent));

                Statistics tmp = new Statistics(tmpQuizCount, tmpQuestionCount, tmpCorrectAnswer, tmpInCorrectAnswer, tmpSecondsSpent);

                cursor.moveToNext();
                return tmp;

            }
        } catch (Exception e) {
            System.out.println(e);
    }
        return new Statistics();
    }



// + delete
    private void deleteAll() {
        mDatabase.delete(
                GlobalStatisticsSQLiteHelper.TABLE_STATISTICS,
                null, //where clause
                null //where params
        );
    }
}

