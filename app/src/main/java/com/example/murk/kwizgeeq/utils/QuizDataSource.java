package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;

/**
 * Created by akimfors on 2017-05-16.
 */

public class QuizDataSource {

    private Context mContext;
    private QuizSQLiteHelper mQuizSqliteHelper;

    public QuizDataSource(Context context){

        mContext = context;
        mQuizSqliteHelper = new QuizSQLiteHelper(context);
        SQLiteDatabase database = mQuizSqliteHelper.getReadableDatabase();
        database.close();

    }
}
