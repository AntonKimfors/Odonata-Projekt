package com.example.murk.kwizgeeq.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.murk.kwizgeeq.model.KwizGeeQ;

import com.example.murk.kwizgeeq.model.UserQuiz;

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
    public void insertQuizes(ArrayList<UserQuiz> userQuizArrayList) {
        mDatabase.beginTransaction();

        try {
            for (int i = 0; i < userQuizArrayList.size(); i++) {
                ContentValues values = new ContentValues();

                values.put(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME, quizArrayList.get(i).getName());
                values.put(KwizGeeQSQLiteHelper.COLUMN_COLOR, quizArrayList.get(i).getListColor());

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
            Cursor cursor =  mDatabase.query(
                KwizGeeQSQLiteHelper.TABLE_QUIZES,
                new String[] {KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME/*, KwizGeeQSQLiteHelper.COLUMN_COLOR*/},  //Column names
                    null, //where clause
                    null, //where params
                    null, //Grop by
                    null, //having
                    null //orderBy
            );

            return cursor;
        }

    // + update


    public void updateList(){

        Cursor cursor = selectAllQuizes();

        ArrayList<Quiz> tmpQuizList = new ArrayList<Quiz>();


        int columnIndexName = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME);
        //int columnIndexColor = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_COLOR);

        cursor.moveToFirst();

        while (cursor.moveToNext()){
            //int tmpInt = Integer.parseInt(cursor.getString(columnIndexColor));
            //UserQuiz tmp = new UserQuiz(cursor.getString(columnIndexName), 999999999/*tmpInt*/);
            //tmpQuizList.add(tmp);
            cursor.moveToNext();
        }

        KwizGeeQ.getInstance().setQuizList(tmpQuizList);
    }

    // + delete
    public void deleteAll() {
        mDatabase.delete(
            KwizGeeQSQLiteHelper.TABLE_QUIZES,
            null, //where clause
            null //where params
        );
    };

}
