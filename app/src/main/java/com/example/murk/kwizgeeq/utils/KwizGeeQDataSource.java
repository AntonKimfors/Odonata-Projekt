package com.example.murk.kwizgeeq.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;

import com.example.murk.kwizgeeq.model.Quiz;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-16.
 */

public class KwizGeeQDataSource {

    private Context mContext;
    private KwizGeeQSQLiteHelper mQuizSqliteHelper;
    private SQLiteDatabase mDatabase;

    public KwizGeeQDataSource(Context context){
        mContext = context;
        mQuizSqliteHelper = new KwizGeeQSQLiteHelper(context);
    }


    // + open
    public void open() throws SQLException {
        mDatabase = mQuizSqliteHelper.getWritableDatabase();
    }

    // + close
    public void close(){
        mDatabase.close();
    }

    // + insert
    public void insertQuizes(ArrayList<Quiz> quizArrayList) {

        //TODO: Hur funkar det här? Behövs en loop?
        mDatabase.beginTransaction();

        try {
            for (int i = 0; i < quizArrayList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME, quizArrayList.get(i).getName());
                mDatabase.insert(KwizGeeQSQLiteHelper.TABLE_QUIZES, null, values);
            }
            mDatabase.setTransactionSuccessful();
        }
        finally {
            mDatabase.endTransaction();
        }
    } // - end insertQuizes

    // + select
        public Cursor selectAllQuizes(){
            Cursor cursor = mDatabase.query(
                KwizGeeQSQLiteHelper.TABLE_QUIZES,
                new String[] { KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME},  //Column names
                null, //where clause
                    null, //where params
                    null, //Grop by
                    null, //having
                    null //orderBy
            );

            return cursor;
        }

    // + update
    public int updateQuizes(String newName){
        ContentValues values = new ContentValues();

        values.put(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME, newName);
        int rowsUpdated = mDatabase.update(
                KwizGeeQSQLiteHelper.TABLE_QUIZES, //Table
                values, //values
                null,   //where clause
                null   

        );

        return 1;
    }

    // + delete

}
