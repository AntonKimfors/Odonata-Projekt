package com.example.murk.kwizgeeq.controller;

import android.view.View;
import android.widget.AdapterView;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.view.EditQuizView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Murk on 2017-05-06.
 */

public class EditQuizController implements Controller, Observer {

    private EditQuizView editQuizview;
    private KwizGeeQ model;
    private int index;
    private List<Question> questions;


    public EditQuizController(final EditQuizView editQuizview, int index) {
        this.editQuizview = editQuizview;
        this.model = KwizGeeQ.getInstance();
        this.questions = model.getQuiz(index).getQuestions(); //TODO: this variable should be passed in by constructor
        if(questions==null){
            System.out.println("questions is null");
        }
        this.index = index;

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int questionIndex, long id) {
                editQuizview.changeView(questions,questionIndex);
            }
        };


        View.OnClickListener colorOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editQuizview.openColorDialog();
            }

        };
        editQuizview.setColorItemClickedListener(colorOnClickListener);
        editQuizview.setOnListItemClickedListener(onItemClickListener);
    }


    @Override
    public void onCreate() {

    }

    public void onClickAction(View view) {
        this.editQuizview.fabPressed(questions);
    }


    public void saveQuizName() {
        model.getQuiz(index).setName(editQuizview.getQuizName().toString());
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}


