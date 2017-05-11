package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.util.*;

import com.example.murk.kwizgeeq.activity.*;
import com.example.murk.kwizgeeq.model.*;
import com.example.murk.kwizgeeq.utils.ImageFileHandler;

public class CreateQuestionView extends Observable implements Observer{

    private KwizGeeQ model;
    private NavigatableActivity currentActivity;
    private Context currentContext;

    private File imageStorageDir;
    private PackageManager packageManager;
    private int captureImageRequestCode;

    private EditText questionText;
    private EditText correctText;
    private EditText wrongText1;
    private EditText wrongText2;
    private EditText wrongText3;

    private ImageView thumbnail;

    private int quizIndex;
    private int questionIndex;

    private Uri imageUri;

    public Uri getImageUri(){
        return imageUri;
    }

    public CreateQuestionView(NavigatableActivity currentActivity, Context currentContext,
                              File imageStorageDir, PackageManager packageManager,
                              int captureImageRequestCode, EditText questionText,
                              EditText correctText, EditText wrongText1, EditText wrongText2,
                              EditText wrongText3, ImageView thumbnail, int quizIndex,
                              int questionIndex) {

        this.currentActivity = currentActivity;
        this.currentContext = currentContext;

        this.imageStorageDir = imageStorageDir;
        this.packageManager = packageManager;
        this.captureImageRequestCode = captureImageRequestCode;

        this.questionText = questionText;
        this.correctText = correctText;
        this.wrongText1 = wrongText1;
        this.wrongText2 = wrongText2;
        this.wrongText3 = wrongText3;

        this.thumbnail = thumbnail;

        this.quizIndex = quizIndex;
        this.questionIndex = questionIndex;

        model = KwizGeeQ.getInstance();
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

    private void flash(EditText editText){
        if(editText.getText().toString().isEmpty()){
            ColorDrawable f1 = new ColorDrawable(Color.argb(255,100,100,100));
            ColorDrawable f2 = new ColorDrawable(Color.argb(255,255,255,255));
            AnimationDrawable a = new AnimationDrawable();
            a.addFrame(f1,100);
            a.addFrame(f2,0);
        }
    }

    public void highlightField(View view){
        EditText textField = (EditText) view;

        if(textField.equals(correctText)){
            int greenColor = Color.argb(255,150, 255, 150);
            correctText.setBackgroundColor(greenColor);
        } else{
            int redColor = Color.argb(255, 255, 129, 109);
            correctText.setBackgroundColor(redColor);
        }

    }

    public void normalizeField(View view){
        int whiteColor = Color.argb(255,255,255,255);
        EditText textField = (EditText) view;
        textField.setBackgroundColor(whiteColor);
    }

    public void setThumbnail(){
        UserQuestion question = (UserQuestion)model.getQuestion(quizIndex,questionIndex);
        String imagePath = question.getImagePath();


        if(imageUri!=null){
            /*BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
            bitmap = Bitmap.createScaledBitmap(bitmap,thumbnail.getWidth(),thumbnail.getHeight(),true);*/
            Uri imageUri = Uri.parse(imagePath);
            thumbnail.setImageURI(imageUri);
        }
    }

    public void setTextFields(int quizIndex, int questionIndex){
        UserQuestion question = (UserQuestion)model.getQuestion(quizIndex,questionIndex);

        setQuestionString(question.getQuestionText());

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

    public void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(packageManager) != null) {
            imageUri = ImageFileHandler.getImageURI(imageStorageDir,currentContext);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            currentActivity.startActivityForResult(takePictureIntent, captureImageRequestCode);
        }
    }

    public void addMoreQuestions(int quizIndex, int questionIndex){
        Intent intent = new Intent(currentContext,CreateQuestionActivity.class);
        intent.putExtra("quizIndex",quizIndex);
        intent.putExtra("questionIndex",questionIndex);
        currentActivity.startActivity(intent);
    }

    public void endAddOfQuestions(){
        Intent intent = new Intent(currentContext,QuizListActivity.class);
        currentActivity.startActivity(intent);
    }

    public void update(Observable o, Object arg) {
        setThumbnail();
    }
}
