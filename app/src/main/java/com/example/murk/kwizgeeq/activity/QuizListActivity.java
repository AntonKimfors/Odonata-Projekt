package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.example.murk.kwizgeeq.utils.KwizGeeQSQLiteHelper;
import com.example.murk.kwizgeeq.view.QuizListView;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListActivity extends ListActivity {

    public KwizGeeQDataSource mKwizGeeQDataSource; //TODO: Make it private. Take a look on Data for MVC!
    private QuizListController controller;
    private QuizListView view;
    private ArrayList<String> mQuizNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_quiz_list);

        mKwizGeeQDataSource = new KwizGeeQDataSource(QuizListActivity.this);

        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab), mKwizGeeQDataSource);
        controller = new QuizListController(view, this, this);
        view.addObserver(controller);
        controller.onCreate();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //mKwizGeeQDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mKwizGeeQDataSource.open();

        Cursor cursor = mKwizGeeQDataSource.selectAllQuizes();
        updateList(cursor);*/
    }

    protected void updateList(Cursor cursor){
        mQuizNames = null;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int columnIndex = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME);
            String name = cursor.getColumnName(columnIndex);

            mQuizNames.add(name);

            cursor.moveToNext();
        }

        ArrayList<UserQuiz> tempList = oldQuizes();

        KwizGeeQ.getInstance().setUserQuizList(tempList);

    }

    //JUST FOR NOW: Creates a new quizlist from a String[] of quiz names
    private ArrayList<UserQuiz> oldQuizes() {
        ArrayList<UserQuiz> oldQuizes = new ArrayList<>();
        for(int i = 0; i < mQuizNames.size() - 1; i++){
            oldQuizes.add(new UserQuiz(mQuizNames.get(i), 3) {
            });
        }

        return oldQuizes;
    }

}
