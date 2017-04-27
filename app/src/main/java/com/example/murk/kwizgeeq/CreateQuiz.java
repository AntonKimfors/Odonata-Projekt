package com.example.murk.kwizgeeq;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;

public class CreateQuiz extends ListActivity {
    public EditText QuizLabel;
    ListView listView;
    ArrayAdapter<Question> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Flyttade bara upp dessa för att få det att funka. Test koden gör ju
        // så att test Question alltid finns där med när man skapar ett nytt quiz
        //vilket verkar funka nu
        //
        //Se QuizListAdapter för att få listan att se ut på annat sett
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        //teststuff
        Answer testAnswer1 = new Answer(true,"fest");
        Answer testAnswer2 = new Answer(false,"fel");
        Answer testAnswer3 = new Answer(false,"fel");
        Answer testAnswer4 = new Answer(false,"fel");
        final Question testQuest = new UserQuestion("Meningen med livet jao?",null,null,null);
        testQuest.addAnswer(testAnswer1);
        testQuest.addAnswer(testAnswer2);
        testQuest.addAnswer(testAnswer3);
        testQuest.addAnswer(testAnswer4);
        Color testColor = new Color();
        UserQuiz testquiz = new UserQuiz("testname", testColor);
        //KwizGeeQ.getInstance().quizzList.add(testquiz);
        QuizLabel = (EditText) findViewById(R.id.QuizLabel);
        testquiz.addQuestion(testQuest);
        listView = getListView();   //tha fuq?!

        KwizGeeQ.getInstance().activeQuiz = testquiz;
        System.out.println(KwizGeeQ.getInstance().activeQuiz.getName());
        listAdapter = new ArrayAdapter<Question>(this, android.R.layout.simple_list_item_1, KwizGeeQ.getInstance().activeQuiz.getQuestions());
        listView.setAdapter(listAdapter);

        //Play quiz
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

                Intent intent = new Intent(CreateQuiz.this, CreateQuestion.class);
                intent.putExtra("selectedQuestion", KwizGeeQ.getInstance().activeQuiz.getQuestions().get(index));
                startActivity(intent);
            }
        });





        //end teststuff - and en onCreate
    }
    public void fabPressed(View view){
        Intent intent = new Intent(this, CreateQuestion.class);

        startActivity(intent);

        Bundle applesData = getIntent().getExtras();
        if(applesData==null){
            return;
        }
        String dataRecieved = applesData.getString("stufftosend");


    }

}
