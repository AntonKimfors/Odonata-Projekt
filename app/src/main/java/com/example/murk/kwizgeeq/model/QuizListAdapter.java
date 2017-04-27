package com.example.murk.kwizgeeq.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;

/**
 * Created by akimfors on 2017-04-26.
 */

public class QuizListAdapter extends BaseAdapter {


    private Context mContext;
    private Quiz[] mQuiz;

    public QuizListAdapter(Context context, Quiz[] quizs){
        mContext = context;
        mQuiz = quizs;
    }

    @Override
    public int getCount() {
        return mQuiz.length;
    }

    @Override
    public Object getItem(int position) {
        return mQuiz[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;  //not using this. Tag items for easy refernece.
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            //new convertView

            convertView = LayoutInflater.from(mContext).inflate(R.layout.content_item_quiz_list, null);
            holder = new ViewHolder();
            holder.quizNameLabel = (TextView) convertView.findViewById(R.id.quizNameLabel);
            holder.quizNameLabel = (TextView) convertView.findViewById(R.id.quizQuestionAmountLabel);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }
        return null;
    }

    private static class ViewHolder {
        TextView quizNameLabel;
        TextView quizQuestionAmountLabel;
    }


}
