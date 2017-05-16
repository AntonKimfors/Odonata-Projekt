package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;

/**
 * Created by akimfors on 2017-05-16.
 */

public class QuizDataSource {

    private Context mContext;
    private KwizGeeQSQLiteHelper mQuizSqliteHelper;
    private SQLiteDatabase mDatabase;

    public QuizDataSource(Context context){
        mContext = context;
        mQuizSqliteHelper = new KwizGeeQSQLiteHelper(context);
        //mQuizSqliteHelper.getReadableDatabase();
        //database.close();

    }


    // open
    public void open() throws SQLException {
        mDatabase = mQuizSqliteHelper.getWritableDatabase();
    }

    // close

    // insert

    // select

    // update

    // delete

}
