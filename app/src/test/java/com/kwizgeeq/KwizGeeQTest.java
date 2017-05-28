package com.kwizgeeq;

import android.app.Activity;
import android.graphics.Color;

import com.kwizgeeq.model.Answer;
import com.kwizgeeq.model.AnswerType;
import com.kwizgeeq.model.Question;
import com.kwizgeeq.model.Quiz;
import com.kwizgeeq.model.Statistics;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Murk on 2017-05-28.
 */

public class KwizGeeQTest {
    Activity activity;
    Quiz quiz = new Quiz("PartyQuiz!", Color.CYAN);
    Answer answer1 = new Answer(true, "Smurfen", AnswerType.TEXT);
    Answer picAnswer = new Answer(true, "location", AnswerType.IMAGE);
    Answer answer2 = new Answer(false, "Trump", AnswerType.TEXT);
    Answer answer3 = new Answer(false, "en Sten", AnswerType.TEXT);
    Answer answer4 = new Answer(false, "Anders Svensson Lindberg", AnswerType.TEXT);
    Question question = new Question();
    Question picQuestion = new Question();
    Statistics statistics = new Statistics();


    @Test
    public void testBasicFunctionality() {
        //START ---------------------------------- Statistics
        assertTrue(statistics.getQuizCount() == 0);
        assertTrue(statistics.getQuestionCount() == 0);
        assertTrue(statistics.getAnswerCorrectCount() == 0);
        assertTrue(statistics.getAnswerIncorrectCount() == 0);
        assertTrue(statistics.getSecondsSpent() == 0);

        question.setQuestionText("vad är IT-sektionens Helgon?");
        question.setImagePath("finabilder/annat");
        question.addAnswer(answer1.getData(), answer1.isCorrect(), answer1.getAnswerType());
        question.addAnswer(answer2.getData(), answer2.isCorrect(), answer2.getAnswerType());
        question.addAnswer(answer3.getData(), answer3.isCorrect(), answer3.getAnswerType());
        question.addAnswer(answer4.getData(), answer4.isCorrect(), answer4.getAnswerType());

        assertEquals(answer1.getAnswerType().toString(), "TEXT");
        assertEquals(picAnswer.getAnswerType().toString(), "IMAGE");


        Question testQuestion = question;
        assertEquals(question.equals(testQuestion), true);

        Question testQuestion2 = new Question();
        testQuestion2.setQuestionText("4-1");
        assertFalse(question.equals(testQuestion2));


        assertTrue(question.checkNumberOfAnswers(4));
        question.clearAnswers();
        assertTrue(question.checkNumberOfAnswers(0));

        Iterator<Answer> shuffledIterator = question.answerIterator(true);
        Iterator<Answer> nonShuffledIterator = question.answerIterator(true);
        assertFalse(shuffledIterator == nonShuffledIterator);


    }


    @Test
    public void testEquals() {
        Question testQuestion = question;
        assertEquals(question.equals(testQuestion), true);


        Question testQuestion2 = new Question();
        testQuestion2.setQuestionText("fest");
        testQuestion.setQuestionText(null);
        assertFalse(testQuestion.equals(testQuestion2));

        Question testQuestion3 = new Question();
        testQuestion.setImagePath("fest");
        testQuestion3.setImagePath(null);
        assertFalse(testQuestion.equals(testQuestion3));


    }


    @Test
    public void testAddAnswerWichPic() {
        picQuestion.addAnswer(picAnswer.getData(), picAnswer.isCorrect(), picAnswer.getAnswerType());
        assertFalse(picQuestion.equals(question));
    }

    @Test
    public void testClearAnswers() {

        question.addAnswer(answer1.getData(), answer1.isCorrect(), answer1.getAnswerType());
        question.addAnswer(answer2.getData(), answer2.isCorrect(), answer2.getAnswerType());
        question.addAnswer(answer3.getData(), answer3.isCorrect(), answer3.getAnswerType());
        question.addAnswer(answer4.getData(), answer4.isCorrect(), answer4.getAnswerType());
        assertTrue(question.checkNumberOfAnswers(4));
        question.clearAnswers();
        assertTrue(question.checkNumberOfAnswers(0));
    }

}

