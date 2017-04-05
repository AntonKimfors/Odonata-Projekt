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
        q1 = new UserQuestion("What is the capital of Sweden",null,null);
    }

    @Test
    public void testAdd() {
        assertTrue(q1.setCorrectAnswer("Stockholm"));
        assertTrue(q1.addIncorrectAnswer("Copenhagen"));
        assertTrue(!q1.addIncorrectAnswer("Copenhagen"));
        assertTrue(q1.addIncorrectAnswer("Helsinki"));
        assertTrue(q1.addIncorrectAnswer("Oslo"));
        assertTrue(!q1.addIncorrectAnswer("Berlin"));
    }

    public void testRemove(){
        Iterator<Answer<String>> iterator = q1.answerIterator();
    }
}
