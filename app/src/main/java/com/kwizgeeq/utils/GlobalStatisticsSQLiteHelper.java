package com.kwizgeeq.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by akimfors on 2017-05-16.
 *
 * @author Anton Kimfors
 * revised by Henrik Håkansson, Are Ehnberg and Marcus Olsson Lindvärn
 */

public class GlobalStatisticsSQLiteHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "statisticsData.db";
    private static final int DB_VERSION = 1;


    //TODO: Add columns for Color and Assets(maybe) ---- CHECK ----
    //TODO: Assets should be for questions. Each question has title and maybe image. ---- CHECK ----
    //TODO: Also 4 answers. 4 text answers. Each Questions ---- CHECK ----
    //TODO: should also have an id to identify what quiz it belongs to. ---CHECK--

    //TODO: HUR SPARAS BIULDER??

    //------------- TABLE_STATISTICS ----------------------
    protected static final String TABLE_STATISTICS = "STATISTICS";
    protected static final String COLUMN_GLOBAL_STATS_CORRECT = "BEST_CORRECT";
    protected static final String COLUMN_GLOBAL_STATS_INCORRECT = "BEST_INCORRECT";
    protected static final String COLUMN_GLOBAL_STATS_SECONDSSPENT = "BEST_SECONDSSPENT";
    protected static final String COLUMN_GLOBAL_STATS_QUIZCOUNT = "BEST_QUIZCOUNT";
    protected static final String COLUMN_GLOBAL_STATS_QUESTIONCOUNT = "BEST_QUESTIONCOUNT";



    private static String DB_CREATE =
            "CREATE TABLE " + TABLE_STATISTICS + " ("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_GLOBAL_STATS_CORRECT + " INTEGER,"
                    + COLUMN_GLOBAL_STATS_INCORRECT + " INTEGER,"
                    + COLUMN_GLOBAL_STATS_SECONDSSPENT + " INTEGER,"
                    + COLUMN_GLOBAL_STATS_QUIZCOUNT + " INTEGER,"
                    + COLUMN_GLOBAL_STATS_QUESTIONCOUNT + " INTEGER" + ")";



    public GlobalStatisticsSQLiteHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
