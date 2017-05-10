package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.murk.kwizgeeq.activity.CreateQuestionActivity;
import com.example.murk.kwizgeeq.activity.NavigatableActivity;
import com.example.murk.kwizgeeq.activity.QuestioneerActivity;
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

    public EditQuizView(final int index, final ListView listView, final Context context, final NavigatableActivity oldActivity, EditText editText) {
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();
        this.editText = editText;

        //this.editText.setText(model.getQuiz(index).getName().toString());  //Checkwhayy
        this.adapter = new EditQuizAdapter(context, model.getQuiz(index).getQuestions());
        listView.setAdapter(adapter);

        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //TODO make the above possible with sent intents.

                Intent intent = new Intent(context, CreateQuestionActivity.class);
                intent.putExtra("questionindex",position);
                intent.putExtra("quizindex",index);

                oldActivity.startActivity(intent);
            }
        });

    }


}
