package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.Observable;


/**
 * Created by Murk on 2017-05-09.
 */

public class EditQuizView extends Observable {

    private final Class<? extends Activity> createQuestionActivity;
    private final Activity currentActivity;
    final private Button btnColorPicker;
    private int index;
    private Context context;
    private ListView listView;
    private EditText editText;
    private KwizGeeQ model;
    private EditQuizAdapter adapter;
    private FloatingActionButton fab;
    private int mSelectedColor;

    public EditQuizView(final Class<? extends Activity> createQuestionActivity, final int index,
                        final ListView listView, final Context context, final Activity currentActivity,
                        EditText editText, FloatingActionButton fab, final Button btnColorPicker) {
        this.createQuestionActivity = createQuestionActivity;
        this.currentActivity = currentActivity;
        this.context = context;
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();
        this.editText = editText;
        //this.fab = fab;  // should not be needed ?
        this.index = index;
        this.btnColorPicker = btnColorPicker;
        mSelectedColor = model.getQuiz(index).getListColor();

        this.btnColorPicker.setBackgroundColor(mSelectedColor);
        this.editText.setText(model.getQuizName(index));
        this.adapter = new EditQuizAdapter(context, model.getQuiz(index).getQuestions(), model.getQuiz(index));
        listView.setAdapter(adapter);


    }

    public String getQuizName() {
        return editText.getText().toString();
    }

    public void fabPressed() {
        Intent intent = new Intent(context, createQuestionActivity);
        intent.putExtra("quizIndex", model.getUserQuizList().size()-1);
        currentActivity.startActivity(intent);
    }

    public void setColorItemClickedListener(View.OnClickListener listener) {
        btnColorPicker.setOnClickListener(listener);
    }

    public void setOnListItemClickedListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    public void changeView(int questionIndex) {
        Intent intent = new Intent(context, createQuestionActivity);
        intent.putExtra("questionIndex", questionIndex);
        intent.putExtra("quizIndex", index);

        currentActivity.startActivity(intent);
    }

    public void saveChanges() {
        //TODO make a listener to handle this instead - but it Tworks!

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
                model.getQuiz(index).setListColor(mSelectedColor);
                currentActivity.finish();
                currentActivity.startActivity((currentActivity).getIntent());

            }

        });

        dialog.show(currentActivity.getFragmentManager(), "color_dialog_test");
    }
}


