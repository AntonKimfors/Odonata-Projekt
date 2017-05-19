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
import com.example.murk.kwizgeeq.databinding.ActivityEditQuestionBinding;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Henrik on 05/05/2017.
 */

public class EditQuestionActivity extends AppCompatActivity{

    private EditQuestionView editQuestionView;
    private EditQuestionController editQuestionController;

    final int captureImageRequestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditQuestionBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_question);

        File imageStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<Question> questions = (List)bundle.getSerializable("questions");
        int questionIndex = intent.getIntExtra("questionIndex",0);
        System.out.println(questions.toString());
        System.out.println("Question index: " + questionIndex);

        editQuestionView = new EditQuestionView(this, EditQuestionActivity.class,
                QuizListActivity.class, captureImageRequestCode);

        editQuestionController = new EditQuestionController(editQuestionView,
                this, imageStorageDir, questions, questionIndex);

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
