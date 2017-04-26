package com.example.murk.kwizgeeq;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.Quiz;
import com.example.murk.kwizgeeq.model.UserQuiz;

import java.util.ArrayList;

public class QuizList extends ListActivity {

    ListView listView;
    ArrayAdapter<Quiz> listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        KwizGeeQ.getInstance().quizzList.add(new UserQuiz("Spsh", new Color())); //Just adding a quiz to test out the methods.

        //Get listView from the layout. Same as by using findById()
        listView = getListView();

        listAdapter = new ArrayAdapter<Quiz>(this, android.R.layout.simple_list_item_1, KwizGeeQ.getInstance().quizzList);
        listView.setAdapter(listAdapter);

        //Play quiz
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                KwizGeeQ.getInstance().activeQuiz = KwizGeeQ.getInstance().quizzList.get(position); //Make the clicked quiz active quiz.

                Intent intent = new Intent( QuizList.this, Questioneer.class);
                startActivity(intent);
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
