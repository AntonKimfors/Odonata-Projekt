package com.example.murk.kwizgeeq.model;

import java.util.*;
import com.google.gson.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ {

    private ArrayList<Quiz> quizList;
    private Statistics globalStatistics;
    private Statistics currentQuizStatistics;
    private static KwizGeeQ singletonInstance = null;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
        }

        return singletonInstance;
    }

    private KwizGeeQ(){
        quizList = new ArrayList<Quiz>();
        globalStatistics = new Statistics();
    }

    public void startQuiz(){
        currentQuizStatistics = new Statistics();
    }

    public void endQuiz(){
        currentQuizStatistics.mergeWith(globalStatistics);
    }

    public Statistics getCurrentQuizStatistics() {
        return currentQuizStatistics;
    }

    public ArrayList<Quiz> getQuizList(){
        return quizList;
    }

    public Quiz getQuiz(int quizIndex){
        if(quizIndex<quizList.size()){
            return quizList.get(quizIndex);
        }

        throw new IndexOutOfBoundsException("Quiz on index "+ quizIndex +" does not exist.");
    }

    public ArrayList<Question> getQuestionList(int quizIndex){
        if(quizIndex < quizList.size()-1)
            return quizList.get(quizIndex).getQuestions();
        else
            throw new IndexOutOfBoundsException("Quiz on index "+ quizIndex +" does not exist.");
    }

    public int getQuizSize(int quizIndex){
        Quiz quiz = quizList.get(quizIndex);
        return quiz.getSize();
    }

    public String getQuizName(int quizIndex){
        return quizList.get(quizIndex).getName();
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }
}
