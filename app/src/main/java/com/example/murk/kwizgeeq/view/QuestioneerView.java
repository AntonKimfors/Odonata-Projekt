package com.example.murk.kwizgeeq.view;

import android.view.View;

import com.example.murk.kwizgeeq.model.Quiz;

/**
 * Created by Are on 03/05/2017.
 */

public interface QuestioneerView {

    void flashCorrectAnswer(View view);
    void flashIncorrectAnswer(View view);
    void updateQuestioneer(Quiz quiz, int currentQuestion, int totalQuestions);
    void finishQuiz();

}
