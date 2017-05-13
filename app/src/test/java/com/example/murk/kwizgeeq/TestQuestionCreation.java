package com.example.murk.kwizgeeq;
import com.example.murk.kwizgeeq.model.*;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Henrik on 03/05/2017.
 */

public class TestQuestionCreation {
    @Test
    public void creationOfQuestion(){
        KwizGeeQ model = KwizGeeQ.getInstance();

        model.createUserQuiz("test",0);

        UserQuestion question = (UserQuestion)model.getQuestion(0,0);
        question.setQuestionText("Question 1");
        question.setImagePath("ImagePath 1");
        question.addAnswer("correct",true);
        question.addAnswer("wrong1",false);
        question.addAnswer("wrong2",false);
        question.addAnswer("wrong3",false);

        UserQuestion question2 = (UserQuestion)KwizGeeQ.getInstance().getQuestion(0,1);
        question2.setQuestionText("Question 1");
        question2.setImagePath("ImagePath 1");
        question2.addAnswer("correct",true);
        question2.addAnswer("wrong1",false);
        question2.addAnswer("wrong2",false);
        question2.addAnswer("wrong3",false);

        question.clearAnswers();
        question.addAnswer("new correct",true);
        question.addAnswer("wrong1",false);
        question.addAnswer("wrong2",false);
        question.addAnswer("wrong3",false);

        model.removeQuestion(0,0);

        assertTrue(model.getQuizSize(0)==1);
    }
}
