package com.example.murk.kwizgeeq.presenter;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.view.QuestioneerView;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerPresenter implements Presenter{

    private QuestioneerView view;
    private KwizGeeQ model;

    public QuestioneerPresenter(QuestioneerView view) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
    }

    public void onCreate() {
        model = KwizGeeQ.getInstance();
    }

    public void onPause() {

    }

    public void onResume() {

    }

    public void onDestroy() {

    }

}
