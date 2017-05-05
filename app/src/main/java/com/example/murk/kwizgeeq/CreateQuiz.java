package com.example.murk.kwizgeeq;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.CreateQuizAdapter;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.CreateQuestionView;

public class CreateQuiz extends ListActivity {
    private EditText QuizLabel;
    ListView listView;
    CreateQuizAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Flyttade bara upp dessa för att få det att funka. Test koden gör ju
        // så att test Question alltid finns där med när man skapar ett nytt quiz
        //vilket verkar funka nu
        //
        //Se QuizListAdapter för att få listan att se ut på annat sätt
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        //teststuff
        Answer testAnswer1 = new Answer(true,"fest");
        Answer testAnswer2 = new Answer(false,"fel");
        Answer testAnswer3 = new Answer(false,"fel");
        Answer testAnswer4 = new Answer(false,"fel");
        final Question testQuest = new UserQuestion("Meningen med livet jao?",null,0,0,null);
        testQuest.addAnswer(testAnswer1);
        testQuest.addAnswer(testAnswer2);
        testQuest.addAnswer(testAnswer3);
        testQuest.addAnswer(testAnswer4);
        Color testColor = new Color();
        UserQuiz testquiz = new UserQuiz("testname", testColor);
        testquiz.addQuestion(testQuest);
        KwizGeeQ.getInstance().activeQuiz = testquiz;
        //KwizGeeQ.getInstance().quizzList.add(testquiz);
        //end teststuff

        QuizLabel = (EditText) findViewById(R.id.QuizLabel);


        listView = getListView();

        adapter = new CreateQuizAdapter(this, KwizGeeQ.getInstance().activeQuiz.getQuestions());
        listView.setAdapter(adapter);

        //Edit Question
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

                Intent intent = new Intent(CreateQuiz.this, CreateQuestionView.class);
                intent.putExtra("selectedQuestion", KwizGeeQ.getInstance().activeQuiz.getQuestions().get(index));
                startActivity(intent);
            }
        });
        // end onCreate
    }
    public void fabPressed(View view){
        Intent intent = new Intent(this, CreateQuestionView.class);

        startActivity(intent);



    }

}
