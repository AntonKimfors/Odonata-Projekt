package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.murk.kwizgeeq.activity.EditQuizActivity;
import com.example.murk.kwizgeeq.activity.QuestioneerActivity;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.QuizListAdapter;
import com.example.murk.kwizgeeq.model.UserQuiz;

import java.util.Observable;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListView extends Observable {

    private ListView listView;
    private final Activity currentActivity;
    private final Class<? extends Activity> editQuizActivityClass;
    private final Class<? extends Activity> questioneerActivityClass;
    private KwizGeeQ model;
    private QuizListAdapter adapter;
    private FloatingActionButton fab;


    public QuizListView(final ListView listView, final Context context, final Activity currentActivity, final Class<? extends Activity> editQuizActivityClass, final Class<? extends Activity> questioneerActivityClass, FloatingActionButton fab){
        this.listView = listView;
        this.editQuizActivityClass = editQuizActivityClass;
        this.questioneerActivityClass = questioneerActivityClass;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = currentActivity;
        this.fab = fab;


        this.adapter = new QuizListAdapter(context, model.getQuizList());
        listView.setAdapter(adapter);

        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, questioneerActivityClass);

                intent.putExtra("quizIndex", position);
                currentActivity.startActivity(intent);

            }
        });


        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().getQuizList().get(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(currentActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Edit Quiz?")
                        .setMessage("Do you want to Edit or Delete the quiz?");

                final AlertDialog ad = alertDialog.create();

                    alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, editQuizActivityClass);
                         intent.putExtra("quizIndex", position);
                        currentActivity.startActivity(intent);
                        }
                     })
                            .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                model.getQuizList().remove(position);

                                currentActivity.finish();
                                currentActivity.startActivity((currentActivity).getIntent());

                            }
                        })

                           .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   ad.dismiss();
                               }
                           })

                        .show();

                return true;
            }
        });


        //Added clickListenet to FloatingActionButton
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                final EditText input = new EditText(currentActivity);


                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(currentActivity)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Create Quiz?")
                        .setView(input);

                final AlertDialog ad = alertDialog.create();


                alertDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String quizTitle = input.getText().toString();
                        model.getQuizList().add(new UserQuiz(quizTitle, 0));
                    }
                })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ad.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
}


