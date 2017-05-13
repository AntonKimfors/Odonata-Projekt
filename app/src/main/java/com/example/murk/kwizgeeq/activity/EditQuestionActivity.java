package com.example.murk.kwizgeeq.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.controller.*;
import com.example.murk.kwizgeeq.databinding.ActivityCreateQuestionBinding;
import com.example.murk.kwizgeeq.view.*;

import java.io.*;

/**
 * Created by Henrik on 05/05/2017.
 */

public class EditQuestionActivity extends AppCompatActivity{

    private EditQuestionView editQuestionView;
    private EditQuestionController editQuestionController;

    final int captureImageRequestCode = 1;

    private ActivityCreateQuestionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create_question);

        File imageStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        PackageManager packageManager = getPackageManager();

        EditText questionText = (EditText)findViewById(R.id.questionText);
        EditText correctText = (EditText)findViewById(R.id.correctText);
        EditText wrongtext1 = (EditText)findViewById(R.id.wrongText1);
        EditText wrongtext2 = (EditText)findViewById(R.id.wrongText2);
        EditText wrongtext3 = (EditText)findViewById(R.id.wrongText3);

        Button doneButton = (Button)findViewById(R.id.doneButton);
        Button nextButton = (Button)findViewById(R.id.nextQuestionButton);

        ImageView thumbnail = (ImageView)findViewById(R.id.thumbnail);

        int quizIndex = getIntent().getIntExtra("quizIndex",0);
        int questionIndex = getIntent().getIntExtra("questionIndex",0);

        editQuestionView = new EditQuestionView(this, EditQuestionActivity.class,
                QuizListActivity.class, packageManager,captureImageRequestCode, questionText,
                correctText,wrongtext1,wrongtext2,wrongtext3,thumbnail,doneButton,nextButton,
                quizIndex, questionIndex);

        editQuestionController = new EditQuestionController(editQuestionView,
                this, imageStorageDir, quizIndex, questionIndex);
        editQuestionController.onCreate();

        binding.setController(editQuestionController);

        editQuestionView.addObserver(editQuestionController);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == captureImageRequestCode && resultCode == RESULT_OK) {
            editQuestionController.imageCreated();
        }
    }
}
