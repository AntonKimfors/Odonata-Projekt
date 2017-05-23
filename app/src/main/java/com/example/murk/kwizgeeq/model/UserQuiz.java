package com.example.murk.kwizgeeq.model;

import com.example.murk.kwizgeeq.events.EventBusWrapper;
import com.google.common.eventbus.EventBus;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Henrik on 04/04/2017.
 */

public class UserQuiz implements Iterable, Serializable{
    protected ArrayList<Question> questions;
    private int listColor;
    private String name;
    private Statistics bestStatistics;
    private Statistics currentTempStatistics;

    public UserQuiz(String name, int listColor) {
        questions = new ArrayList<>();
        this.name = name;
        this.listColor = listColor;
        bestStatistics = new Statistics();
        currentTempStatistics = new Statistics();
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

    public void replaceQuestions(ArrayList<Question> newQuestions){
        questions.clear();
        questions.addAll(newQuestions);

        System.out.println("replacing questions");

        EventBusWrapper.BUS.post(this);
    }

    public void removeQuestion(Question question){
        questions.remove(question);
    }

    public int getSize(){
        return questions.size();
    }

    public Question getQuestion(int questionIndex){
        return questions.get(questionIndex);
    }

    public Iterator<Question> questionsOnIndexIterator(List<Integer> indexes){
        Collections.sort(indexes);
        Iterator<Integer> indexIterator = indexes.iterator();
        List<Question> questionToReturn = new ArrayList<>();

        while(indexIterator.hasNext()){
            questionToReturn.add(questions.get(indexIterator.next()));
        }

        return questionToReturn.iterator();
    }

    public Statistics getBestStatistics() {
        return bestStatistics;
    }

    public Statistics getCurrentTempStatistics() {
        return currentTempStatistics;
    }

    public void resetCurrentTempStatistics(){
        currentTempStatistics = new Statistics();
    }

    public void updateBestStatistics() {
        int oldCorrectCount = bestStatistics.getAnswerCorrectCount();
        int newCorrectCount = currentTempStatistics.getAnswerCorrectCount();

        if (newCorrectCount > oldCorrectCount) {
            currentTempStatistics.mergeInto(bestStatistics);
        } else if (newCorrectCount == oldCorrectCount) {
            if (currentTempStatistics.getSecondsSpent() > bestStatistics.getSecondsSpent()) {
                currentTempStatistics.mergeInto(bestStatistics);
            }
        }
    }

    @Override
    public Iterator iterator() {
        return questions.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("UserQuiz name: ").append(name).append(System.lineSeparator());
        sb.append("Color: ").append(listColor).append(System.lineSeparator());
        for(Question q:questions){
            sb.append(q.toString());
        }

        return sb.toString();
    }
}
