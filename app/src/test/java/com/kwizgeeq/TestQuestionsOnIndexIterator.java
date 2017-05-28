package com.kwizgeeq;
import com.kwizgeeq.model.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/**
 * Created by Henrik on 15/05/2017.
 */

public class TestQuestionsOnIndexIterator {
    /*@Test
    public void testIterator(){
        UserQuiz userQuiz = new UserQuiz("test",0);

        for(int i=0;i<10;i++){
            userQuiz.addQuestion(createUserQuestion(i));
        }

        List<Integer> index = new ArrayList<>();
        index.add(2);
        index.add(3);
        index.add(8);
        Iterator<Question> wrong = userQuiz.questionsOnIndexIterator(index);
        int numberOfWrong = 0;

        while(wrong.hasNext()){
            numberOfWrong++;
            System.out.println(wrong.next());
        }

        assertTrue(numberOfWrong == 3);

    }

    private Question createUserQuestion(int i){
        Question userQuestion = new Question();
        userQuestion.setQuestionText("Question " + i);
        userQuestion.setImagePath("ImagePath"+i);
        userQuestion.addAnswer("Correct"+i,true,AnswerType.TEXT);
        userQuestion.addAnswer("WrongA"+i,false,AnswerType.TEXT);
        userQuestion.addAnswer("WrongB"+i,false,AnswerType.TEXT);
        userQuestion.addAnswer("WrongC"+i,false,AnswerType.TEXT);

        return userQuestion;
    }*/
}
