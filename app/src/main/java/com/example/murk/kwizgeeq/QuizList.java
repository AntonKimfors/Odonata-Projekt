package com.example.murk.kwizgeeq;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.murk.kwizgeeq.activity.EditQuizActivity;
import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.QuizListAdapter;
import com.example.murk.kwizgeeq.model.UserQuestion;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.activity.QuestioneerActivity;

import org.xdty.preference.colorpicker.ColorPickerDialog;
import org.xdty.preference.colorpicker.ColorPickerSwatch;

public class QuizList extends ListActivity {
    private int mSelectedColor;
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



        AlertDialog.Builder mBuilder = new AlertDialog.Builder(QuizList.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_create_quiz, null);
        final EditText mEmail = (EditText) mView.findViewById(R.id.etQuizName);
        Button mCancel = (Button) mView.findViewById(R.id.btnCancel);
        Button mCreateQuiz = (Button) mView.findViewById(R.id.btnCreateQuiz);
        final Button mPickColor = (Button) mView.findViewById(R.id.btnPickColor);




        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        mPickColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedColor = ContextCompat.getColor(QuizList.this, R.color.flamingo);


                int[] mColors = getResources().getIntArray(R.array.default_rainbow);

                ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.color_picker_default_title,
                        mColors,
                        mSelectedColor,
                        5, // Number of columns
                        ColorPickerDialog.SIZE_SMALL,
                        true // True or False to enable or disable the serpentine effect
                        //0, // stroke width
                        //Color.BLACK // stroke color
                );

                dialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

                    @Override
                    public void onColorSelected(int color) {
                        mSelectedColor = color;
                        mPickColor.setBackgroundColor(mSelectedColor);

                    }

                });

                dialog.show(getFragmentManager(), "color_dialog_test");
            }
        });

        mCreateQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizList.this, CreateQuiz.class);
                startActivity(intent);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }




}
