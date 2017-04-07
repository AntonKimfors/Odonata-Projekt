package com.example.murk.kwizgeeq;

import android.graphics.Color;
import android.provider.Settings;

import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.model.Answer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Murk on 2017-04-05.
 */

public class TestQuizCreation {
    UserQuestion testQuestion = new UserQuestion("who dis!?",null,null);
    UserQuiz testQuiz = new UserQuiz("TestQuiznumerouno",null);
    Answer corrAnswer = new Answer(true,"Fest");
    Answer wrongAnswer = new Answer(false,"inte fest");


    /*
    @Test
    public void testQuizCreation() {
        testQuiz.addQuestion(testQuestion);
        testQuestion.setCorrectAnswer(corrAnswer);
        testQuestion.addWrongAnswer(wrongAnswer);
        testQuestion.addWrongAnswer(wrongAnswer);
        testQuestion.addWrongAnswer(wrongAnswer);

        assertTrue(testQuiz.getName()=="TestQuiznumerouno");
        assertTrue(testQuiz.getQuestions().size()==1);
        assertTrue(testQuestion.getCorrectAnswer()== corrAnswer);

    }
    @Test
    public void testCopyMethod() {
        testQuiz.addQuestion(testQuestion);
        testQuestion.setCorrectAnswer(corrAnswer);
        testQuestion.addWrongAnswer(wrongAnswer);
        testQuestion.addWrongAnswer(wrongAnswer);
        testQuestion.addWrongAnswer(wrongAnswer);
        UserQuiz testClonedQuiz = testQuiz.copyUserQuiz();
        System.out.println(testClonedQuiz.getName());
        assertTrue(testClonedQuiz.getName()=="TestQuiznumerouno");
        System.out.println(testClonedQuiz.getQuestions().size());
        assertTrue(testClonedQuiz.getQuestions().size()==1);
        UserQuestion extraQuestion = new UserQuestion("extra!?",null,null);
        testClonedQuiz.addQuestion(extraQuestion);
        System.out.println(testClonedQuiz.getQuestions().size());
        assertTrue(testClonedQuiz.getQuestions().size()==2);
        assertTrue(testQuiz.getQuestions().size()==1);
        assertTrue(testClonedQuiz.getQuestions().get(0).getCorrectAnswer()==corrAnswer);




    }*/

}
