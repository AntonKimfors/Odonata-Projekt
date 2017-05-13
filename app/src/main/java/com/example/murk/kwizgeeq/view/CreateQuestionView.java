package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.util.*;

import com.example.murk.kwizgeeq.activity.*;
import com.example.murk.kwizgeeq.model.*;
import com.example.murk.kwizgeeq.utils.ImageFileHandler;

public class CreateQuestionView extends Observable {

    private final UserQuestion userQuestion;
    private final Activity currentActivity;
    private final Class<? extends Activity> createQuestionActivityClass;
    private final Class<? extends Activity> quizListActivityClass;

    /*
    private File imageStorageDir;*/
    private final PackageManager packageManager;
    private final int captureImageRequestCode;

    private final EditText questionText;
    private final EditText correctText;
    private final EditText wrongText1;
    private final EditText wrongText2;
    private final EditText wrongText3;

    private ImageView thumbnail;

    private Drawable originalEditText;

    public CreateQuestionView(Activity currentActivity,
                              Class<? extends Activity> createQuestionActivityClass,
                              Class<? extends Activity> quizListActivityClass,
                              PackageManager packageManager, int captureImageRequestCode,
                              EditText questionText, EditText correctText, EditText wrongText1,
                              EditText wrongText2, EditText wrongText3, ImageView thumbnail,
                              int quizIndex, int questionIndex) {

        this.currentActivity = currentActivity;
        this.createQuestionActivityClass = createQuestionActivityClass;
        this.quizListActivityClass = quizListActivityClass;

        //this.imageStorageDir = imageStorageDir;
        this.packageManager = packageManager;
        this.captureImageRequestCode = captureImageRequestCode;

        this.questionText = questionText;
        this.correctText = correctText;
        this.wrongText1 = wrongText1;
        this.wrongText2 = wrongText2;
        this.wrongText3 = wrongText3;

        this.thumbnail = thumbnail;

        userQuestion = (UserQuestion)KwizGeeQ.getInstance().getQuestion(quizIndex,questionIndex);

        originalEditText = correctText.getBackground();

        setTextFields();
    }

    public void addOnFocusChangeListener(View.OnFocusChangeListener listener){
        correctText.setOnFocusChangeListener(listener);
        wrongText1.setOnFocusChangeListener(listener);
        wrongText2.setOnFocusChangeListener(listener);
        wrongText3.setOnFocusChangeListener(listener);
    }

    public void flashEmpty(){
        flash(questionText);
        flash(correctText);
        flash(wrongText1);
        flash(wrongText2);
        flash(wrongText3);
    }

    private void flash(final EditText editText){
        if(editText.getText().toString().isEmpty()){
            final Drawable f2 = editText.getBackground();
            ColorDrawable f1 = new ColorDrawable(Color.argb(255,50,50,50));
            AnimationDrawable a = new AnimationDrawable();
            a.addFrame(f1,100);
            a.addFrame(f2,0);
            a.stop();
            a.setOneShot(true);
            editText.setBackground(a);
            a.start();

            new CountDownTimer(100, 100){
                public void onTick(long l){

                }
                public void onFinish(){
                    editText.setBackground(f2);
                }
            }.start();
        }
    }

    public void highlightField(View view){
        EditText textField = (EditText) view;
        originalEditText = textField.getBackground();

        if(textField.equals(correctText)){
            int greenColor = Color.argb(255,150, 255, 150);
            textField.setBackgroundColor(greenColor);
        } else{
            int redColor = Color.argb(255, 255, 129, 109);
            textField.setBackgroundColor(redColor);
        }

    }

    public void normalizeField(View view){
        EditText textField = (EditText) view;
        textField.setBackground(originalEditText);
    }

    public void setThumbnail(){
        String imagePath = userQuestion.getImagePath();

        if(imagePath!=null){
            Uri imageUri = Uri.parse(imagePath);
            thumbnail.setImageURI(imageUri);
        }
    }

    public void setTextFields(){
        setQuestionString(userQuestion.getQuestionText());

        Iterator<Answer> answerIterator = userQuestion.answerIterator(false);

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

    public void takePhoto(Uri imageUri){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            currentActivity.startActivityForResult(takePictureIntent, captureImageRequestCode);
        }
    }

    public void addMoreQuestions(int nextQuizIndex, int nextQuestionIndex){
        Intent intent = new Intent(currentActivity,createQuestionActivityClass);
        intent.putExtra("quizIndex",nextQuizIndex);
        intent.putExtra("questionIndex",nextQuestionIndex);
        currentActivity.startActivity(intent);
    }

    public void endAddOfQuestions(){
        Intent intent = new Intent(currentActivity,quizListActivityClass);
        currentActivity.startActivity(intent);
    }

    public void update(Observable o, Object arg) {
        setThumbnail();
        setTextFields();
    }
}
