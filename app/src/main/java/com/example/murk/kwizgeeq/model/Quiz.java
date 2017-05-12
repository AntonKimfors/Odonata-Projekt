package com.example.murk.kwizgeeq.model;

import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public abstract class Quiz implements Iterable{
    protected ArrayList<Question> questions;
    private int listColor;
    private String name;


    public Quiz(String name,int listColor) {
        questions = new ArrayList<>();
        this.name = name;
        this.listColor = listColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getListColor() {
        return listColor;
    }

    public void setListColor(int listColor) {
        this.listColor = listColor;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * eventuellt behövs inte denna metod
     */
    public void addQuestion(Question question){
        questions.add(question);
    }

    public void removeQuestion(int questionIndex){
        questions.remove(questionIndex);
    }

    public void addTextAnswer(int questionIndex, String answerText, boolean isCorrect){
        Question question = getQuestion(questionIndex);

        question.addAnswer(answerText,isCorrect);
    }

    public void removeQuestion(Question question){
        getQuestions().remove(question);
    }

    public int getSize(){
        return questions.size();
    }

    public Question getQuestion(int questionIndex){
        if(questionIndex<=questions.size()){

            if(questionIndex==questions.size()){
                Question question = createQuestion();
                questions.add(question);
                return question;
            } else {
                return questions.get(questionIndex);
            }

        } else{
            throw new IndexOutOfBoundsException("Requested question index too large. " +
                    "Only modification of existing or putting new question on end are allowed.");
        }
    }

    abstract Question createQuestion();

    public void setUserQuestionString(int questionIndex, String questionString){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setQuestionText(questionString);
        System.out.println(toString());
    }

    public void setUserQuestionImagePath(int questionIndex, String questionImagePath){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setImagePath(questionImagePath);
    }

    public void setUserQuestionPosition(int questionIndex, double x, double y){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setxPosition(x);
        userQuestion.setyPosition(y);
    }

    public void setUserQuestionAudioPath(int questionIndex, String audioPath){
        UserQuestion userQuestion = (UserQuestion)getQuestion(questionIndex);
        userQuestion.setAudioPath(audioPath);
    }


    @Override
    public Iterator iterator() {
        return questions.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(Question q:questions){
            sb.append(q.toString());
        }

        return sb.toString();
    }
}
