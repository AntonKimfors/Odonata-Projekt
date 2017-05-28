package com.kwizgeeq;

import android.graphics.Color;

import com.kwizgeeq.model.Answer;
import com.kwizgeeq.model.AnswerType;
import com.kwizgeeq.model.Question;
import com.kwizgeeq.model.Quiz;
import com.kwizgeeq.model.Statistics;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Murk on 2017-05-28.
 */

public class QuizTest {

    Quiz quiz;
    ArrayList<Question> questionList;
    Question question1;
    Question question2;
    Question question3;

    @Before
    public void before(){
        quiz = new Quiz("Quiz Name", Color.CYAN);
        questionList = new ArrayList<>();
        question1 = new Question();
        question2 = new Question();
        question3 = new Question();
        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
    }

    @Test
    public void testCreation(){
        Quiz testQuiz1 = new Quiz("Quiz Name 1", Color.CYAN);
        Statistics testStatistics = new Statistics();
        Quiz testQuiz2 = new Quiz("Quiz Name 2", Color.BLUE, testStatistics);

        assertEquals(testQuiz1.getName(), "Quiz Name 1");
        assertEquals(testQuiz1.getListColor(), Color.CYAN);
        assertNotNull(testQuiz1.getQuestions());
        assertNotNull(testQuiz1.getBestStatistics());
        assertNotNull(testQuiz1.getCurrentTempStatistics());

        assertEquals(testQuiz2.getName(), "Quiz Name 2");
        assertEquals(testQuiz2.getListColor(), Color.BLUE);
        assertNotNull(testQuiz2.getQuestions());
        assertEquals(testQuiz2.getBestStatistics(), testStatistics);
        assertNotNull(testQuiz2.getCurrentTempStatistics());
    }

    @Test
    public void testName() {
        quiz.setName("Test Name");

        assertEquals(quiz.getName(), "Test Name");
    }

    @Test
    public void testColor() {
        quiz.setListColor(Color.BLACK);

        assertEquals(quiz.getListColor(), Color.BLACK);
    }

    @Test
    public void testAnswerIteratorText() {
        question1.addAnswer("1", true, AnswerType.TEXT);
        question1.addAnswer("2", false, AnswerType.TEXT);
        question1.addAnswer("3", false, AnswerType.TEXT);
        question1.addAnswer("4", false, AnswerType.TEXT);

        Iterator<Answer> answerIterator = question1.answerIterator(false);

        assertEquals(answerIterator.next().getData(), "1");
        assertEquals(answerIterator.next().getData(), "2");
        assertEquals(answerIterator.next().getData(), "3");
        assertEquals(answerIterator.next().getData(), "4");
    }

    /*@Test
    public void testAnswerIteratorImage() {
        question1.addAnswer("Image 1", true, AnswerType.IMAGE);
        question1.addAnswer("Image 2", false, AnswerType.IMAGE);
        question1.addAnswer("Image 3", false, AnswerType.IMAGE);
        question1.addAnswer("Image 4", false, AnswerType.IMAGE);

        Iterator<Answer> answerIterator = question1.answerIterator(false);

        assertEquals(answerIterator.next().getData(), "Image 1");
        assertEquals(answerIterator.next().getData(), "Image 2");
        assertEquals(answerIterator.next().getData(), "Image 3");
        assertEquals(answerIterator.next().getData(), "Image 4");
    }*/

    @Test
    public void testGetSize() {
        quiz.getQuestions().clear();
        quiz.getQuestions().add(question1);
        quiz.getQuestions().add(question2);
        quiz.getQuestions().add(question3);

        assertEquals(quiz.getSize(), 3);
    }

    @Test
    public void testQuestionAdding(){
        quiz.getQuestions().clear();
        quiz.getQuestions().add(question1);

        assertEquals(quiz.getQuestion(0), question1);
    }

    @Test
    public void testQuestionReplace(){
        ArrayList<Question> tempTestQuestionList = new ArrayList<>();
        ArrayList<Question> currentQuestionList = quiz.getQuestions();

        tempTestQuestionList.add(question1);
        tempTestQuestionList.add(question3);
        quiz.replaceQuestions(tempTestQuestionList);

        for (Question question : currentQuestionList) {
            assertEquals(question, tempTestQuestionList.get(currentQuestionList.indexOf(question)));
        }
    }

    @Test
    public void testTempStatistics(){
        Statistics testTempStatistics = quiz.getCurrentTempStatistics();

        quiz.resetCurrentTempStatistics();

        assertNotEquals(testTempStatistics, quiz.getCurrentTempStatistics());
    }

    @Test
    public void testBestStatistics(){
        Statistics oldBestStatistics = quiz.getBestStatistics();
        Statistics currentTempStatistics = quiz.getCurrentTempStatistics();

        oldBestStatistics.incQuestionCount();
        oldBestStatistics.incQuestionCount();
        oldBestStatistics.incQuestionCount();
        oldBestStatistics.incAnswerCorrectCount();
        oldBestStatistics.incAnswerCorrectCount();
        oldBestStatistics.incAnswerIncorrectCount();
        quiz.updateBestStatistics();

        assertNotEquals(oldBestStatistics, currentTempStatistics);

        currentTempStatistics.startTimer();
        currentTempStatistics.incQuestionCount();
        currentTempStatistics.incQuestionCount();
        currentTempStatistics.incQuestionCount();
        currentTempStatistics.incAnswerCorrectCount();
        currentTempStatistics.incAnswerCorrectCount();
        currentTempStatistics.incAnswerIncorrectCount();

        assertNotEquals(oldBestStatistics, currentTempStatistics);

    }

    @Test //TODO move to questiontest
    public void testImagePath(){
        question1.setImagePath("path/image");

        assertEquals(question1.getImagePath(), "path/image");
    }
}
