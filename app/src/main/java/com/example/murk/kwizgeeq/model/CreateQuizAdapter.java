package com.example.murk.kwizgeeq.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;

import java.util.ArrayList;

import static com.example.murk.kwizgeeq.R.color.*;

/**
 * Created by akimfors on 2017-04-26.
 */

public class CreateQuizAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<Question> mQuestions;

    public CreateQuizAdapter(Context context, ArrayList<Question> questions){
        mContext = context;
        mQuestions = questions;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuestions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;  //not using this. Tag items for easy refernece.
    }



}
