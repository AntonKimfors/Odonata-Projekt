package com.example.murk.kwizgeeq;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.graphics.Color;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;


import com.example.murk.kwizgeeq.activity.*;
import com.example.murk.kwizgeeq.model.*;
import com.example.murk.kwizgeeq.view.CreateQuestionView;

public class CreateQuiz extends ListActivity {
    private EditText QuizLabel;
    ListView listView;
    CreateQuizAdapter adapter;
    int quizIndex;

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
        //quizIndex = 0;

        //fixade några nya metoder i KwizGeeQ och det verkar fungera
        quizIndex = KwizGeeQ.getInstance().createUserQuiz("test",testColor);
        KwizGeeQ.getInstance().addQuestion(quizIndex,0,testQuest);

        //UserQuiz testquiz = new UserQuiz("testname", testColor);
        //testquiz.addQuestion(testQuest);
        //KwizGeeQ.getInstance().activeQuiz = testquiz;
        //KwizGeeQ.getInstance().quizzList.add(testquiz);
        //end teststuff

        QuizLabel = (EditText) findViewById(R.id.QuizLabel);

        listView = getListView();

        adapter = new CreateQuizAdapter(this, KwizGeeQ.getInstance().getQuiz(quizIndex).getQuestions());
        listView.setAdapter(adapter);

        //Edit Question
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

                Intent intent = new Intent(CreateQuiz.this, CreateQuestionActivity.class);
                intent.putExtra("quizIndex",quizIndex);
                //intent.putExtra("selectedQuestion", KwizGeeQ.getInstance().activeQuiz.getQuestions().get(index));
                startActivity(intent);
            }
        });
        // end onCreate
    }
    public void fabPressed(View view){
        //hämta quizIndex för nytt userQuiz genom att anropa denna metod
        //kanske inte mest lämpligt att göra det just här, men jag gjorde det mest för att visa
        int quizIndex = KwizGeeQ.getInstance().createUserQuiz(QuizLabel.getText().toString(),new Color());

        Intent intent = new Intent(CreateQuiz.this, CreateQuestionActivity.class);

        //skicka quizIndex med intenten så kommer createQuestion att lösa resten
        intent.putExtra("quizIndex",quizIndex);
        startActivity(intent);
    }

}
