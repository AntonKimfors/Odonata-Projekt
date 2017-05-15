package com.example.murk.kwizgeeq;
import com.example.murk.kwizgeeq.model.*;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by Henrik on 03/05/2017.
 */

public class TestQuestionCreation {
    @Test
    public void creationOfQuestion(){
        KwizGeeQ model = KwizGeeQ.getInstance();

        List<Quiz> quizList = model.getQuizList();
        Quiz quiz = new UserQuiz("testQuiz",0);
        quizList.add(quiz);

        UserQuestion question = new UserQuestion();
        quiz.addQuestion(question);
        question.setQuestionText("Question 1");
        question.setImagePath("ImagePath 1");
        question.addAnswer("correct",true,AnswerType.TEXT);
        question.addAnswer("wrong1",false,AnswerType.TEXT);
        question.addAnswer("wrong2",false,AnswerType.TEXT);
        question.addAnswer("wrong3",false,AnswerType.TEXT);

        UserQuestion question2 = new UserQuestion();
        quiz.addQuestion(question2);
        question2.setQuestionText("Question 1");
        question2.setImagePath("ImagePath 1");
        question2.addAnswer("correct",true,AnswerType.TEXT);
        question2.addAnswer("wrong1",false,AnswerType.TEXT);
        question2.addAnswer("wrong2",false,AnswerType.TEXT);
        question2.addAnswer("wrong3",false,AnswerType.TEXT);

        question.clearAnswers();
        question.addAnswer("new correct",true,AnswerType.TEXT);
        question.addAnswer("wrong1",false,AnswerType.TEXT);
        question.addAnswer("wrong2",false,AnswerType.TEXT);
        question.addAnswer("wrong3",false,AnswerType.TEXT);

        quiz.removeQuestion(question2);

        System.out.println(quiz);
        assertTrue(model.getQuizSize(0)==1);
    }
}
