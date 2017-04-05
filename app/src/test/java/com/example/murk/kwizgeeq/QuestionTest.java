package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuestion;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Henrik on 05/04/2017.
 */

public class QuestionTest {
    Question<String> q1;

    public QuestionTest(){
        q1 = new UserQuestion("What is the capital of Sweden?",null,null);
        q1.setCorrectAnswer("Stockholm");
        q1.addWrongAnswer("Helsinki");
        q1.addWrongAnswer("Copenhagen");
        q1.addWrongAnswer("Oslo");
    }


    public void testAdd() {
        assertTrue(q1.setCorrectAnswer("Stockholm"));
        assertTrue(q1.addWrongAnswer("Copenhagen"));
        assertTrue(!q1.addWrongAnswer("Copenhagen"));
        assertTrue(q1.addWrongAnswer("Helsinki"));
        assertTrue(q1.addWrongAnswer("Oslo"));
        assertTrue(!q1.addWrongAnswer("Berlin"));
        assertTrue(q1.setCorrectAnswer("Schtockholm"));
    }

    @Test
    public void testRemove(){
        Iterator<Answer<String>> iterator = q1.answerIterator();
        assertTrue(iterator!=null);

        Answer<String> answer = iterator.next();
        assertTrue(q1.removeWrongAnswer(answer));

        iterator = q1.answerIterator();
        assertTrue(iterator==null);

        iterator = q1.wrongAnswerIterator();
        assertTrue(iterator!=null);
    }

    @Test
    public void testEquals(){
        Question<String> q2 = new UserQuestion("What is the capital of Sweden?",null,null);
        q2.setCorrectAnswer("Stockholm");
        q2.addWrongAnswer("Helsinki");
        q2.addWrongAnswer("Copenhagen");
        q2.addWrongAnswer("Oslo");

        assertEquals(q1,q2);
    }
}
