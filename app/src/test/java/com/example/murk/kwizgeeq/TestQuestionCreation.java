package com.example.murk.kwizgeeq;
import org.junit.Test;
import static org.junit.Assert.*;
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

        System.out.println(presenter.getCurrent().toString());

        presenter.createQuestion("hejhej");

        System.out.println(presenter.getCurrent().toString());

        presenter.addStringAnswer("right",true);
        presenter.addStringAnswer("false1",false);
        presenter.addStringAnswer("false2",false);
        presenter.addStringAnswer("false3",false);

        System.out.println(presenter.getCurrent().toString());
    }
}
