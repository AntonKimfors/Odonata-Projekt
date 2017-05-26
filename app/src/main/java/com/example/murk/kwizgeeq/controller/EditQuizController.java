package com.example.murk.kwizgeeq.controller;


import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.EditQuizView;


import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Murk on 2017-05-06.
 */

public class EditQuizController implements Observer {

    private final ColorPickerSwatch.OnColorSelectedListener colorPickerListener;
    private final AdapterView.OnItemClickListener onItemClickListener;
    private final AdapterView.OnItemLongClickListener itemLongClickListener;
    private final View.OnClickListener mPickColorListener;

    private EditQuizView editQuizview;
    private UserQuiz quiz;
    private List<Question> questions;


    public EditQuizController(final EditQuizView editQuizview, final Serializable quiz) {
        this.editQuizview = editQuizview;
        if(quiz instanceof UserQuiz){
            this.quiz = (UserQuiz)quiz;
        }
        this.questions = this.quiz.getQuestions();
        editQuizview.setQuestions((ArrayList<Question>) questions);
        //Start onItemClickListerner for the List
        onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int questionIndex, long id) {
                editQuizview.openEditQuestion(questions, questionIndex);
            }
        };
        editQuizview.setOnListItemClickedListener(onItemClickListener);

        //Start onItemLongClickListerner for the List
        itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int questionIndex, long id) {
                editQuizview.setAlertDialogPositiveListener(getPositiveListener(questionIndex));
                editQuizview.setAlertDialogNegativeListener(getNegativeListener(questionIndex));
                editQuizview.setAlertDialogNeutralListener(getDismissListener());
                editQuizview.showAlertDialog();


                return true;
            }

        };
        editQuizview.setOnItemLongClickListener(itemLongClickListener);

        //Reacting on a picked color
        colorPickerListener = new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                editQuizview.setmSelectedColor(color);
                editQuizview.updatePickColorBackground();
                saveQuizColor();
                editQuizview.reloadView();

            }
        };
        editQuizview.setColorPickerListener(colorPickerListener);


        //Open ColorPicker
        mPickColorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editQuizview.showColorDialog();


            }
        };
        editQuizview.setmPickColorListener(mPickColorListener);


    }

    public void onClickAction(View view) {
        this.editQuizview.fabPressed(questions);
    }


    private DialogInterface.OnClickListener getPositiveListener(final int questionIndex) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editQuizview.openEditQuestion(questions, questionIndex);
                editQuizview.dismissAlertDialog();
            }
        };
    }

    private DialogInterface.OnClickListener getNegativeListener(final int questionIndex) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                quiz.getQuestions().remove(questionIndex);
                editQuizview.reloadView();
            }
        };
    }

    private DialogInterface.OnClickListener getDismissListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editQuizview.dismissAlertDialog();
            }
        };
    }


    public void saveQuizName() {
        quiz.setName(editQuizview.getQuizName().toString());
    }

    public void saveQuizColor() {
        quiz.setListColor(editQuizview.getmSelectedColor());

    }

    //TODO: Anpassa när det ska sparas. On button clicks istället?

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setQuestionList(Serializable newQuestions) {
        if(newQuestions instanceof  ArrayList){
            quiz.replaceQuestions((ArrayList<Question>)newQuestions);
        }
    }

    public void onBackPressed() {
        saveQuizName();
        editQuizview.quitQuizEditing();
    }
}


