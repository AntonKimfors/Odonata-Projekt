package com.kwizgeeq;

import com.kwizgeeq.model.Answer;
import com.kwizgeeq.model.AnswerType;
import com.kwizgeeq.model.Question;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Murk on 2017-05-28.
 */

public class AnswerCreationTest {


    Answer answer1 = new Answer(true, "42", AnswerType.TEXT);
    Answer answer2 = new Answer(false, "Muffins", AnswerType.TEXT);
    Answer answer3 = new Answer(false, "Palsternacka", AnswerType.TEXT);
    Answer answer4 = new Answer(false, "Choklad", AnswerType.TEXT);
    @Test
    public void testIsCorrect() {


        assertTrue(answer1.isCorrect());
        assertFalse(answer2.isCorrect());
        assertFalse(answer3.isCorrect());
        assertFalse(answer4.isCorrect());
    }

    @Test
    public void testGetData() {

        assertTrue(answer1.getData()=="42");
        assertTrue(answer2.getData()=="Muffins");
        assertTrue(answer3.getData()=="Palsternacka");
        assertTrue(answer4.getData()=="Choklad");
        assertFalse(answer1.getData()=="Palsternacka");
    }
    @Test
    public void testGetAnswerType() {

        assertTrue(answer1.getAnswerType()== AnswerType.TEXT);
        assertTrue(answer2.getAnswerType()== AnswerType.TEXT);
        assertTrue(answer3.getAnswerType()== AnswerType.TEXT);
        assertTrue(answer4.getAnswerType()== AnswerType.TEXT);
        assertFalse(answer1.getAnswerType()== AnswerType.IMAGE);
    }
    @Test
    public void testEquals() {
        Answer testAnswer = answer1;
        assertTrue(answer1.equals(testAnswer));
    }

    @Test
    public void testToString() {
        assertEquals(answer1.toString(), "42 - true");
    }
    @Test
    public void testHashCode() {
        assertEquals(answer1.hashCode(), 1662);
    }





}
