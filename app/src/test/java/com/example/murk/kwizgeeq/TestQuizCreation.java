package com.example.murk.kwizgeeq.com.example.murk.kwizgeeq.model;

import android.graphics.Color;

import com.example.murk.kwizgeeq.model.UserQuiz;

import org.junit.Test;

/**
 * Created by Murk on 2017-04-05.
 */

public class TestQuizCreation {
    @Test
    public void testQuizCreation() {
        int parseColor = (Color.parseColor("#636161"));
        UserQuiz testQuiz = new UserQuiz("fest",parseColor);
        System.out.print(parseColor);

    }
}
