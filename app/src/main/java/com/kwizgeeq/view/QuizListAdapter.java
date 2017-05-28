package com.kwizgeeq.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kwizgeeq.R;
import com.kwizgeeq.model.Quiz;

import java.util.ArrayList;

/**
 * Created by akimfors on 2017-04-26.
 *
 * @author Anton Kimfors
 * revised by Henrik Håkansson, Are Ehnberg and Marcus Olsson Lindvärn
 *
 */

public class QuizListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Quiz> mQuiz;

    protected QuizListAdapter(Context context, ArrayList<Quiz> quizs){
        mContext = context;
        mQuiz = quizs;
    }

    @Override
    public int getCount() {
        return mQuiz.size();
    }

    @Override
    public Object getItem(int position) {
        return mQuiz.get(position);
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
            holder.tvBestResult = (TextView) convertView.findViewById(R.id.tvBestResult);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_layout);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }

        Quiz quiz = mQuiz.get(position);
        holder.quizNameLabel.setText(quiz.getName());
        holder.quizQuestionAmountLabel.setText(quiz.getQuestions().size() + " Questions");
        holder.tvBestResult.setText(quiz.getBestStatistics().getCorrectAnswerPercentage() + "%");
        holder.relativeLayout.setBackgroundColor(quiz.getListColor());
        
        return convertView;
    }


    private static class ViewHolder {
        TextView quizNameLabel;
        TextView tvBestResult;
        TextView quizQuestionAmountLabel;
        RelativeLayout relativeLayout;
    }


}
