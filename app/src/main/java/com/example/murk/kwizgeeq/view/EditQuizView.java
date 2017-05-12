package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.murk.kwizgeeq.activity.CreateQuestionActivity;
import com.example.murk.kwizgeeq.model.EditQuizAdapter;
import com.example.murk.kwizgeeq.model.KwizGeeQ;

import java.util.Observable;

/**
 * Created by Murk on 2017-05-09.
 */

public class EditQuizView extends Observable {


    public ListView listView;
    private EditText editText;
    private KwizGeeQ model;
    private EditQuizAdapter adapter;

    public EditQuizView(final int index, final ListView listView, final Context context, final Activity oldActivity, EditText editText) {
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();
        this.editText = editText;



        //System.out.println(index);
        //System.out.println(model.getQuizName(index));
        //this.editText.setText(model.getQuizName(index));  //Checkwhayy
        this.adapter = new EditQuizAdapter(context, model.getQuiz(index).getQuestions());
        listView.setAdapter(adapter);

        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(context, CreateQuestionActivity.class);
                intent.putExtra("questionindex",position);
                intent.putExtra("quizindex",index);

                oldActivity.startActivity(intent);
            }
        });

    }


}
