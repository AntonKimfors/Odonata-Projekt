package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by akimfors on 2017-05-16.
 */

public class KwizGeeQSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "data.db";
    private static final int DB_VERSION = 1;


    //TODO: Add columns for Color and Assets(maybe) ---- CHECK ----
    //TODO: Assets should be for questions. Each question has title and maybe image. ---- CHECK ----
    //TODO: Also 4 answers. 4 text answers. Each Questions ---- CHECK ----
    //TODO: should also have an id to identify what quiz it belongs to. ---CHECK--

    //TODO: HUR SPARAS BIULDER??

    //-------------QUIZ Table ----------------------
    protected static final String TABLE_QUIZES = "QUIZES";
    protected static final String COLUMN_COLOR = "COLOR";
    protected static final String COLUMN_QUIZ_NAME = "QUIZ_NAME";
    protected static final String COLUMN_BEST_STATS_CORRECT = "BEST_CORRECT";
    protected static final String COLUMN_BEST_STATS_INCORRECT = "BEST_INCORRECT";
    protected static final String COLUMN_BEST_STATS_SECONDSSPENT = "BEST_SECONDSSPENT";
    protected static final String COLUMN_BEST_STATS_QUIZCOUNT = "BEST_QUIZCOUNT";
    protected static final String COLUMN_BEST_STATS_QUESTIONCOUNT = "BEST_QUESTIONCOUNT";


    private static String DB_CREATE =
            "CREATE TABLE " + TABLE_QUIZES + " ("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_QUIZ_NAME + " TEXT,"
                    + COLUMN_BEST_STATS_CORRECT + " INTEGER,"
                    + COLUMN_BEST_STATS_INCORRECT + " INTEGER,"
                    + COLUMN_BEST_STATS_SECONDSSPENT + " INTEGER,"
                    + COLUMN_BEST_STATS_QUIZCOUNT + " INTEGER,"
                    + COLUMN_BEST_STATS_QUESTIONCOUNT + " INTEGER,"
                    + COLUMN_COLOR + " INTEGER" + ")";


    //-------------QUESTION Table---------------------
    protected static final String ANNOTATIONS_TABLE = "QUESTIONS";
    protected static final String COLUMN_ANNOTATION_PICTURE = "PICTURE";
    protected static final String COLUMN_ANNOTATION_TITLE = "QUESTION";
    protected static final String COLUMN_ANNOTATIONS_CORRECT_ANSWER = "CORRECT_ANSWER";
    protected static final String COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1 = "INCORRECT_ANSWER_1";
    protected static final String COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2 = "INCORRECT_ANSWER_2";
    protected static final String COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3 = "INCORRECT_ANSWER_3";
    protected static final String COLUMN_ANNOTATIONS_ANSWER_TYPE = "ANSWER_TYPE";
    protected static final String COLUMN_FOREIGN_KEY_QUIZ = "QUIZ_ID";

    //TODO: quiZZes i plural
    private static final String CREATE_ANNOTATIONS = "CREATE TABLE " + ANNOTATIONS_TABLE + " (" +
            BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ANNOTATION_TITLE + " TEXT, " +
            COLUMN_ANNOTATION_PICTURE + " TEXT, " +
            COLUMN_ANNOTATIONS_CORRECT_ANSWER + " TEXT, " +
            COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1 + " TEXT, " +
            COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2 + " TEXT, " +
            COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3 + " TEXT, " +
            COLUMN_ANNOTATIONS_ANSWER_TYPE + " TEXT, " +
            COLUMN_FOREIGN_KEY_QUIZ + " INTEGER, " +
            "FOREIGN KEY(" + COLUMN_FOREIGN_KEY_QUIZ + ") REFERENCES QUIZES(_ID))";



    protected KwizGeeQSQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        db.execSQL(CREATE_ANNOTATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
