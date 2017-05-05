package com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akimfors on 2017-04-05.
 */

public class KwizGeeQ {

    public ArrayList<Quiz> quizzList;
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
        quizzList = new ArrayList<Quiz>();
        activeQuestionIndex = 1;
    }

    public Quiz getQuiz(int index){
        return quizzList.get(index);
    }

    public Question getQuestion(int quizIndex, int questionIndex){
        return quizzList.get(quizIndex).getQuestion(questionIndex);
    }

    /**
     * Denna metod kanske inte behövs, se tex createUserQuestion
     * @param quizIndex
     * @param questionIndex
     * @param question
     */
    public void addQuestion(int quizIndex, int questionIndex, Question question){
        Quiz quiz = quizzList.get(quizIndex);
        quiz.addQuestionOnIndex(questionIndex,question);
    }

    public void addAnswer(int quizIndex, int questionIndex, Answer answer){
        Quiz quiz = quizzList.get(quizIndex);
        quiz.addAnswer(questionIndex,answer);
    }

    public int getQuizSize(int quizIndex){
        Quiz quiz = quizzList.get(quizIndex);
        return quiz.getSize();
    }

    public void createUserQuestion(int quizIndex, int questionIndex, String questionStr,
                                   String questionImg, double xPosition, double yPosition,
                                   String audioFile){

        UserQuestion question = new UserQuestion(questionStr,questionImg,xPosition,yPosition,
                audioFile);

        Quiz quiz = quizzList.get(quizIndex);
        quiz.addQuestionOnIndex(questionIndex,question);
    }

    public void addNewQuiz(String name, Color color){
        quizzList.add(new UserQuiz(name, color));
    };

    public void firePlayQuiz(Quiz quiz){
        activeQuiz = quiz;

    };

    public void fireEditQuiz(Quiz quiz){
        activeQuiz = quiz;
    };

    public boolean checkAnswerIsCorrect(Answer answer){
        return answer.isCorrect();
    }

    public void incActiveQuestion(){
        activeQuestionIndex++;
    }


}
