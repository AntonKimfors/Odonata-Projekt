package com.example.murk.kwizgeeq.model;

import java.util.*;

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

    public Question getQuestion(int quizIndex, int questionIndex){
        Quiz quiz = getQuiz(quizIndex);
        return quiz.getQuestion(questionIndex);
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

    public void removeQuestion(int quizIndex, int questionIndex){
        Quiz quiz = getQuiz(quizIndex);
        quiz.removeQuestion(questionIndex);
    }

    /**
     *  This method both returns value and has side effects
     *  but I have not came up with a better solution so far
     * @param name
     * @param color
     * @return the index of the created quiz
     */
    public int createUserQuiz(String name, int color){
        UserQuiz userQuiz = new UserQuiz(name, color);
        quizList.add(userQuiz);
        return quizList.indexOf(userQuiz);
    }

    public Statistics getGlobalStatistics() {
        return globalStatistics;
    }
}
