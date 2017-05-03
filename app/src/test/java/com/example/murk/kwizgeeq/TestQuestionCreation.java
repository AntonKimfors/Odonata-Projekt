package com.example.murk.kwizgeeq;
import org.junit.*;
/**
 * Created by Henrik on 03/05/2017.
 */

public class TestQuestionCreation {
    @Test
    public void creationOfQuestion(){
        CreateQuestionPresenter presenter = new CreateQuestionPresenter(new CreateQuestionView());
        presenter.onCreate();

        presenter.createQuestion("testString");

        presenter.addStringAnswer("right",true);

        presenter.addStringAnswer("false1",false);

        presenter.addStringAnswer("false2",false);

        presenter.addStringAnswer("false3",false);
    }
}
