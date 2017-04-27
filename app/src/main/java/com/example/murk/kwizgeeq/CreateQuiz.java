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


import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuiz;

public class CreateQuiz extends ListActivity {
    public EditText QuizLabel;
    ListView listView;
    ArrayAdapter<Question> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //teststuff

        Color testColor = new Color();
        Quiz testquiz = new UserQuiz("testname", testColor);
        KwizGeeQ.getInstance().quizzList.add(testquiz);
        QuizLabel = (EditText) findViewById(R.id.QuizLabel);

        listView = getListView();   //tha fuq?!

        KwizGeeQ.getInstance().activeQuiz = testquiz;
        listAdapter = new ArrayAdapter<Question>(this, android.R.layout.simple_list_item_1, KwizGeeQ.getInstance().activeQuiz.getQuestions());
        listView.setAdapter(listAdapter);

        //Play quiz
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent( CreateQuiz.this, CreateQuestion.class);
                intent.putExtra("selectedQuestion", "fest");
                startActivity(intent);
            }
        });





        //end teststuff

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

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
