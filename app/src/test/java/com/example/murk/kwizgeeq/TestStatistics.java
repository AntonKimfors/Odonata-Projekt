package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.Statistics;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Are on 26/05/2017.
 */

public class TestStatistics {

    private Statistics testStatistics;

    @Before
    public void before(){
        testStatistics = new Statistics();
    }

    @Test
    public void testStatisticsCreation(){
        Statistics testStatisticsNew = new Statistics();

        assertEquals(testStatisticsNew.getQuizCount(), 0);
        assertEquals(testStatisticsNew.getQuestionCount(), 0);
        assertEquals(testStatisticsNew.getAnswerCorrectCount(), 0);
        assertEquals(testStatisticsNew.getAnswerIncorrectCount(), 0);
        assertEquals(testStatisticsNew.getSecondsSpent(), 0);
    }

    @Test
    public void testStatisticsCreationWithInput(){
        int quizCount = 1;
        int questionCount = 3;
        int answerCorrectCount = 2;
        int answerIncorrectCount = 1;
        int secondsSpent = 34;

        Statistics testStatisticsWithInput = new Statistics(quizCount, questionCount, answerCorrectCount, answerIncorrectCount, secondsSpent);

        assertEquals(testStatisticsWithInput.getQuizCount(), quizCount);
        assertEquals(testStatisticsWithInput.getQuestionCount(), questionCount);
        assertEquals(testStatisticsWithInput.getAnswerCorrectCount(), answerCorrectCount);
        assertEquals(testStatisticsWithInput.getAnswerIncorrectCount(), answerIncorrectCount);
        assertEquals(testStatisticsWithInput.getSecondsSpent(), secondsSpent);
    }

    @Test
    public void testStatisticsQuizIncremention(){
        int oldQuizCount = testStatistics.getQuizCount();

        testStatistics.incQuizCount();

        assertEquals(oldQuizCount, (testStatistics.getQuizCount() - 1));
    }

    @Test
    public void testStatisticsQuestionIncremention(){
        int oldQuestionCount = testStatistics.getQuestionCount();

        testStatistics.incQuestionCount();

        assertEquals(oldQuestionCount, (testStatistics.getQuestionCount() - 1));
    }

    @Test
    public void testStatisticsAnswerIncremention(){
        int oldAnswerCorrectCount = testStatistics.getAnswerCorrectCount();
        int oldAnswerIncorrectCount = testStatistics.getAnswerIncorrectCount();

        testStatistics.incAnswerCorrectCount();
        testStatistics.incAnswerIncorrectCount();

        assertEquals(oldAnswerCorrectCount, (testStatistics.getAnswerCorrectCount() - 1));
        assertEquals(oldAnswerIncorrectCount, (testStatistics.getAnswerIncorrectCount() - 1));
    }

    @Test
    public void testStatisticsTimer(){
        StopWatch testStopWatch = new StopWatch();

        testStatistics.startTimer();
        testStopWatch.start();try {
            Thread.sleep(5000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
        testStatistics.stopTimer();
        testStopWatch.stop();

        assertEquals((testStatistics.getSecondsSpent()), (int)(testStopWatch.getTime() / 1000));
    }

    @Test
    public void testStatisticsMerging(){
        int[] oldArr = new int[]{
                testStatistics.getQuizCount(),
                testStatistics.getQuestionCount(),
                testStatistics.getAnswerCorrectCount(),
                testStatistics.getAnswerIncorrectCount(),
                testStatistics.getSecondsSpent()
        };
        int[] newArr = new int[]{1, 2, 3, 4, 5};
        Statistics tempTestStatistics = new Statistics(newArr[0], newArr[1], newArr[2], newArr[3], newArr[4]);

        tempTestStatistics.mergeInto(testStatistics);

        assertEquals((newArr[0] + oldArr[0]), testStatistics.getQuizCount());
        assertEquals((newArr[1] + oldArr[1]), testStatistics.getQuestionCount());
        assertEquals((newArr[2] + oldArr[2]), testStatistics.getAnswerCorrectCount());
        assertEquals((newArr[3] + oldArr[3]), testStatistics.getAnswerIncorrectCount());
        assertEquals((newArr[4] + oldArr[4]), testStatistics.getSecondsSpent());
    }

    @Test
    public void testStatisticsPercentage(){
        int testPercentage1 = calcTestPercentage(testStatistics);
        int statPercentage1 = testStatistics.getCorrectAnswerPercentage();

        testStatistics.incAnswerCorrectCount();
        testStatistics.incQuestionCount();
        int testPercentage2 = calcTestPercentage(testStatistics);
        int statPercentage2 = testStatistics.getCorrectAnswerPercentage();

        testStatistics.incAnswerIncorrectCount();
        testStatistics.incQuestionCount();
        int testPercentage3 = calcTestPercentage(testStatistics);
        int statPercentage3 = testStatistics.getCorrectAnswerPercentage();


        assertEquals(testPercentage1, statPercentage1);
        assertEquals(testPercentage2, statPercentage2);
        assertEquals(testPercentage3, statPercentage3);
    }

    private int calcTestPercentage(Statistics statistics){
        if(statistics.getAnswerCorrectCount() == 0){
            return 0;
        } else if(statistics.getAnswerIncorrectCount() == 0){
            return 100;
        } else {
            return (int)((statistics.getAnswerCorrectCount() * 100.0f) / statistics.getQuestionCount());
        }
    }

}
