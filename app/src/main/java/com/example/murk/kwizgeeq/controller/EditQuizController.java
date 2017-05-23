package com.example.murk.kwizgeeq.controller;

import android.view.View;
import android.widget.AdapterView;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.EditQuizView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Murk on 2017-05-06.
 */

public class EditQuizController implements Controller, Observer {

    private EditQuizView editQuizview;
    private UserQuiz quiz;
    private int index;
    private List<Question> questions;


    public EditQuizController(final EditQuizView editQuizview, final UserQuiz quiz) {
        this.editQuizview = editQuizview;
        this.quiz = quiz;
        this.questions = quiz.getQuestions();
        if(questions==null){
            System.out.println("questions is null");
        }


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
        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int questionIndex, long id) {
                editQuizview.openLongPressDialog(questions,questionIndex);
                return true;
            }

        };
        editQuizview.setOnItemLongClickListener(itemLongClickListener);
        editQuizview.setColorItemClickedListener(colorOnClickListener);
        editQuizview.setOnListItemClickedListener(onItemClickListener);
    }


    @Override
    public void onCreate() {

    }

    public void onClickAction(View view) {
        int index = quiz.getQuestions().size();
        this.editQuizview.fabPressed(questions,index);
    }


    public void saveQuizName() {

        quiz.setName(editQuizview.getQuizName().toString());
    }

    //TODO: Anpassa när det ska sparas. On button clicks istället?
    public void onPause() {
        QuizListController.saveCurrentData();
    }

    public void onResume() {
        QuizListController.readCurrentData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setQuestionList(ArrayList<Question> newQuestions) {
        quiz.replaceQuestions(newQuestions);
    }

    public void onBackPressed() {
        saveQuizName();
        editQuizview.quitQuizEditing();
    }
}


