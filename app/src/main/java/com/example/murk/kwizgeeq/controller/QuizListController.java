
package com.example.murk.kwizgeeq.controller;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.Log;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.QuizListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by akimfors on 2017-05-05.
 */

public class QuizListController implements Controller, Observer{

    private QuizListView view;
    private KwizGeeQ model;
    private Context context;

    public QuizListController(QuizListView view, Context context){
        this.view = view;
        this.context = context;
        model = KwizGeeQ.getInstance();

        //int quiz = model.createUserQuiz("Testquiz",Color.argb(255,100,100,100));
        /*model.setUserQuestionText(quiz,0,"Vad är svaret på fråga nummer 1?");
        model.addTextAnswer(quiz,0,"sant",true);
        model.addTextAnswer(quiz,0,"falskt",false);
        model.addTextAnswer(quiz,0,"falskt",false);
        model.addTextAnswer(quiz,0,"falskt",false);
        model.setUserQuestionText(quiz,1,"Vad är svaret på fråga nummer 2?");
        model.addTextAnswer(quiz,1,"sant",true);
        model.addTextAnswer(quiz,1,"falskt",false);
        model.addTextAnswer(quiz,1,"falskt",false);
        model.addTextAnswer(quiz,1,"falskt",false);
        model.setUserQuestionText(quiz,2,"Vad är svaret på fråga nummer 3?");
        model.addTextAnswer(quiz,2,"sant",true);
        model.addTextAnswer(quiz,2,"falskt",false);
        model.addTextAnswer(quiz,2,"falskt",false);
        model.addTextAnswer(quiz,2,"falskt",false);*/

    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {
        //TODO: Try saving the data

        ArrayList<Quiz> quizlist = model.getQuizList();

        Gson gson = new Gson();
        String jsonquizlist = gson.toJson(quizlist);
        
        try
        {
            File quizFile = new File(context.getFilesDir(), "quiz.txt");
            FileWriter fileWriter = new FileWriter(quizFile, false);
            BufferedWriter writer = new BufferedWriter((fileWriter));
            writer.write(jsonquizlist);
            writer.close();
        }
        catch (Exception e)
        {
            Log.e("Persistance", "Error saving file " + e.getMessage());
        }



    }

    @Override
    public void onResume(){
        try
        {
            File quizFile = new File(context.getFilesDir(), "quiz.txt");
            FileReader fileReader = new FileReader(quizFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String jsonquizlist = reader.readLine();

            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<Quiz>>(){}.getType();

            ArrayList quizlist = gson.fromJson(jsonquizlist, collectionType);


        }catch (Exception e){
            Log.e("Peresistence", "Could not read quizlist. Error " + e.getMessage());
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }


}

