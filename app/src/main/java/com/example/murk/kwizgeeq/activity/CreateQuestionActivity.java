package com.example.murk.kwizgeeq.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.*;
import com.example.murk.kwizgeeq.databinding.ActivityCreateQuestionBinding;
import com.example.murk.kwizgeeq.view.*;

import java.io.*;

/**
 * Created by Henrik on 05/05/2017.
 */

public class CreateQuestionActivity extends AppCompatActivity{

    private CreateQuestionView createQuestionView;
    private CreateQuestionController createQuestionController;

    final int captureImageRequestCode = 1;

    private ActivityCreateQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_question);

        Context applicationContext = getApplicationContext();
        File imageStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        PackageManager packageManager = getPackageManager();

        EditText questionText = (EditText)findViewById(R.id.questionText);
        EditText correctText = (EditText)findViewById(R.id.correctText);
        EditText wrongtext1 = (EditText)findViewById(R.id.wrongText1);
        EditText wrongtext2 = (EditText)findViewById(R.id.wrongText2);
        EditText wrongtext3 = (EditText)findViewById(R.id.wrongText3);

        ImageView thumbnail = (ImageView)findViewById(R.id.thumbnail);

        int quizIndex = getIntent().getIntExtra("quizIndex",0);
        int questionIndex = getIntent().getIntExtra("questionIndex",0);

        createQuestionView = new CreateQuestionView(this, applicationContext, imageStorageDir,
                packageManager,captureImageRequestCode, questionText,correctText,
                wrongtext1,wrongtext2,wrongtext3,thumbnail,quizIndex,questionIndex);

        createQuestionController = new CreateQuestionController(createQuestionView,
                applicationContext, imageStorageDir, quizIndex, questionIndex);
        createQuestionController.onCreate();

        binding.setController(createQuestionController);

        createQuestionView.addObserver(createQuestionController);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == captureImageRequestCode && resultCode == RESULT_OK) {
            createQuestionController.imageCreated();
        }
    }
}
