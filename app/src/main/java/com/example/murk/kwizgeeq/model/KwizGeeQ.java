package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ extends Observable{

    private ArrayList<Quiz> quizList;
    public Quiz activeQuiz;
    public int activeQuestionIndex;
    private static KwizGeeQ singletonInstance = null;


    public static KwizGeeQ getInstance(){
        if (singletonInstance == null){
            singletonInstance = new KwizGeeQ();
        }

        return singletonInstance;
    }

    private KwizGeeQ(){
        quizList = new ArrayList<Quiz>();
        //activeQuestionIndex = 1;
    }
    public ArrayList<Quiz> getQuizList(){
        return quizList;
    }

    public Quiz getQuiz(int quizIndex){
        Quiz quiz = quizList.get(quizIndex);

        if(quiz== null){
            throw new IndexOutOfBoundsException("Quiz on index "+ quizIndex +" does not exist.");
        }

        return quiz;
    }

    public Question getQuestion(int quizIndex, int questionIndex){
        return quizList.get(quizIndex).getQuestion(questionIndex);
    }

    /*public void addQuestion(int quizIndex, int questionIndex, Question question){
        Quiz quiz = quizList.get(quizIndex);

        if(quiz== null){
            throw new IndexOutOfBoundsException("Quiz doesn't exist");
        }

        quiz.addQuestionOnIndex(questionIndex,question);
    }*/
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

    public void setUserQuestionText (int quizIndex, int questionIndex, String questionText){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionString(questionIndex,questionText);
    }

    public void setUserQuestionImagePath (int quizIndex, int questionIndex, String imagePath){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionImagePath(questionIndex,imagePath);

        UserQuestion userQuestion = (UserQuestion)userQuiz.getQuestion(questionIndex);
        setChanged();
        notifyObservers(userQuestion);
    }

    public void setUserQuestionAudioPath (int quizIndex, int questionIndex, String audioPath){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionAudioPath(questionIndex,audioPath);
    }

    public void setUserQuestionPosition (int quizIndex, int questionIndex, double x, double y){
        UserQuiz userQuiz = getUserQuiz(quizIndex);
        userQuiz.setUserQuestionPosition(questionIndex,x,y);
    }

    public void addTextAnswer(int quizIndex, int questionIndex, String answerText,
                                boolean isCorrect){
        Quiz quiz = quizList.get(quizIndex);
        quiz.addTextAnswer(questionIndex,answerText,isCorrect);
    }

    public void removeQuestion(int quizIndex, int questionIndex){
        Quiz quiz = getQuiz(quizIndex);
        quiz.removeQuestion(questionIndex);
    }

    private UserQuiz getUserQuiz(int quizIndex){
        Quiz quiz = getQuiz(quizIndex);

        if(quiz instanceof UserQuiz){
            return (UserQuiz)quiz;
        }

        throw new IllegalArgumentException("Requested quiz is not editible for users");
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

}
