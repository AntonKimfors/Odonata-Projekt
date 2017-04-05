package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuestion;

import org.junit.BeforeClass;
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
        q1.addIncorrectAnswer("Helsinki");
        q1.addIncorrectAnswer("Copenhagen");
        q1.addIncorrectAnswer("Oslo");
    }


    public void testAdd() {
        assertTrue(q1.setCorrectAnswer("Stockholm"));
        assertTrue(q1.addIncorrectAnswer("Copenhagen"));
        assertTrue(!q1.addIncorrectAnswer("Copenhagen"));
        assertTrue(q1.addIncorrectAnswer("Helsinki"));
        assertTrue(q1.addIncorrectAnswer("Oslo"));
        assertTrue(!q1.addIncorrectAnswer("Berlin"));
        assertTrue(q1.setCorrectAnswer("Schtockholm"));
    }

    @Test
    public void testRemove(){
        Iterator<Answer<String>> iterator = q1.answerIterator();
        assertTrue(iterator!=null);

        Answer<String> answer = iterator.next();
        assertTrue(q1.removeIncorrectAnswer(answer));

        iterator = q1.answerIterator();
        assertTrue(iterator==null);

        iterator = q1.incorrectAnswerIterator();
        assertTrue(iterator!=null);
    }

    @Test
    public void testEquals(){
        Question<String> q2 = new UserQuestion("What is the capital of Sweden?",null,null);
        q2.setCorrectAnswer("Stockholm");
        q2.addIncorrectAnswer("Helsinki");
        q2.addIncorrectAnswer("Copenhagen");
        q2.addIncorrectAnswer("Oslo");

        assertEquals(q1,q2);
    }
}
