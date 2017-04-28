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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            //new convertView

            convertView = LayoutInflater.from(mContext).inflate(R.layout.content_item_create_question, null);
            holder = new ViewHolder();

            holder.questionLabel = (TextView) convertView.findViewById(R.id.questionLabel);
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relative_layout);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }

        UserQuestion question = (UserQuestion) mQuestions.get(position);
        holder.questionLabel.setText(question.getQuestionStr());
        //int RGB = android.graphics.Color.rgb(quiz.getListColor().RED,quiz.getListColor().GREEN,quiz.getListColor().BLUE);
        //holder.relativeLayout.setBackgroundColor(RGB);

        holder.relativeLayout.setBackgroundColor(443344400); //Just for testing




        return convertView;
    }


    private static class ViewHolder {
        TextView questionLabel;
        RelativeLayout relativeLayout;
    }

}
