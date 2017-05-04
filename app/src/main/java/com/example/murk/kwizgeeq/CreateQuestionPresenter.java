package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Henrik on 02/05/2017.
 */

public class CreateQuestionPresenter {

    private CreateQuestionView view;
    private int quizIndex;
    private int questionIndex;

    //TODO: probably not needed as class variable
    String mCurrentPhotoPath;

    private KwizGeeQ model;

    public CreateQuestionPresenter(CreateQuestionView view){
        this.view = view;
        model = KwizGeeQ.getInstance();
    }

    public void onCreate() {
        quizIndex = view.getQuizIndex();
        questionIndex = view.getQuestionIndex();

        if(questionIndex<model.getQuiz(quizIndex).getSize()){
            setTextFields();
        }
    }

    public void onPause() {

    }
    public void onResume() {

    }
    public void onDestroy() {

    }

    public void nextButtonAction(){
        saveQuestion();
        view.addMoreQuestions(quizIndex,questionIndex++);
    }

    public void doneButtonAction(){
        saveQuestion();
        view.endAddOfQuestions();
    }

    public File createImageFile(File storageDir) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void saveQuestion(){
        createUserQuestion(view.getQuestionString(),null,0,0,null);

        addStringAnswer(view.getCorrectString(),true);

        addStringAnswer(view.getWrong1String(),false);
        addStringAnswer(view.getWrong2String(),false);
        addStringAnswer(view.getWrong3String(),false);
    }

    private void setTextFields(){
        UserQuestion question = (UserQuestion)model.getQuestion(quizIndex,questionIndex);

        view.setQuestionString(question.getQuestionStr());

        Iterator<Answer> answerIterator = question.answerIterator(false);

        while(answerIterator.hasNext()){
            Answer<String> answer = answerIterator.next();

            if(answer.isCorrect()){
                view.setCorrectStringAnswer(answer.getData());
            } else{
                view.setWrongStringAnswer(answer.getData());
            }
        }
    }

    private void createUserQuestion(String questionStr, String questionImg,
                                   double xPosition, double yPosition, String audioFile){

        UserQuestion question = new UserQuestion(questionStr,questionImg,xPosition,yPosition,audioFile);
        model.addQuestion(quizIndex,questionIndex,question);
    }

    private void addStringAnswer(String answerStr, boolean isCorrect){
        if(!answerStr.equals("")){
            Answer<String> answer = new Answer(isCorrect,answerStr);
            model.addAnswer(quizIndex,questionIndex,answer);
        }
    }
}
