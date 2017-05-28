package com.kwizgeeq.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;


import com.kwizgeeq.model.Answer;
import com.kwizgeeq.model.AnswerType;
import com.kwizgeeq.model.KwizGeeQ;

import com.kwizgeeq.model.Question;
import com.kwizgeeq.model.Statistics;



import com.kwizgeeq.model.UserQuiz;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-16.
 * @author Anton Kimfors
 * revised by Henrik Håkansson, Are Ehnberg and Marcus Olsson Lindvärn
 */


public class KwizGeeQDataSource {

    //private static Context mContext;
    private static KwizGeeQSQLiteHelper mQuizSqliteHelper;
    private SQLiteDatabase mDatabase;

    public KwizGeeQDataSource(Context context){
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
        deleteAll();

        try {
            for (int i = 0; i < userQuizArrayList.size(); i++) {
                ContentValues values = new ContentValues();

                values.put(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME, userQuizArrayList.get(i).getName());
                //TODO: parseToString? eller ok med "" + ?
                values.put(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_CORRECT, "" + userQuizArrayList.get(i).getBestStatistics().getAnswerCorrectCount());
                values.put(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_INCORRECT, "" + userQuizArrayList.get(i).getBestStatistics().getAnswerIncorrectCount());
                values.put(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_SECONDSSPENT,"" + userQuizArrayList.get(i).getBestStatistics().getSecondsSpent());
                values.put(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_QUIZCOUNT, "" + userQuizArrayList.get(i).getBestStatistics().getQuizCount());
                values.put(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_QUESTIONCOUNT, "" + userQuizArrayList.get(i).getBestStatistics().getQuestionCount());
                values.put(KwizGeeQSQLiteHelper.COLUMN_COLOR, "" + (userQuizArrayList.get(i).getListColor()));

                mDatabase.insert(KwizGeeQSQLiteHelper.TABLE_QUIZES, null, values);


                for(int y = 0; y < userQuizArrayList.get(i).getQuestions().size(); y++){

                    ContentValues questionValues = new ContentValues();
                    Question tmpQuestion = (Question) userQuizArrayList.get(i).getQuestion(y);
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_TITLE, tmpQuestion.getQuestionText());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_PICTURE, tmpQuestion.getImagePath());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_ANSWER_TYPE, "" + tmpQuestion.getOrderedList().get(0).getAnswerType());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_CORRECT_ANSWER, tmpQuestion.getOrderedList().get(0).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1, tmpQuestion.getOrderedList().get(1).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2, tmpQuestion.getOrderedList().get(2).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3, tmpQuestion.getOrderedList().get(3).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_FOREIGN_KEY_QUIZ, "" + i);
                    mDatabase.insert(KwizGeeQSQLiteHelper.ANNOTATIONS_TABLE, null, questionValues);

                }
            }
            mDatabase.setTransactionSuccessful();
        }
        finally {
            mDatabase.endTransaction();
        }
    } // - end insertQuizes


    // + select
    private Cursor selectAllQuizes(){
        Cursor cursor =  mDatabase.query(
                KwizGeeQSQLiteHelper.TABLE_QUIZES,
                new String[] {KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME, KwizGeeQSQLiteHelper.COLUMN_COLOR, KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_CORRECT,
                        KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_INCORRECT, KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_SECONDSSPENT,
                        KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_QUIZCOUNT, KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_QUESTIONCOUNT},  //Column names
                null, //where clause
                null, //where params
                null, //Grop by
                null, //having
                null //orderBy
        );

        return cursor;
    }
    // + update


    private Cursor selectAllQuestions(){
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
        updateCurrentListWithDatabaseQuizzes(mKwizGeeQ);
        updateCurrentListWithDatabaseQuestions(mKwizGeeQ);
    }



    private void updateCurrentListWithDatabaseQuizzes(KwizGeeQ mKwizGeeq){
        Cursor cursor = selectAllQuizes();

        ArrayList<UserQuiz> tmpQuizList = new ArrayList<>();
        int columnIndexName = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME);
        int columnIndexColor = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_COLOR);
        int columnIndexBestCorrect = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_CORRECT);
        int columnIndexBestIncorrect = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_INCORRECT);
        int columnIndexBestSeconds = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_SECONDSSPENT);
        int columnIndexBestQuizCount = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_QUIZCOUNT);
        int columnIndexBestQuestionCount = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_BEST_STATS_QUESTIONCOUNT);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            int tmpColorInt = Integer.parseInt(cursor.getString(columnIndexColor));
            String tmpName =  cursor.getString(columnIndexName);
            //int quizCount, int questionCount, int answerCorrectCount, int answerIncorrectCount, int secondsSpent
            Statistics tmpStats = new Statistics(Integer.parseInt(cursor.getString(columnIndexBestQuizCount)), Integer.parseInt(cursor.getString(columnIndexBestQuestionCount)),
                    Integer.parseInt(cursor.getString(columnIndexBestCorrect)), Integer.parseInt(cursor.getString(columnIndexBestIncorrect)),
                    Integer.parseInt(cursor.getString(columnIndexBestSeconds)));

            UserQuiz tmp = new UserQuiz(tmpName,  tmpColorInt, tmpStats);

            tmpQuizList.add(tmp);
            cursor.moveToNext();
        }
        mKwizGeeq.setUserQuizList(tmpQuizList);
    };

    //Borde istället för KwizGeeQ inte ta in något och istället retunera en lista med frågorna och svaren.
    // Som GlobalStatisticsDataSource gör med en Statistics

    private void updateCurrentListWithDatabaseQuestions(KwizGeeQ mKwizGeeq){
        Cursor cursor = selectAllQuestions();

        int columnIndexQuestion = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_TITLE);
        int columnIndexCorrect = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_CORRECT_ANSWER);
        int columnIndexIncorrect_1 = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1);
        int columnIndexIncorrect_2 = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2);
        int columnIndexIncorrect_3 = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3);
        int foreignKey = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_FOREIGN_KEY_QUIZ);

        int type = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_ANSWER_TYPE);
        int questionImage = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_PICTURE);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Question tmpQuestion = new Question();
            tmpQuestion.setQuestionText(cursor.getString(columnIndexQuestion));

            try {
                tmpQuestion.setImagePath(cursor.getString(questionImage));
            } catch (Exception e){System.out.println(e);}

            AnswerType answerType = AnswerType.TEXT;

            if(cursor.getString(type).equals("IMAGE")){
                answerType = AnswerType.IMAGE;
            }
            tmpQuestion.addAnswer(cursor.getString(columnIndexCorrect), true, answerType);
            tmpQuestion.addAnswer(cursor.getString(columnIndexIncorrect_1), false, answerType);
            tmpQuestion.addAnswer(cursor.getString(columnIndexIncorrect_2), false, answerType);
            tmpQuestion.addAnswer(cursor.getString(columnIndexIncorrect_3), false, answerType);


            mKwizGeeq.getUserQuizList().get(Integer.parseInt(cursor.getString(foreignKey))).getQuestions().add(tmpQuestion);
            cursor.moveToNext();
        }

    };

    // + delete
    private void deleteAll() {
        mDatabase.delete(
                KwizGeeQSQLiteHelper.ANNOTATIONS_TABLE,
                null, //where clause
                null //where params
        );
        mDatabase.delete(KwizGeeQSQLiteHelper.TABLE_QUIZES, null, null);
    };

}
