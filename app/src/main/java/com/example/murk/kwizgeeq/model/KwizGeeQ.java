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

        if(quiz== null){
            throw new IndexOutOfBoundsException("Quiz doesn't exist");
        }

        quiz.addQuestionOnIndex(questionIndex,question);
    }

    public void addStringAnswer(int quizIndex, int questionIndex, String answerStr,
                                boolean isCorrect){
        Quiz quiz = quizzList.get(quizIndex);
        Answer<String> answer = new Answer(isCorrect,answerStr);
        quiz.addAnswer(questionIndex,answer);
    }

    public int getQuizSize(int quizIndex){
        Quiz quiz = quizzList.get(quizIndex);
        return quiz.getSize();
    }

    public void createUserQuestion(int quizIndex, int questionIndex, String questionStr,
                                   String questionImg, double xPosition, double yPosition,
                                   String audioFile){
        Quiz quiz = quizzList.get(quizIndex);

        if(quiz== null){
            throw new IndexOutOfBoundsException("Quiz doesn't exist");
        }

        UserQuestion question = new UserQuestion(questionStr,questionImg,xPosition,yPosition,
                audioFile);

        quiz.addQuestionOnIndex(questionIndex,question);
    }

    /**
     *  This method both returns value and has side effects
     *  but I have not came up with a better solution so far
     * @param name
     * @param color
     * @return the index of the created quiz
     */
    public int createUserQuiz(String name, Color color){
        UserQuiz userQuiz = new UserQuiz(name, color);
        quizzList.add(userQuiz);
        return quizzList.indexOf(userQuiz);
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
