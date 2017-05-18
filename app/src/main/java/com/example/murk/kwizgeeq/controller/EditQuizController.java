package com.example.murk.kwizgeeq.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.EditQuizView;
import com.example.murk.kwizgeeq.view.QuizListView;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Murk on 2017-05-06.
 */

public class EditQuizController implements Controller, Observer {

    private EditQuizView editQuizview;
    private KwizGeeQ model;
    private int index;


    public EditQuizController(final EditQuizView editQuizview, int index) {
        this.editQuizview = editQuizview;
        this.model = KwizGeeQ.getInstance();
        this.index = index;

        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int questionIndex, long id) {
                editQuizview.changeView(questionIndex);
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
        this.editQuizview.fabPressed();
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


