package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.activity.QuestioneerActivity;
import com.example.murk.kwizgeeq.model.CreateQuizAdapter;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.QuizListAdapter;

import java.util.Observable;

/**
 * Created by Murk on 2017-05-09.
 */

public class EditQuizView extends Observable {


    public ListView listView;

    private KwizGeeQ model;
    private CreateQuizAdapter adapter;

    public EditQuizView(int index,final ListView listView, final Context context,final NavigatableActivity oldActivity) {
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();



        this.adapter = new CreateQuizAdapter(context, model.getQuiz(index).getQuestions());
        listView.setAdapter(adapter);

        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position); //Make the clicked quiz active quiz.
                //TODO make the above possible with sent intents.


                Intent intent = new Intent(context, QuestioneerActivity.class);
                oldActivity.startActivity(intent);
            }
        });

    }


}
