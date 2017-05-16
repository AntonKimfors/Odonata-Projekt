package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by akimfors on 2017-05-16.
 */

public class QuizSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "data.db";
    private static final int DB_VERSION = 1;

    //QUIZ Table Functionallity
    public static final String QUIZES_TABLE = "QUIZES";
    public static final String COLUMN_QUIZ_ASSET = "ASSET";
    public static final String COLUMN_QUIZ_NAME = "NAME";
    private static String CREATE_QUIZES =
                    "CREATE TABLE " + QUIZES_TABLE + "(" + BaseColumns._ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_QUIZ_ASSET +
                    " TEXT, " + COLUMN_QUIZ_NAME + " TEXT)";

    //  Table Annotations functionallity
    public static final String ANNOTATIONS_TABLE = "ANNOTATIONS";
    public static final String COLUMN_ANNOTATION_COLOR = "COLOR";
    public static final String COLUMN_ANNOTATION_TITLE = "TITLE";
    public static final String COLUMN_ANNOTATION_QUIZLIST_INDEX = "QUIZLIST_INDEX";
    public static final String COLUMN_FOREIGN_KEY_QUIZES = "QUIZES_ID";
    private static final String CREATE_ANNOTATIONS = "CREATE TABLE " + ANNOTATIONS_TABLE + "(" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ANNOTATION_COLOR + " INTEGER, " +
            COLUMN_ANNOTATION_QUIZLIST_INDEX + " INTEGER, " +
            COLUMN_ANNOTATION_TITLE + " TEXT, " +
            " FOREIGN KEY(" + COLUMN_FOREIGN_KEY_QUIZES + ") REFERENCES QUIZES(_ID))";


    public QuizSQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUIZES);
        sqLiteDatabase.execSQL(CREATE_ANNOTATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
