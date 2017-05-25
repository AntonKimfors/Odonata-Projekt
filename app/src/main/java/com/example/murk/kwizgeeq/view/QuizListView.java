package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.murk.kwizgeeq.events.EventBusWrapper;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.utils.KwizGeeQDataSource;
import com.google.common.eventbus.Subscribe;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListView extends Observable {
    private final Activity currentActivity;
    private final Class<? extends Activity> editQuizActivityClass;
    private final Class<? extends Activity> questioneerActivityClass;
    private final EditText quizName;
    private final Button mCancel;
    private final Button mCreateQuiz;
    private final Button mPickColor;
    private final AlertDialog.Builder mBuilder;
    private final View mView;
    private final ColorPickerDialog dialog;
    private final AlertDialog.Builder alertDialog;
    private final AlertDialog ad;
    private int mSelectedColor;
    private ListView listView;
    private QuizListAdapter adapter;
    private FloatingActionButton fab;
    private Context context;
    private int editQuizRequestCode;
    private int createQuizRequestCode;
    private int questioneerRequestCode;
    private AlertDialog creationDialog;


    public QuizListView(final ListView listView, final Context context,
                        final Activity currentActivity,
                        final Class<? extends Activity> editQuizActivityClass,
                        final Class<? extends Activity> questioneerActivityClass,
                        FloatingActionButton fab, final KwizGeeQDataSource mKwizGeeQDataSource, int editQuizRequestCode, int createQuizRequestCode, int questioneerRequestCode) {

        this.listView = listView;
        this.context = context;
        this.editQuizActivityClass = editQuizActivityClass;
        this.questioneerActivityClass = questioneerActivityClass;
        this.editQuizRequestCode = editQuizRequestCode;
        this.createQuizRequestCode = createQuizRequestCode;
        this.questioneerRequestCode = questioneerRequestCode;
        this.currentActivity = currentActivity;
        this.fab = fab;

        mView = LayoutInflater.from(context).inflate(R.layout.dialog_create_quiz, null);
        quizName = (EditText) mView.findViewById(R.id.etQuizName);
        mCancel = (Button) mView.findViewById(R.id.btnCancel);
        mCreateQuiz = (Button) mView.findViewById(R.id.btnCreateQuiz);
        mPickColor = (Button) mView.findViewById(R.id.btnPickColor);
        mPickColor.setBackgroundColor(context.getResources().getColor(R.color.flamingo));
        mSelectedColor = ContextCompat.getColor(context, R.color.flamingo);

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

        alertDialog = new AlertDialog.Builder(currentActivity)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Edit UserQuiz?")
                .setMessage("Do you want to Edit or Delete the quiz?");

        mBuilder = new AlertDialog.Builder(context);
        mBuilder.setView(mView);
        creationDialog = mBuilder.create();

        ad = alertDialog.create();

        EventBusWrapper.BUS.register(this);

    }

    public void setQuestions(ArrayList<UserQuiz> quizList){
        this.adapter = new QuizListAdapter(context, quizList);
        listView.setAdapter(adapter);
    }

    public void fabPressed() {
        quizName.setText("");
        creationDialog.show();
    }

    public void setmSelectedColor(int color){
        mSelectedColor = color;
    }

    public void showColorDialog(){
        dialog.show(currentActivity.getFragmentManager(), "color_dialog_test");
    }

    public void dismissCreationDialog(){
        creationDialog.dismiss();
    }

    public void setmCancelOnClickListener(View.OnClickListener listener){
        mCancel.setOnClickListener(listener);
    }

    public void setmPickColorListener(View.OnClickListener listener){
        mPickColor.setOnClickListener(listener);
    }

    public void setColorPickerListener(ColorPickerSwatch.OnColorSelectedListener listener){
        dialog.setOnColorSelectedListener(listener);
    }

    public void setmCreateQuizOnClickListener(View.OnClickListener listener){
        mCreateQuiz.setOnClickListener(listener);
    }

    public void setOnListItemClickedListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
        listView.setOnItemLongClickListener(listener);
    }

    public String getQuizName(){
        return quizName.getText().toString();
    }

    public int getmSelectedColor(){
        return mSelectedColor;
    }

    public void editQuiz(UserQuiz quiz,int quizIndex){
        Intent intent = new Intent(context, editQuizActivityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable("quiz",quiz);
        intent.putExtras(bundle);
        currentActivity.startActivityForResult(intent, editQuizRequestCode);
    }

    public void createNewQuiz(UserQuiz quiz){
        Intent intent = new Intent(context, editQuizActivityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable("quiz",quiz);
        intent.putExtras(bundle);
        currentActivity.startActivityForResult(intent,createQuizRequestCode);
    }

    public void startQuestioneer(UserQuiz quiz) {
        Intent intent = new Intent(context, questioneerActivityClass);
        intent.putExtra("quiz", quiz);
        currentActivity.startActivityForResult(intent, questioneerRequestCode);
    }

    public void showAlertDialog(){
        alertDialog.show();
    }

    public void dismissAlertDialog(){
        ad.dismiss();
    }

    public void setAlertDialogPositiveListener(DialogInterface.OnClickListener listener){
        alertDialog.setPositiveButton("Edit",listener);
    }

    public void setAlertDialogNegativeListener(DialogInterface.OnClickListener listener){
        alertDialog.setNegativeButton("DELETE",listener);
    }

    public void setAlertDialogNeutralListener(DialogInterface.OnClickListener listener){
        alertDialog.setNeutralButton("Cancel",listener);
    }

    public void updatePickColorBackground() {
        mPickColor.setBackgroundColor(mSelectedColor);
    }

    @Subscribe
    public void update(KwizGeeQ kwizGeeQ){
        System.out.println("Update!");
        adapter.notifyDataSetChanged();
    }
}



