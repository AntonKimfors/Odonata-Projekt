package com.example.murk.kwizgeeq.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
<<<<<<< HEAD
=======
import android.provider.BaseColumns;
import android.view.ContextMenu;
>>>>>>> Done qith sqlite classes, need to implement when to save and read data

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.AnswerType;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
<<<<<<< HEAD

=======
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
>>>>>>> Done qith sqlite classes, need to implement when to save and read data
import com.example.murk.kwizgeeq.model.UserQuiz;

import org.w3c.dom.Text;

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

                //TODO: parseToString?
                values.put(KwizGeeQSQLiteHelper.COLUMN_COLOR, "" + (quizArrayList.get(i).getListColor()));
                mDatabase.insert(KwizGeeQSQLiteHelper.TABLE_QUIZES, null, values);

                for(int y = 0; y < quizArrayList.get(i).getQuestions().size(); y++){

                    ContentValues questionValues = new ContentValues();


                    UserQuestion tmpQuestion = (UserQuestion) quizArrayList.get(i).getQuestion(y);
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_TITLE, tmpQuestion.getQuestionText());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_CORRECT_ANSWER, tmpQuestion.getOrderedList().get(0).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1, tmpQuestion.getOrderedList().get(1).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2, tmpQuestion.getOrderedList().get(2).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3, tmpQuestion.getOrderedList().get(3).getData());
                    questionValues.put(KwizGeeQSQLiteHelper.COLUMN_FOREIGN_KEY_QUIZ, "" + i);
                    mDatabase.insert(KwizGeeQSQLiteHelper.TABLE_QUIZES, null, questionValues);
                }
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
                new String[] {KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME, (String) KwizGeeQSQLiteHelper.COLUMN_COLOR},  //Column names
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
                new String[] {KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_TITLE , BaseColumns._ID, KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_CORRECT_ANSWER,
                        KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1, KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2,
                        KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3, KwizGeeQSQLiteHelper.COLUMN_FOREIGN_KEY_QUIZ},  //Column names
                null, //where clause
                null, //where params
                null, //Grop by
                null, //having
                null //orderBy
        );
        return cursor;

    }


    public void updateList(){

        updateCurrentListWithDatabaseQuizzes();
        updateCurrentListWithDatabaseQuestions();
    }

    public void updateCurrentListWithDatabaseQuizzes(){
        Cursor cursor = selectAllQuizes();
        //String[] ny = cursor.getColumnNames();

        ArrayList<Quiz> tmpQuizList = new ArrayList<Quiz>();
        int columnIndexName = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME);
        int columnIndexColor = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_COLOR);
        cursor.moveToFirst();

        while (cursor.moveToNext()){
            int tmpInt = Integer.parseInt(cursor.getString(columnIndexColor));
            UserQuiz tmp = new UserQuiz(cursor.getString(columnIndexName),  tmpInt);
            tmpQuizList.add(tmp);
            cursor.moveToNext();
        }
        KwizGeeQ.getInstance().setQuizList(tmpQuizList);
    };



    public void updateCurrentListWithDatabaseQuestions(){
        Cursor cursor = selectAllQuestions();
        //ArrayList<Answer> tmpAnswerList = new ArrayList<>();

        int columnIndexQuestion = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATION_TITLE);
        int columnIndexCorrect = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_CORRECT_ANSWER);
        int columnIndexIncorrect_1 = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_1);
        int columnIndexIncorrect_2 = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_2);
        int columnIndexIncorrect_3 = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_ANNOTATIONS_INCORRECT_ANSWER_3);
        int foreignKey = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_FOREIGN_KEY_QUIZ);

        cursor.moveToFirst();

        while (cursor.moveToNext()){
            UserQuestion tmpQuestion = new UserQuestion();
            tmpQuestion.setQuestionText(cursor.getString(columnIndexQuestion));
            tmpQuestion.addAnswer(cursor.getString(columnIndexCorrect), true, AnswerType.TEXT);
            tmpQuestion.addAnswer(cursor.getString(columnIndexIncorrect_1), false, AnswerType.TEXT);
            tmpQuestion.addAnswer(cursor.getString(columnIndexIncorrect_2), false, AnswerType.TEXT);
            tmpQuestion.addAnswer(cursor.getString(columnIndexIncorrect_3), false, AnswerType.TEXT);
            KwizGeeQ.getInstance().getQuizList().get(Integer.parseInt(cursor.getString(foreignKey))).addQuestion(tmpQuestion);
        }

    };




    // + delete
    public void deleteAll() {
        mDatabase.delete(
            KwizGeeQSQLiteHelper.TABLE_QUIZES,
            null, //where clause
            null //where params
        );
    };

}
