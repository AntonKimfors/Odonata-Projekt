package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Statistics;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.Observable;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListView extends Observable {
    private final Activity currentActivity;
    private final Class<? extends Activity> editQuizActivityClass;
    private final Class<? extends Activity> questioneerActivityClass;
    private final KwizGeeQDataSource mKwizGeeQDataSource;
    private int mSelectedColor;
    private ListView listView;
    private KwizGeeQ model;
    private QuizListAdapter adapter;
    private FloatingActionButton fab;
    private Context context;


    public QuizListView(final ListView listView, final Context context, final Activity currentActivity, final Class<? extends Activity> editQuizActivityClass, final Class<? extends Activity> questioneerActivityClass, FloatingActionButton fab, final KwizGeeQDataSource mKwizGeeQDataSource) {
        this.listView = listView;
        this.context = context;
        this.editQuizActivityClass = editQuizActivityClass;
        this.questioneerActivityClass = questioneerActivityClass;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = currentActivity;
        this.fab = fab;
        this.mKwizGeeQDataSource = mKwizGeeQDataSource;


        this.adapter = new QuizListAdapter(context, model.getUserQuizList());
        listView.setAdapter(adapter);

    }

    public void fabPressed() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_create_quiz, null);
        final EditText quizName = (EditText) mView.findViewById(R.id.etQuizName);
        Button mCancel = (Button) mView.findViewById(R.id.btnCancel);
        Button mCreateQuiz = (Button) mView.findViewById(R.id.btnCreateQuiz);
        final Button mPickColor = (Button) mView.findViewById(R.id.btnPickColor);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mSelectedColor = ContextCompat.getColor(context, R.color.flamingo);
        mPickColor.setBackgroundColor(context.getResources().getColor(R.color.flamingo));
        mPickColor.setOnClickListener(new View.OnClickListener() {
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
                        mPickColor.setBackgroundColor(mSelectedColor);

                    }

                });

                dialog.show(currentActivity.getFragmentManager(), "color_dialog_test");
            }
        });

        mCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, editQuizActivityClass);
                String quizTitle = quizName.getText().toString();
                int quizindex = model.getUserQuizList().size();
                model.getUserQuizList().add(new UserQuiz(quizTitle, mSelectedColor));
                model.getQuizStatisticsList().add(new Statistics());

                //TODO: Är detta mvc? FUnkar det??
                //mKwizGeeQDataSource.insertQuizes(model.getUserQuizList());

                intent.putExtra("quizIndex", quizindex);
                currentActivity.startActivity(intent);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void setOnListItemClickedListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        listView.setOnItemLongClickListener(listener);
    }


    public void changeView(int questionIndex) {

        Intent intent = new Intent(context, questioneerActivityClass);
        intent.putExtra("quizIndex", questionIndex);
        model.startQuiz();
        currentActivity.startActivity(intent);


    }

    public void openLongPressDialog(final int questionIndex) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(currentActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Edit UserQuiz?")
                .setMessage("Do you want to Edit or Delete the quiz?");

        final AlertDialog ad = alertDialog.create();

        alertDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context, editQuizActivityClass);
                intent.putExtra("quizIndex", questionIndex);
                currentActivity.startActivity(intent);
            }
        })
                .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        model.getUserQuizList().remove(questionIndex);

                        currentActivity.finish();
                        currentActivity.startActivity((currentActivity).getIntent());

                    }
                })

                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ad.dismiss();
                    }
                })

                .show();
    }
}



