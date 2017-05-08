package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.murk.kwizgeeq.CreateQuiz;
import com.example.murk.kwizgeeq.QuizList;
import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.activity.QuestioneerActivity;
import com.example.murk.kwizgeeq.activity.QuizListActivity;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.QuizListAdapter;

import java.util.Observable;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.example.murk.kwizgeeq.R.id.parent;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListView extends Observable {

    public ListView listView;

    private KwizGeeQ model;
    private QuizListAdapter adapter;


    public QuizListView(ListView listView, final Context context, final NavigatableActivity oldActivity){
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();

        QuizListAdapter adapter = new QuizListAdapter(context, model.quizzList);
        listView.setAdapter(adapter);

        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position); //Make the clicked quiz active quiz.

                Intent intent = new Intent(context, QuestioneerActivity.class);
                oldActivity.startActivity(intent);
            }
        });


        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position);
                Intent intent = new Intent(context, CreateQuiz.class);
                oldActivity.startActivity(intent);

                return true;
            }
        });


    }




}
