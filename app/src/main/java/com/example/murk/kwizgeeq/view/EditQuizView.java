package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.murk.kwizgeeq.activity.QuizListActivity;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuiz;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;


/**
 * Created by Murk on 2017-05-09.
 */

public class EditQuizView extends Observable {

    private final Class<? extends Activity> editQuestionActivity;
    private final Activity currentActivity;
    final private Button btnColorPicker;
    private UserQuiz quiz;
    private Context context;
    private ListView listView;
    private EditText editText;
    private EditQuizAdapter adapter;
    private int mSelectedColor;

    public EditQuizView(final Class<? extends Activity> editQuestionActivity, final UserQuiz quiz,
                        final ListView listView, final Context context, final Activity currentActivity) {
        this.editQuestionActivity = editQuestionActivity;
        this.currentActivity = currentActivity;
        this.context = context;
        this.quiz = quiz;
        this.listView = listView;
        this.editText = (EditText) currentActivity.findViewById(R.id.etQuizLabel);
        this.btnColorPicker = (Button) currentActivity.findViewById(R.id.btnColorPicker);
        mSelectedColor = quiz.getListColor();
        this.btnColorPicker.setBackgroundColor(mSelectedColor);
        this.editText.setText(quiz.getName());
        this.adapter = new EditQuizAdapter(context, quiz.getQuestions(), quiz);
        listView.setAdapter(adapter);


    }

    public String getQuizName() {
        return editText.getText().toString();
    }

    public void fabPressed(List<Question> questions, int index) {
        Intent intent = new Intent(context, editQuestionActivity);
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable) questions);
        intent.putExtras(bundle);
        intent.putExtra("questionIndex", index);
        //the quiz index should be decided by controller and sent in to this method when called
        currentActivity.startActivity(intent);
    }

    public void setColorItemClickedListener(View.OnClickListener listener) {
        btnColorPicker.setOnClickListener(listener);
    }

    public void setOnListItemClickedListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        listView.setOnItemLongClickListener(listener);
    }

    public void changeView(List<Question> questions, int questionIndex) {
        Intent intent = new Intent(context, editQuestionActivity);
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable) questions);
        intent.putExtras(bundle);
        intent.putExtra("questionIndex", questionIndex);// TODO: change this to work with serializable

        currentActivity.startActivity(intent);
    }

    public void reloadView() {
        Intent intent = new Intent(context, QuizListActivity.class);
        currentActivity.startActivity(intent);
    }

    public void openColorDialog() {
        int[] mColors = context.getResources().getIntArray(R.array.default_rainbow);

        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                5, // Number of columns
                ColorPickerDialog.SIZE_SMALL,
                true // True or False to enable or disable the serpentine effect
                //0, // stroke width
                //Color.BLACK // stroke color
        );

        dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                mSelectedColor = color;
                btnColorPicker.setBackgroundColor(mSelectedColor);
                quiz.setListColor(mSelectedColor);
                currentActivity.finish();
                currentActivity.startActivity((currentActivity).getIntent());

            }

        });

        dialog.show(currentActivity.getFragmentManager(), "color_dialog_test");
    }
    public void openLongPressDialog(final List<Question> questions, final int questionIndex) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(currentActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Edit UserQuiz?")
                .setMessage("Do you want to Edit or Delete the quiz?");

        final AlertDialog ad = alertDialog.create();

        alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(context, editQuestionActivity);
                Bundle bundle = new Bundle();
                bundle.putSerializable("questions",(Serializable) questions);
                intent.putExtra("questionIndex",questionIndex);
                intent.putExtras(bundle);

                currentActivity.startActivity(intent);
            }
        })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quiz.getQuestions().remove(questionIndex);

                        currentActivity.finish();
                        currentActivity.startActivity((currentActivity).getIntent());

                    }
                })



                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

                    //Går detta att flytta ut??

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ad.dismiss();
                    }
                })

                .show();
    }
}


