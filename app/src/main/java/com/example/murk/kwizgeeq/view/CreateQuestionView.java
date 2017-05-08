package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.murk.kwizgeeq.*;
import com.example.murk.kwizgeeq.activity.*;
import com.example.murk.kwizgeeq.model.*;
import com.example.murk.kwizgeeq.utils.ImageFileHandler;

public class CreateQuestionView extends Observable{

    private KwizGeeQ model;

    private EditText questionText;
    private EditText correctText;
    private EditText wrongText1;
    private EditText wrongText2;
    private EditText wrongText3;

    private String photoPath;

    public CreateQuestionView(EditText questionText, EditText correctText, EditText wrongText1,
                              EditText wrongText2, EditText wrongText3) {

        this.questionText = questionText;
        this.correctText = correctText;
        this.wrongText1 = wrongText1;
        this.wrongText2 = wrongText2;
        this.wrongText3 = wrongText3;

        model = KwizGeeQ.getInstance();



    }

    public void setTextFields(int quizIndex, int questionIndex){
        UserQuestion question = (UserQuestion)model.getQuestion(quizIndex,questionIndex);

        setQuestionString(question.getQuestionStr());

        Iterator<Answer> answerIterator = question.answerIterator(false);

        while(answerIterator.hasNext()){
            Answer<String> answer = answerIterator.next();

            if(answer.isCorrect()){
                setCorrectStringAnswer(answer.getData());
            } else{
                setWrongStringAnswer(answer.getData());
            }
        }
    }

    private void setQuestionString(String s){
        questionText.setText(s, TextView.BufferType.EDITABLE);
    }

    private void setCorrectStringAnswer(String correctString){
        correctText.setText(correctString, TextView.BufferType.EDITABLE);
    }

    private void setWrongStringAnswer(String wrongString){
        if(wrongText1.getText().toString().equals("")){
            wrongText1.setText(wrongString, TextView.BufferType.EDITABLE);
        } else if(wrongText2.getText().toString().equals("")){
            wrongText2.setText(wrongString, TextView.BufferType.EDITABLE);
        } else if(wrongText3.getText().toString().equals("")){
            wrongText3.setText(wrongString, TextView.BufferType.EDITABLE);
        }
    }

    public String getQuestionString(){
        return questionText.getText().toString();
    }

    public String getCorrectString(){
        return correctText.getText().toString();
    }

    public String getWrong1String(){
        return wrongText1.getText().toString();
    }

    public String getWrong2String(){
        return wrongText2.getText().toString();
    }

    public String getWrong3String(){
        return wrongText3.getText().toString();
    }

    public void mediaButtonAction(NavigatableActivity activity, File storageDir,
                                  PackageManager packageManager, Context context, int requestCode){

        ImageFileHandler handler = new ImageFileHandler();
        Uri photoURI = handler.getImageURI(storageDir,context);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            activity.startActivityForResult(takePictureIntent, requestCode);
        }

    }

    public void addMoreQuestions(int quizIndex, int questionIndex, NavigatableActivity oldActivity,
                                 Context context){
        Intent intent = new Intent(context,CreateQuestionActivity.class);
        intent.putExtra("quizIndex",quizIndex);
        intent.putExtra("questionIndex",questionIndex);
        oldActivity.startActivity(intent);
    }

    public void endAddOfQuestions(NavigatableActivity oldActivity, Context context){
        Intent intent = new Intent(context,QuizList.class);
        oldActivity.startActivity(intent);
    }
}
