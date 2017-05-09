package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.murk.kwizgeeq.CreateQuiz;

import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.activity.QuestioneerActivity;
import com.example.murk.kwizgeeq.activity.QuizListActivity;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.QuizListAdapter;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;

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


    public QuizListView(final ListView listView, final Context context, final NavigatableActivity oldActivity){
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();



        this.adapter = new QuizListAdapter(context, model.quizzList);
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
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) oldActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Edit Quiz?")
                        .setMessage("Do you want to Edit or Delete the quiz?")

                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, CreateQuiz.class);
                                oldActivity.startActivity(intent);
                            }
                        })

                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                model.quizzList.remove(position);
                                oldActivity.finish();
                                oldActivity.startActivity(((Activity) oldActivity).getIntent());



                            }
                        });

                alertDialog.create().show();




                //Intent intent = new Intent(context, CreateQuiz.class);
                //oldActivity.startActivity(intent);

                return true;
            }
        });



    }




}
