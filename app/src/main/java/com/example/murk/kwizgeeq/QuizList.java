package com.example.murk.kwizgeeq;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.QuizListAdapter;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.view.QuestioneerActivity;

public class QuizList extends ListActivity {

    ListView listView;
    QuizListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        /* test quiz*/
        UserQuiz quiz1 = new UserQuiz("Spsh", new Color());
        KwizGeeQ.getInstance().quizzList.add(quiz1);
        UserQuestion question1 = new UserQuestion("quantos anos tienes?", null, new PointF(1,1), null);
        question1.addAnswer(new Answer(true, "4"));
        question1.addAnswer(new Answer(false, "42"));
        question1.addAnswer(new Answer(false, "24"));
        question1.addAnswer(new Answer(false, "2"));
        quiz1.addQuestion(question1);

        /*end - test quiz*/




        //Get listView from the layout. Same as by using findById()
        listView = getListView();


        //Set adapter to customized adapter
        adapter = new QuizListAdapter(this, KwizGeeQ.getInstance().quizzList);
        listView.setAdapter(adapter);



        //Play quiz
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position); //Make the clicked quiz active quiz.

                Intent intent = new Intent(QuizList.this, QuestioneerActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position);
                Intent intent = new Intent(QuizList.this, CreateQuiz.class);
                startActivity(intent);

                return true;
            }
        });
    } //end of "OnCreate" method.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fabPressed(View view){
        Intent intent = new Intent(this, CreateQuiz.class);
        startActivity(intent);
    }




}
