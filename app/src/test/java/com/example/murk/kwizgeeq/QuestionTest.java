package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Henrik on 05/04/2017.
 */

public class QuestionTest {
    Question<String> q1;

    public static String generateString(Random rng, String characters, int length){
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    @Test
    public void test1(){
        q1 = new UserQuestion("What is the capital of Sweden?",null,null);
        Random rand = new Random();
        long time1 = System.currentTimeMillis();

        for(int i=0;i<10000000;i++){
            q1.addWrongAnswer(generateString(rand,"abcdefghijklmnopqrs",5));
        }
        for(int i=0;i<10000000;i++){
            q1.addCorrectAnswer(generateString(rand,"tuvxyzåäö1234567890",5));
        }

        Iterator<Answer<String>> it = q1.shuffledAnswerIterator();
        int correct = 0;
        int wrong = 0;

        while(it.hasNext()){
            Answer<String> answer = it.next();
            if(answer.isCorrect()){
                correct++;
            } else{
                wrong++;
            }

            q1.removeAnswer(answer);

        }

        long time2 = System.currentTimeMillis();

        System.out.println("Correct: " + correct);
        System.out.println("Wrong: " + wrong);
        System.out.println("Time: " + (time2-time1));
        assertTrue(true);
    }

    /*
    @Test
    public void testAdd() {
        Question<String> q = new UserQuestion("What is the capital of Sweden?",null,null);
        assertTrue(q.addCorrectAnswer("Stockholm"));
        assertTrue(q.addWrongAnswer("Copenhagen"));
        assertTrue(!q.addWrongAnswer("Copenhagen"));
        assertTrue(q.addWrongAnswer("Helsinki"));
        assertTrue(q.addWrongAnswer("Oslo"));
        assertTrue(!q.addWrongAnswer("Berlin"));
        assertTrue(q.addCorrectAnswer("Schtockholm"));
    }

    @Test
    public void testRemove(){
        Iterator<String> iterator = q1.shuffledAnswerIterator();
        assertTrue(iterator!=null);

    }

    @Test
    public void testEquals(){
        Question<String> q2 = new UserQuestion("What is the capital of Sweden?",null,null);
        q2.addCorrectAnswer("Stockholm");
        q2.addWrongAnswer("Helsinki");
        q2.addWrongAnswer("Copenhagen");
        q2.addWrongAnswer("Oslo");

        assertEquals(q1,q2);
    }
    */
}
