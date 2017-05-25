package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;

import java.io.Serializable;
import java.util.*;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.*;
import com.example.murk.kwizgeeq.events.EventBusWrapper;
import com.google.common.eventbus.Subscribe;

public class EditQuestionView extends Observable {

    private Question question;
    private final Activity currentActivity;
    private final Class<? extends Activity> editQuestionActivityClass;
    private final Class<? extends Activity> quizListActivityClass;

    private final com.google.common.eventbus.EventBus eventBus;

    private final PackageManager packageManager;
    private final int captureImageRequestCode;
    private final int questionEditingRequestCode;

    private final EditText questionText;
    private final EditText correctText;
    private final EditText wrongText1;
    private final EditText wrongText2;
    private final EditText wrongText3;

    private final ImageView correctImageView;
    private final ImageView wrong1ImageView;
    private final ImageView wrong2ImageView;
    private final ImageView wrong3ImageView;

    private final Switch answerSwitch;
    private final ViewFlipper viewFlipper;

    private final Button doneButton;
    private final Button nextButton;


    private final ImageView questionThumbnail;

    private Drawable originalEditText;

    public EditQuestionView(Activity currentActivity,
                            Class<? extends Activity> editQuestionActivityClass,
                            Class<? extends Activity> quizListActivityClass,
                            int captureImageRequestCode, int questionEditingRequestCode) {

        this.currentActivity = currentActivity;
        this.editQuestionActivityClass = editQuestionActivityClass;
        this.quizListActivityClass = quizListActivityClass;

        this.packageManager = currentActivity.getPackageManager();
        this.captureImageRequestCode = captureImageRequestCode;

        questionText = (EditText)currentActivity.findViewById(R.id.questionText);

        questionThumbnail = (ImageView)currentActivity.findViewById(R.id.thumbnail);

        answerSwitch = (Switch)currentActivity.findViewById(R.id.answerSwitch);

        viewFlipper = (ViewFlipper)currentActivity.findViewById(R.id.viewFlipper);

        correctText = (EditText)currentActivity.findViewById(R.id.correctText);
        wrongText1 = (EditText)currentActivity.findViewById(R.id.wrongText1);
        wrongText2 = (EditText)currentActivity.findViewById(R.id.wrongText2);
        wrongText3 = (EditText)currentActivity.findViewById(R.id.wrongText3);

        correctImageView = (ImageView)currentActivity.findViewById(R.id.correctImageView);
        wrong1ImageView = (ImageView)currentActivity.findViewById(R.id.wrong1ImageView);
        wrong2ImageView = (ImageView)currentActivity.findViewById(R.id.wrong2ImageView);
        wrong3ImageView = (ImageView)currentActivity.findViewById(R.id.wrong3ImageView);

        doneButton = (Button)currentActivity.findViewById(R.id.doneButton);
        nextButton = (Button)currentActivity.findViewById(R.id.nextQuestionButton);
        this.questionEditingRequestCode = questionEditingRequestCode;

        eventBus = EventBusWrapper.BUS;
        eventBus.register(this);

        originalEditText = correctText.getBackground();

    }

    public void setUserQuestion(Question question, boolean questionIsEdited){
        this.question = question;
        if(questionIsEdited){
            setEditButtonTexts();
            setTextFields();
        }
    }

    public void addOnFocusChangeListener(View.OnFocusChangeListener listener){
        correctText.setOnFocusChangeListener(listener);
        wrongText1.setOnFocusChangeListener(listener);
        wrongText2.setOnFocusChangeListener(listener);
        wrongText3.setOnFocusChangeListener(listener);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener){
        answerSwitch.setOnCheckedChangeListener(listener);
    }

    public void changeAnswerView(boolean isChecked){
        if(isChecked){
            viewFlipper.setDisplayedChild(1);
        } else {
            viewFlipper.setDisplayedChild(0);
        }
    }

    public boolean isSwitchChecked(){
        return answerSwitch.isChecked();
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

    private void setThumbnail(){
        String imagePath = question.getImagePath();

        if(imagePath!=null){
            Uri imageUri = Uri.parse(imagePath);
            questionThumbnail.setImageURI(imageUri);
        }
    }

    private void setEditButtonTexts(){
        doneButton.setText("Quit editing");
        nextButton.setText("Edit next");
    }

    private void setTextFields(){
        setQuestionString(question.getQuestionText());

        Iterator<Answer> answerIterator = question.answerIterator(false);

        while(answerIterator.hasNext()){
            Answer answer = answerIterator.next();

            if(answer.isCorrect()){
                if(answer.getAnswerType()==AnswerType.TEXT){
                    setCorrectStringAnswer(answer.getData());
                }
            } else{
                if(answer.getAnswerType()==AnswerType.TEXT){
                    setWrongStringAnswer(answer.getData());
                }
            }
        }
    }

    private void setAnswerImages() {
        Iterator<Answer> answerIterator = question.answerIterator(false);
        if(answerIterator.hasNext()){
            correctImageView.setImageURI(Uri.parse(answerIterator.next().getData()));
        }
        if(answerIterator.hasNext()){
            wrong1ImageView.setImageURI(Uri.parse(answerIterator.next().getData()));
        }
        if(answerIterator.hasNext()){
            wrong2ImageView.setImageURI(Uri.parse(answerIterator.next().getData()));
        }
        if(answerIterator.hasNext()){
            wrong3ImageView.setImageURI(Uri.parse(answerIterator.next().getData()));
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

    public void addMoreQuestions(List<Question> questions, int nextQuestionIndex){
        Intent intent = new Intent(currentActivity, editQuestionActivityClass);
        Bundle bundle = currentActivity.getIntent().getExtras();
        bundle.putSerializable("questions",(Serializable)questions);
        intent.putExtras(bundle);
        intent.putExtra("questionIndex",nextQuestionIndex);
        currentActivity.startActivityForResult(intent,questionEditingRequestCode);
    }

    public void killEditQuestionActivity(List<Question> questions){
        Intent intent = currentActivity.getIntent();
        Bundle bundle = intent.getExtras();
        bundle.putSerializable("questions",(Serializable)questions);
        intent.putExtras(bundle);
        currentActivity.setResult(Activity.RESULT_OK,intent);
        currentActivity.finish();
    }

    @Subscribe
    public void update(Question question){
        if(question == this.question){
            setThumbnail();
            setAnswerImages();
        }
    }


}
