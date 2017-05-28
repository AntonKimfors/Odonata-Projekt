package com.kwizgeeq;

import android.graphics.Color;

import com.kwizgeeq.model.Answer;
import com.kwizgeeq.model.AnswerType;
import com.kwizgeeq.model.Question;
import com.kwizgeeq.model.Statistics;
import com.kwizgeeq.model.UserQuiz;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Murk on 2017-05-28.
 */

public class QuizCreationTest {
    UserQuiz quiz = new UserQuiz("PartyQuiz!",Color.CYAN);
    ArrayList<Question> qList = new ArrayList<>();
    Question question1 = new Question();
    Question question2 = new Question();
    Question question3 = new Question();
    Question question4 = new Question();
    ArrayList<Answer> aList2;

    @Test
    public void testName() {
        assertTrue(quiz.getName()== "PartyQuiz!");
        quiz.setName("PartyQuiz 2.0 !");
        assertFalse(quiz.getName()== "PartyQuiz");
        assertTrue(quiz.getName()== "PartyQuiz 2.0 !");

    }

    @Test
    public void testColor() {
        assertTrue(quiz.getListColor()== Color.CYAN);
        quiz.setListColor(Color.BLACK);
        assertFalse(quiz.getListColor() == Color.CYAN);
        assertTrue(quiz.getListColor() == Color.BLACK);

    }

    @Test
    public void testToString() {
        Question question = new Question();
        question.setQuestionText("Meningen med Livet?");
        assertEquals(question.toString(),"Question: Meningen med Livet?com.kwizgeeq.model.Question@1");  //TODO fixa efter paket
    }

    @Test
    public void testQuestionManagement() {
        question1.setQuestionText("Meningen med Livet?");
        question1.addAnswer("42", true, AnswerType.TEXT);
        question1.addAnswer("Muffins", false, AnswerType.TEXT);
        question1.addAnswer("Choklad", false, AnswerType.TEXT);
        question1.addAnswer("Palsternacka", false, AnswerType.TEXT);

        question2.setQuestionText("Är potatis en grönsak?");
        question2.addAnswer("Bara på Torsdagar", false, AnswerType.TEXT);
        question2.addAnswer("Ibland", false, AnswerType.TEXT);
        question2.addAnswer("Ja", false, AnswerType.TEXT);
        question2.addAnswer("Nej", true, AnswerType.TEXT);

        question3.setQuestionText("är Blåbärsmuffins en transportmetod?");
        question3.addAnswer("If you are Brave Enough", false, AnswerType.TEXT);
        question3.addAnswer("Ibland", false, AnswerType.TEXT);
        question3.addAnswer("Ja", false, AnswerType.TEXT);
        question3.addAnswer("Nej", true, AnswerType.TEXT);

        question4.setQuestionText("Om Gurkor var Djur, hur många ben skulle de ha ?");
        question4.addAnswer("2", false, AnswerType.TEXT);
        question4.addAnswer("3", false, AnswerType.TEXT);
        question4.addAnswer("6", false, AnswerType.TEXT);
        question4.addAnswer("8", true, AnswerType.TEXT);


        assertTrue(question1.getQuestionText() == "Meningen med Livet?");
        assertTrue(question2.getQuestionText() == "Är potatis en grönsak?");
        assertTrue(question3.getQuestionText() == "är Blåbärsmuffins en transportmetod?");
        assertTrue(question4.getQuestionText() == "Om Gurkor var Djur, hur många ben skulle de ha ?");

        qList.add(question3);
        qList.add(question2);
        qList.add(question1);
        qList.add(question4);
        assertTrue(quiz.getQuestions().size() == quiz.getSize());

        quiz.replaceQuestions(qList);
        assertTrue(quiz.getQuestion(0) == question3);



        //TODO Testa   updateBestStatistics(), getBestStatistics(), getCurrentTempStatistics(), questionsOnIndexIterator()

    }


    @Test
    public void testOrderedList(){
        question1.setQuestionText("Meningen med Livet?");
        question1.addAnswer("42", true, AnswerType.TEXT);
        aList2 = question1.getOrderedList();
        assertEquals(aList2.get(0).getData(), "42");

    }
    @Test
    public void testImagePath(){
        question1.setImagePath("DCIM/SecretPhotos");
        assertTrue(question1.getImagePath() == "DCIM/SecretPhotos");
    }

    @Test
    public void testAnswerIterator() {
        question1.addAnswer("42", true, AnswerType.TEXT);
        question1.addAnswer("Muffins", false, AnswerType.TEXT);
        question1.addAnswer("Choklad", false, AnswerType.TEXT);
        question1.addAnswer("Palsternacka", false, AnswerType.TEXT);

        Iterator<Answer> answerIterator= question1.answerIterator(false);

        assertTrue(answerIterator.next().getData() == "42");
        assertTrue(answerIterator.next().getData() == "Muffins");
        assertTrue(answerIterator.next().getData() == "Choklad");
        assertTrue(answerIterator.next().getData() == "Palsternacka");
    }
    @Test
    public void testGetSize() {
        assertTrue(quiz.getSize() == 0);
        quiz.getQuestions().add(question1);
        quiz.getQuestions().add(question2);
        quiz.getQuestions().add(question3);
        quiz.getQuestions().add(question4);
        assertTrue(quiz.getSize() == 4);
    }


    @Test
    public void testStatistics(){
        Statistics statistics = new Statistics();
        assertTrue(statistics.getQuizCount() == 0);
        assertTrue(statistics.getQuestionCount() == 0);
        assertTrue(statistics.getAnswerCorrectCount() == 0);
        assertTrue(statistics.getAnswerIncorrectCount() == 0);
        assertTrue(statistics.getSecondsSpent() == 0);

        // (int quizCount, int questionCount, int answerCorrectCount, int answerIncorrectCount, int secondsSpent)
        Statistics newStatistics = new Statistics(3,100,30,70,100);
        assertTrue(newStatistics.getQuizCount() == 3);
        assertTrue(newStatistics.getQuestionCount() == 100);
        assertTrue(newStatistics.getAnswerCorrectCount() == 30);
        assertTrue(newStatistics.getAnswerIncorrectCount() == 70);
        assertTrue(newStatistics.getSecondsSpent() == 100);
        Statistics betterStatics = new Statistics(3,100,30,70,99);

        UserQuiz statisticsQuiz = new UserQuiz("statistics Quiz",Color.CYAN,statistics);

        Statistics testStats = statisticsQuiz.getCurrentTempStatistics();
        assertTrue(testStats.getAnswerIncorrectCount() == 0);

    }
    @Test
    public void testGetCurrentTempStatistics() {
        Statistics statistics = new Statistics();
        UserQuiz statisticsQuiz = new UserQuiz("statistics Quiz",Color.CYAN,statistics);
        Statistics testStats = statisticsQuiz.getBestStatistics();


        assertTrue(statistics.getQuizCount() == 0);
        assertTrue(statistics.getQuestionCount() == 0);
        assertTrue(statistics.getAnswerCorrectCount() == 0);
        assertTrue(statistics.getAnswerIncorrectCount() == 0);
        assertTrue(statistics.getSecondsSpent() == 0);


        assertTrue(testStats.getQuizCount() == 0);
        assertTrue(testStats.getQuestionCount() == 0);
        assertTrue(testStats.getAnswerCorrectCount() == 0);
        assertTrue(testStats.getAnswerIncorrectCount() == 0);
        assertTrue(testStats.getSecondsSpent() == 0);

    }
    @Test
    public void testStatisticsManagement() {

        Statistics betterStatics = new Statistics(3,100,30,70,99);
        UserQuiz statisticsQuiz = new UserQuiz("statistics Quiz",Color.CYAN, betterStatics);
        Statistics testStats =  statisticsQuiz.getBestStatistics();

        assertTrue(testStats.getQuizCount() == 3);
        assertTrue(testStats.getQuestionCount() == 100);
        assertTrue(testStats.getAnswerCorrectCount() == 30);
        assertTrue(testStats.getAnswerIncorrectCount() == 70);
        assertTrue(testStats.getSecondsSpent() == 99);




    }




}
