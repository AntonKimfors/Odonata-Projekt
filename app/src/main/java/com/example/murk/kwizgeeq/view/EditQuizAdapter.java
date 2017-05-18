package com.example.murk.kwizgeeq.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.murk.kwizgeeq.R;
import com.example.murk.kwizgeeq.model.Question;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.example.murk.kwizgeeq.model.UserQuestion;

import java.util.ArrayList;







public class EditQuizAdapter extends BaseAdapter {

    private UserQuiz userQuiz;
    private Context mContext;
    private ArrayList<Question> mQuestions;

    public EditQuizAdapter(Context context, ArrayList<Question> questions, UserQuiz inUserQuiz){
        mContext = context;
        mQuestions = questions;
        userQuiz = inUserQuiz;
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
        holder.questionLabel.setText(question.getQuestionText());

        holder.relativeLayout.setBackgroundColor(userQuiz.getListColor());


        return convertView;
    }


    private static class ViewHolder {
        TextView questionLabel;
        RelativeLayout relativeLayout;
    }

}
