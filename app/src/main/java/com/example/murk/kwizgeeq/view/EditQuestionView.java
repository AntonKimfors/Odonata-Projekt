package com.example.murk.kwizgeeq.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.*;

import java.util.*;

import com.example.murk.kwizgeeq.model.*;
import com.example.murk.kwizgeeq.utils.BusWrapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class EditQuestionView extends Observable {

    private UserQuestion userQuestion;
    private final Activity currentActivity;
    private final Class<? extends Activity> createQuestionActivityClass;
    private final Class<? extends Activity> quizListActivityClass;

    private final EventBus eventBus;

    private final PackageManager packageManager;
    private final int captureImageRequestCode;

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

    private ImageView thumbnail;

    private Drawable originalEditText;

    public EditQuestionView(Activity currentActivity,
                            Class<? extends Activity> createQuestionActivityClass,
                            Class<? extends Activity> quizListActivityClass,
                            PackageManager packageManager, int captureImageRequestCode,
                            EditText questionText, EditText correctText, EditText wrongText1,
                            EditText wrongText2, EditText wrongText3, ImageView thumbnail,
                            Button doneButton, Button nextButton, int quizIndex, int questionIndex,
                            ImageView correctImageView, ImageView wrong1ImageView,
                            ImageView wrong2ImageView, ImageView wrong3ImageView,
                            Switch answerSwitch, ViewFlipper viewFlipper) {

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
        this.doneButton = doneButton;
        this.nextButton = nextButton;
        this.correctImageView = correctImageView;
        this.wrong1ImageView = wrong1ImageView;
        this.wrong2ImageView = wrong2ImageView;
        this.wrong3ImageView = wrong3ImageView;

        this.answerSwitch = answerSwitch;
        this.viewFlipper = viewFlipper;

        eventBus = BusWrapper.BUS;
        eventBus.register(this);

        originalEditText = correctText.getBackground();

        setUserQuestion(quizIndex,questionIndex);
        setTextFields();

        if(questionIndex<KwizGeeQ.getInstance().getQuizSize(quizIndex)-1){
            setEditButtonTexts();
        }

    }

    private void setUserQuestion(int quizIndex,int questionIndex){
        KwizGeeQ model = KwizGeeQ.getInstance();
        Quiz quiz = model.getQuiz(quizIndex);
        if(quiz.getSize()>=questionIndex){
            userQuestion = new UserQuestion();
            quiz.addQuestion(userQuestion);
        } else{
            Question question = quiz.getQuestion(questionIndex);
            if(question instanceof UserQuestion){
                userQuestion = (UserQuestion) question;
            }
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
        String imagePath = userQuestion.getImagePath();

        if(imagePath!=null){
            Uri imageUri = Uri.parse(imagePath);
            thumbnail.setImageURI(imageUri);
        }
    }

    private void setEditButtonTexts(){
        doneButton.setText("Quit editing");
        nextButton.setText("Edit next");
    }

    private void setTextFields(){

        setQuestionString(userQuestion.getQuestionText());

        Iterator<Answer> answerIterator = userQuestion.answerIterator(false);

        while(answerIterator.hasNext()){
            Answer answer = answerIterator.next();

            if(answer.isCorrect()){
                if(answer.getAnswerType()==AnswerType.TEXT){
                    setCorrectStringAnswer(answer.getData());
                }
                if(answer.getAnswerType()==AnswerType.IMAGE){
                    setCorrectImageAnswer(answer.getData());
                }
            } else{
                if(answer.getAnswerType()==AnswerType.TEXT){
                    setWrongStringAnswer(answer.getData());
                }
                if(answer.getAnswerType()==AnswerType.IMAGE){
                    setWrongImageAnswer(answer.getData());
                }
            }
        }
    }

    private void setCorrectImageAnswer(String path){
        //TODO
    }

    private void setWrongImageAnswer(String path){
        //TODO
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

    @Subscribe
    public void update(UserQuestion question){
        if(question == userQuestion){
            setThumbnail();
            setTextFields();
        }
    }
}
