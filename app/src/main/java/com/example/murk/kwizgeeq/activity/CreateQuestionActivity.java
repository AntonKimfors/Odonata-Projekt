package com.example.murk.kwizgeeq.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.health.PackageHealthStats;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.*;
import com.example.murk.kwizgeeq.databinding.ActivityCreateQuestionBinding;
import com.example.murk.kwizgeeq.view.*;

import java.io.*;

/**
 * Created by Henrik on 05/05/2017.
 */

public class CreateQuestionActivity extends AppCompatActivity implements NavigatableActivity{

    private CreateQuestionView createQuestionView;
    private CreateQuestionController createQuestionController;

    final int captureImageRequestCode = 1;

    private ActivityCreateQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_question);

        Context applicationContext = getApplicationContext();
        File imageStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        PackageManager packageManager = getPackageManager();

        EditText questionText = (EditText)findViewById(R.id.questionText);
        EditText correctText = (EditText)findViewById(R.id.correctText);
        EditText wrongtext1 = (EditText)findViewById(R.id.wrongText1);
        EditText wrongtext2 = (EditText)findViewById(R.id.wrongText2);
        EditText wrongtext3 = (EditText)findViewById(R.id.wrongText3);

        createQuestionView = new CreateQuestionView(this, applicationContext, imageStorageDir,
                packageManager,captureImageRequestCode, questionText,correctText,
                wrongtext1,wrongtext2,wrongtext3);

        int quizIndex = getIntent().getIntExtra("quizIndex",0);
        int questionIndex = getIntent().getIntExtra("questionIndex",0);

        createQuestionController = new CreateQuestionController(createQuestionView,quizIndex,
                questionIndex);
        createQuestionController.onCreate();

        binding.setController(createQuestionController);

        createQuestionView.addObserver(createQuestionController);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == captureImageRequestCode && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String path = uri.getPath();
            createQuestionController.imageCreated(path);
        }
    }
}
