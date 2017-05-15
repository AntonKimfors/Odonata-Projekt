package com.example.murk.kwizgeeq;
import com.example.murk.kwizgeeq.model.*;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

/**
 * Created by Henrik on 15/05/2017.
 */

public class TestQuestionsOnIndexIterator {
    @Test
    public void testIterator(){
        Quiz quiz = new UserQuiz("test",0);

        for(int i=0;i<10;i++){
            quiz.addQuestion(createUserQuestion(i));
        }

        List<Integer> index = new ArrayList<>();
        index.add(2);
        index.add(3);
        index.add(8);
        Iterator<Question> wrong = quiz.questionsOnIndexIterator(index);
        int numberOfWrong = 0;

        while(wrong.hasNext()){
            numberOfWrong++;
            System.out.println(wrong.next());
        }

        assertTrue(numberOfWrong == 3);

    }

    private UserQuestion createUserQuestion(int i){
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setQuestionText("Question " + i);
        userQuestion.setImagePath("ImagePath"+i);
        userQuestion.addAnswer("Correct"+i,true,AnswerType.TEXT);
        userQuestion.addAnswer("WrongA"+i,false,AnswerType.TEXT);
        userQuestion.addAnswer("WrongB"+i,false,AnswerType.TEXT);
        userQuestion.addAnswer("WrongC"+i,false,AnswerType.TEXT);

        return userQuestion;
    }
}
