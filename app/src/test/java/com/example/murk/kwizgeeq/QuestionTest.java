package com.example.murk.kwizgeeq;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuestion;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Henrik on 05/04/2017.
 */

public class QuestionTest {
    @Test
    public void testAdd(){
        Question<String> q1 = new UserQuestion("What is the capital of Sweden",null,null);
        assertTrue(q1.addAnswer(new Answer(true,"Stockholm")));
        assertTrue(q1.addAnswer(new Answer(false,"Copenhagen")));
        assertTrue(!q1.addAnswer(new Answer(false,"Copenhagen")));
        assertTrue(q1.addAnswer(new Answer(false,"Helsinki")));
        assertTrue(q1.addAnswer(new Answer(false,"Oslo")));
        assertTrue(!q1.addAnswer(new Answer(false,"Berlin")));
    }
}
