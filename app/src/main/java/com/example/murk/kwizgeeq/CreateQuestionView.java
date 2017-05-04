package com.example.murk.kwizgeeq;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.*;

import com.example.murk.kwizgeeq.model.*;

public class CreateQuestionView extends AppCompatActivity {

    private CreateQuestionPresenter presenter;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    static final int REQUEST_TAKE_PHOTO = 1;

    public CreateQuestionView(){
        presenter = new CreateQuestionPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_question);

        presenter.onCreate();
    }


    public void setQuestionString(String s){
        EditText questionText = (EditText) findViewById(R.id.questionText);
        questionText.setText(s, TextView.BufferType.EDITABLE);
    }

    public void setCorrectStringAnswer(String correctString){
        EditText correct = (EditText) findViewById(R.id.correctText);
        correct.setText(correctString, TextView.BufferType.EDITABLE);
    }

    public void setWrongStringAnswer(String wrongString){
        EditText wrong1 = (EditText) findViewById(R.id.wrongText1);
        EditText wrong2 = (EditText) findViewById(R.id.wrongText2);
        EditText wrong3 = (EditText) findViewById(R.id.wrongText2);

        if(wrong1.getText().toString().equals("")){
            wrong1.setText(wrongString, TextView.BufferType.EDITABLE);
        } else if(wrong2.getText().toString().equals("")){
            wrong2.setText(wrongString, TextView.BufferType.EDITABLE);
        } else if(wrong3.getText().toString().equals("")){
            wrong3.setText(wrongString, TextView.BufferType.EDITABLE);
        }
    }


    public void mediaButtonAction(View view){
        dispatchTakePictureIntent();
    }

    public void nextButtonAction(View view){
        presenter.nextButtonAction();
    }

    public void doneButtonAction(View view){
        presenter.doneButtonAction();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = presenter.createImageFile(storageDir);
        } catch (IOException ex) {

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }

    /**
     * Så här tänkte jag först att det skulle se ut om vi använder Serializable
     * @return
     */
    public UserQuiz getQuiz(){
        return (UserQuiz)getIntent().getSerializableExtra("selectedQuiz");
    }

    /**
     * Så här skulle man göra om vi skickar runt index
     * @return
     */
    public int getQuizIndex(){
        return getIntent().getIntExtra("quizIndex",0);
    }

    public int getQuestionIndex(){
        return getIntent().getIntExtra("questionIndex",0);
    }


    public String getImageFilePath(){

    }

    public String getQuestionString(){
        EditText questionText = (EditText) findViewById(R.id.questionText);
        return questionText.getText().toString();
    }

    public String getCorrectString(){
        EditText correctText = (EditText) findViewById(R.id.correctText);
        return correctText.getText().toString();
    }

    public String getWrong1String(){
        EditText wrongText1 = (EditText) findViewById(R.id.wrongText1);
        return wrongText1.getText().toString();
    }

    public String getWrong2String(){
        EditText wrongText2 = (EditText) findViewById(R.id.wrongText2);
        return wrongText2.getText().toString();
    }

    public String getWrong3String(){
        EditText wrongText3 = (EditText) findViewById(R.id.WrongText3);
        return wrongText3.getText().toString();
    }


    public void addMoreQuestions(int quizIndex, int questionIndex){
        Intent intent = new Intent(CreateQuestionView.this,CreateQuestionView.class);
        intent.putExtra("quizIndex",quizIndex);
        intent.putExtra("questionIndex",questionIndex);
        startActivity(intent);
    }

    public void endAddOfQuestions(){
        Intent intent = new Intent(CreateQuestionView.this,QuizList.class);
        startActivity(intent);
    }
}
