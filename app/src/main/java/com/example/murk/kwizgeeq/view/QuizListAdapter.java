package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.UserQuiz;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-04-26.
 */

public class QuizListAdapter extends BaseAdapter {


    private Context mContext;
    private ArrayList<UserQuiz> mUserQuiz;

    public QuizListAdapter(Context context, ArrayList<UserQuiz> userQuizs){
        mContext = context;
        mUserQuiz = userQuizs;
    }

    @Override
    public int getCount() {
        return mUserQuiz.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserQuiz.get(position);
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
            holder.quizQuestionAmountLabel = (TextView) convertView.findViewById(R.id.quizQuestionAmountLabel);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_layout);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }

        UserQuiz userQuiz = mUserQuiz.get(position);
        holder.quizNameLabel.setText(userQuiz.getName());
        holder.quizQuestionAmountLabel.setText(userQuiz.getQuestions().size() + " Questions");
        holder.relativeLayout.setBackgroundColor(userQuiz.getListColor());
        
        return convertView;
    }


    private static class ViewHolder {
        TextView quizNameLabel;
        TextView quizQuestionAmountLabel;
        RelativeLayout relativeLayout;
    }


}
