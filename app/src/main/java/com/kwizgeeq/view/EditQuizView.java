package com.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.kwizgeeq.R;
import com.kwizgeeq.events.EventBusWrapper;
import com.kwizgeeq.model.Question;
import com.kwizgeeq.model.Quiz;
import com.google.common.eventbus.Subscribe;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


/**
 * Created by Murk on 2017-05-09.
 *
 *  * @author Marcus Olsson Lindvärn
 * revised by Anton Kimfors, Henrik Håkansson and Are Ehnberg
 */

public class EditQuizView extends Observable {

    private final Class<? extends Activity> editQuestionActivity;
    private final Activity currentActivity;
    final private Button btnColorPicker;
    private final int questionListRequestCode;
    private final AlertDialog.Builder alertDialog;
    private final ColorPickerDialog dialog;
    private final AlertDialog ad;
    private Quiz quiz;
    private Context context;
    private ListView listView;
    private EditText editText;
    private EditQuizAdapter adapter;
    private int mSelectedColor;


    public EditQuizView(final Class<? extends Activity> editQuestionActivity, final Serializable quiz,
                        final ListView listView, final Context context, final Activity currentActivity,
                        int questionListRequestCode) {

        this.editQuestionActivity = editQuestionActivity;
        this.currentActivity = currentActivity;
        this.context = context;
        if(quiz instanceof Quiz){
            this.quiz = (Quiz)quiz;
        }
        this.listView = listView;
        this.editText = (EditText) currentActivity.findViewById(R.id.etQuizLabel);
        this.btnColorPicker = (Button) currentActivity.findViewById(R.id.btnColorPicker);
        mSelectedColor = this.quiz.getListColor();
        this.questionListRequestCode = questionListRequestCode;
        this.btnColorPicker.setBackgroundColor(mSelectedColor);
        this.editText.setText(this.quiz.getName());
        this.adapter = new EditQuizAdapter(context, this.quiz.getQuestions());

        //Creation of ColorPicker Dialog
        int[] mColors = context.getResources().getIntArray(R.array.default_rainbow);
        dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                mColors,
                mSelectedColor,
                5, // Number of columns
                ColorPickerDialog.SIZE_SMALL,
                true // True or False to enable or disable the serpentine effect
                //0, // stroke width
                //Color.BLACK // stroke color
        );

        //Creaton of Alert Dialog
        alertDialog = new AlertDialog.Builder(currentActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Edit Quiz?")
                .setMessage("do you want to edit or delete the Quiz");


        ad = alertDialog.create();


        EventBusWrapper.BUS.register(this);
    }


    public void fabPressed(List<Question> questions) {
        Intent intent = new Intent(context, editQuestionActivity);
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable) questions);
        intent.putExtras(bundle);
        currentActivity.startActivityForResult(intent, questionListRequestCode);
    }

    //START---------------------------------------------------ListClick functionality
    public void setOnListItemClickedListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }
    //END-----------------------------------------------------ListClick functionality

    //START---------------------------------------------------ColorPicker functionality

    public void showColorDialog() {
        dialog.show(currentActivity.getFragmentManager(), "color_dialog_test");
    }

    public int getmSelectedColor() {
        return mSelectedColor;
    }

    public void setmSelectedColor(int color) {
        mSelectedColor = color;
    }

    public void updatePickColorBackground() {
        btnColorPicker.setBackgroundColor(mSelectedColor);
    }

    public void setmPickColorListener(View.OnClickListener listener) {
        btnColorPicker.setOnClickListener(listener);
    }

    public void setColorPickerListener(ColorPickerSwatch.OnColorSelectedListener listener) {
        dialog.setOnColorSelectedListener(listener);
    }
    //END-----------------------------------------------------ColorPicker functionality


    //START---------------------------------------------------Longpress functionality
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        listView.setOnItemLongClickListener(listener);
    }

    public void showAlertDialog() {
        alertDialog.show();
    }

    public void dismissAlertDialog() {
        ad.dismiss();
    }

    public void setAlertDialogPositiveListener(DialogInterface.OnClickListener listener) {
        alertDialog.setPositiveButton("Edit", listener);
    }

    public void setAlertDialogNegativeListener(DialogInterface.OnClickListener listener) {
        alertDialog.setNegativeButton("DELETE", listener);
    }

    public void setAlertDialogNeutralListener(DialogInterface.OnClickListener listener) {
        alertDialog.setNeutralButton("Cancel", listener);
    }
    //END-----------------------------------------------------Longpress functionality

    public String getQuizName() {
        return editText.getText().toString();
    }

    public void openEditQuestion(List<Question> questions, int questionIndex) {
        Intent intent = new Intent(context, editQuestionActivity);
        Bundle bundle = new Bundle();
        bundle.putSerializable("questions", (Serializable) questions);
        intent.putExtras(bundle);
        intent.putExtra("questionIndex", questionIndex);

        currentActivity.startActivityForResult(intent, questionListRequestCode);
    }

    public void reloadView() {
        this.adapter.notifyDataSetChanged();

    }

    public void setQuestions(ArrayList<Question> questions) {
        this.adapter = new EditQuizAdapter(context, questions);
        listView.setAdapter(adapter);
    }


    @Subscribe
    public void update(Quiz quiz) {
        System.out.println("notified");
        if (this.quiz == quiz) {
            adapter.notifyDataSetChanged();
        }
    }

    public void quitQuizEditing() {
        Intent intent = currentActivity.getIntent();
        Bundle bundle = intent.getExtras();
        bundle.putSerializable("quiz", quiz);
        intent.putExtras(bundle);
        currentActivity.setResult(Activity.RESULT_OK, intent);
        currentActivity.finish();
    }
}


