package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


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
    private Context context;
    private ListView listView;
    private EditText editText;
    final private Button btnColorPicker;
    private KwizGeeQ model;
    private EditQuizAdapter adapter;
    private FloatingActionButton fab;
    private int mSelectedColor;

    public EditQuizView(final Class<? extends Activity> createQuestionActivity, final int index,
                        final ListView listView, final Context context, final Activity currentActivity,
                        EditText editText, FloatingActionButton fab,final Button btnColorPicker) {
        this.createQuestionActivity = createQuestionActivity;
        this.currentActivity = currentActivity;
        this.context = context;
        this.listView = listView;
        this.model = KwizGeeQ.getInstance();
        this.editText = editText;
        this.fab = fab;
        this.btnColorPicker = btnColorPicker;
        mSelectedColor = model.getQuiz(index).getListColor();

        this.btnColorPicker.setBackgroundColor(mSelectedColor);
        this.btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });


        this.editText.setText(model.getQuizName(index));
        this.adapter = new EditQuizAdapter(context, model.getQuiz(index).getQuestions(),model.getQuiz(index));
        listView.setAdapter(adapter);



        //Play quiz
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(context, createQuestionActivity);
                intent.putExtra("questionIndex",position);
                intent.putExtra("quizIndex",index);

                currentActivity.startActivity(intent);
            }
        });

        //Added clickListenet to FloatingActionButton
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, createQuestionActivity);
                //model.getQuiz(index).
                intent.putExtra("quizIndex",model.getUserQuizList().size()-1);
                currentActivity.startActivity(intent);

            }

        });



    }
    public void fabPressed(){
        Intent intent = new Intent(context, createQuestionActivity);
        //model.getQuiz(index).
        intent.putExtra("quizIndex",model.getUserQuizList().size()-1);
        currentActivity.startActivity(intent);
    }

}
