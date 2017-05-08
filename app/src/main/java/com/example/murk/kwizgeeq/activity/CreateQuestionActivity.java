package com.example.murk.kwizgeeq.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.murk.kwizgeeq.*;
import com.example.murk.kwizgeeq.controller.*;
import com.example.murk.kwizgeeq.view.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Henrik on 05/05/2017.
 */

public class CreateQuestionActivity extends AppCompatActivity implements NavigatableActivity{

    private CreateQuestionView createQuestionView;
    private CreateQuestionController createQuestionController;

    String mCurrentPhotoPath;

    static final int REQUEST_TAKE_PHOTO = 1;

    private int quizIndex;
    private int questionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_create_question);

        quizIndex = getIntent().getIntExtra("quizIndex",0);
        questionIndex = getIntent().getIntExtra("questionIndex",0);

        createQuestionView = new CreateQuestionView((EditText)findViewById(R.id.questionText),
                (EditText)findViewById(R.id.correctText),(EditText)findViewById(R.id.wrongText1),
                (EditText)findViewById(R.id.wrongText2),(EditText)findViewById(R.id.wrongText3));

        createQuestionController = new CreateQuestionController(createQuestionView);
        createQuestionController.setTextFields(quizIndex,questionIndex);

        createQuestionView.addObserver(createQuestionController);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void mediaButtonAction(View view){
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        createQuestionView.mediaButtonAction(this,storageDir,getPackageManager(),
                view.getContext(),1);
    }

    public void nextButtonAction(View view){
        createQuestionController.nextButtonAction(quizIndex,questionIndex,this, view.getContext());
    }

    public void doneButtonAction(View view){
        createQuestionController.doneButtonAction(quizIndex,questionIndex,this, view.getContext());
    }
}
