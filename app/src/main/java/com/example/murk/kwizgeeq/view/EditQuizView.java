package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ListView;


import com.example.murk.kwizgeeq.model.EditQuizAdapter;
import com.example.murk.kwizgeeq.model.KwizGeeQ;


import java.util.Observable;



/**
 * Created by Murk on 2017-05-09.
 */

public class EditQuizView extends Observable {


    private final Class<? extends Activity> createQuestionActivity;
    public ListView listView;
    private EditText editText;
    private KwizGeeQ model;
    private EditQuizAdapter adapter;
    private FloatingActionButton fab;

    public EditQuizView(final Class<? extends Activity> createQuestionActivity, final int index,
                        final ListView listView, final Context context, final Activity oldActivity,
                        EditText editText, FloatingActionButton fab) {
        this.createQuestionActivity = createQuestionActivity;
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();
        this.editText = editText;
        this.fab = fab;
        //this.editText.setText(model.getQuiz(index).getName());


        //System.out.println(index);
        //System.out.println(model.getQuizName(index));
        this.editText.setText(model.getQuizName(index));  //Checkwhayy
        this.adapter = new EditQuizAdapter(context, model.getQuiz(index).getQuestions());
        listView.setAdapter(adapter);

        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(context, createQuestionActivity);
                intent.putExtra("questionIndex",position);
                intent.putExtra("quizIndex",index);

                oldActivity.startActivity(intent);
            }
        });

        //Added clickListenet to FloatingActionButton
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, createQuestionActivity);
                //model.getQuiz(index).
                intent.putExtra("quizIndex",index);
                oldActivity.startActivity(intent);

            }

        });

    }


}
