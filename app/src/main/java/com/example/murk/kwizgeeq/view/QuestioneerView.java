package com.example.murk.kwizgeeq.view;

import android.view.View;

import com.example.murk.kwizgeeq.model.Quiz;

/**
 * Created by Are on 03/05/2017.
 */

public interface QuestioneerView {

    void flashAnswer(View view, int color);
    void updateQuestioneer(Quiz quiz);

}
