package com.example.murk.kwizgeeq.activity;

import android.app.ListActivity;
import android.database.Cursor;

import android.databinding.DataBindingUtil;

import android.graphics.Color;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.QuizListController;
import com.example.murk.kwizgeeq.databinding.ActivityEditQuizBinding;
import com.example.murk.kwizgeeq.databinding.ActivityQuizListBinding;
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
    //private ArrayList<String> mQuizNames;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);

        ActivityQuizListBinding binding;
        binding = DataBindingUtil.setContentView(this,R.layout.activity_quiz_list);


        mKwizGeeQDataSource = new KwizGeeQDataSource(QuizListActivity.this);

        view = new QuizListView(getListView(), this, this, EditQuizActivity.class,
                QuestioneerActivity.class, (FloatingActionButton) findViewById(R.id.fab), mKwizGeeQDataSource);
        controller = new QuizListController(view, this, this);

        binding.setController(controller);
        view.addObserver(controller);
        controller.onCreate();

    }


    //TODO: TROR KLAR - for sql i alla fall
    @Override
    protected void onPause() {
        super.onPause();

        ArrayList<Quiz> tmpQuizList= KwizGeeQ.getInstance().getQuizList();
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.insertQuizes(tmpQuizList);
        mKwizGeeQDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mKwizGeeQDataSource.open();
        mKwizGeeQDataSource.updateList();
        mKwizGeeQDataSource.close();
    }


    //SEEMS TO WORK FOR NOW - not using global
    public void updateList(Cursor cursor){
        ArrayList<Quiz> tmpQuizList = new ArrayList<Quiz>();


        int columnIndexName = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_QUIZ_NAME);
        int columnIndexColor = cursor.getColumnIndex(KwizGeeQSQLiteHelper.COLUMN_COLOR);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            /*String s = cursor.getString(columnIndex); //Spara namnet för ett quiz
            mNames.add(s); //lägg till namnet i listan för namn*/
            //int tmpInt = Integer.parseInt(cursor.getString(columnIndexColor));
            //UserQuiz tmp = new UserQuiz(cursor.getString(columnIndexName), tmpInt);
            //tmpQuizList.add(tmp);
            cursor.moveToNext();
        }


        //KwizGeeQ.getInstance().setQuizList(tmpQuizList);

        /*ArrayList<Quiz> tempList = oldQuizes(mNames); //Kalla oldQuizes med listan för namn
        KwizGeeQ.getInstance().setQuizList(tempList); //Sätt quizList till den nya listan*/


    }

    //JUST FOR NOW: Creates a new quizlist from a String[] of quiz names

    private ArrayList<UserQuiz> oldQuizes() {
        ArrayList<UserQuiz> oldQuizes = new ArrayList<>();
        for(int i = 0; i < mQuizNames.size() - 1; i++){
            oldQuizes.add(new UserQuiz(mQuizNames.get(i), 3) {
            });

    private ArrayList<Quiz> oldQuizes(ArrayList<String> mNames) {
        ArrayList<Quiz> oldQuizes = new ArrayList<>(); //Skapa en ny quizlist
        for(int i = 0; i < mNames.size(); i++){

            //Skapa ett quiz med namnet från namnlistan och lägg till i ny quizlist
            oldQuizes.add(new UserQuiz(mNames.get(i), Color.BLUE));


    }


}
