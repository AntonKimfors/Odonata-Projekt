package com.example.murk.kwizgeeq.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.murk.kwizgeeq.model.Answer;
import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.view.QuestioneerView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Are on 03/05/2017.
 */

public class QuestioneerController implements Controller, Observer{

    private QuestioneerView view;
    private KwizGeeQ model;
    private Class<? extends Activity> switchActivityClass;
    private Activity currentActivity;
    private ArrayList<Integer> outReplayIndexList;
    private ArrayList<Integer> inReplayIndexList;

    private int quizIndex;
    private int questionIndex;
    private int currentQuestion;
    private int quizSize;
    private boolean playingByIndex;
    private int REPLAY__REQUEST_CODE = 1;

    public QuestioneerController(Activity activity, QuestioneerView view) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = activity;
        this.outReplayIndexList = new ArrayList<>();
        this.inReplayIndexList = new ArrayList<>();
        this.quizIndex = activity.getIntent().getIntExtra("quizIndex", 0);
        this.questionIndex = 0;
        this.currentQuestion = 1;
        this.playingByIndex = false;
    }

    public void onCreate() {
        updateQuestionIndex();
        updateQuizSize();
        view.updateQuizRelatedItems(model.getQuizName(quizIndex), quizSize);
        view.updateQuestioneer(quizIndex, questionIndex, currentQuestion, quizSize);
        model.getCurrentQuizStatistics().startTimer();
    }

    //TODO: Anpassa vad som spara
    public void onPause() {

         /*//TODO: Try saving the data

        ArrayList<Quiz> quizlist = model.getQuizList();

        Gson gson = new Gson();
        String jsonquizlist = gson.toJson(quizlist);

        try
        {
            File quizFile = new File(context.getFilesDir(), "quiz.txt");
            FileWriter fileWriter = new FileWriter(quizFile, false);
            BufferedWriter writer = new BufferedWriter((fileWriter));
            writer.write(jsonquizlist);
            writer.close();
        }
        catch (Exception e)
        {
            Log.e("Persistance", "Error saving file " + e.getMessage());
        }*/




    }

    public void onResume() {

          /*/try
        {
            File quizFile = new File(context.getFilesDir(), "quiz.txt");
            FileReader fileReader = new FileReader(quizFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String jsonquizlist = reader.readLine();

            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<Quiz>>(){}.getType();

            ArrayList quizlist = gson.fromJson(jsonquizlist, collectionType);


        }catch (Exception e){
            Log.e("Peresistence", "Could not read quizlist. Error " + e.getMessage());
        }*/

    }

    public void onDestroy() {
        /*if(currentActivity.isFinishing()) {
            Intent intent = new Intent(currentActivity, switchActivityClass);
            intent.putExtra("quizIndex", quizIndex);
            intent.putExtra("outReplayIndexList", outReplayIndexList);
            currentActivity.startActivity(intent);
        }*/
    }

    private void updateQuestionIndex(){
        if(!playingByIndex) {
            questionIndex = currentQuestion-1;
        } else{
            questionIndex = inReplayIndexList.get(currentQuestion-1);
        }
    }

    private void updateQuizSize(){
        if(!playingByIndex){
            this.quizSize = model.getQuiz(quizIndex).getQuestions().size();
        } else {
            this.quizSize = inReplayIndexList.size();
        }
    }

    public void setSwitchActivityClass(Class<? extends Activity> activityClass){
        this.switchActivityClass = activityClass;
    }

    public void answerSelected(View view){
        model.getCurrentQuizStatistics().incQuestionCount();
        if(((Answer)view.getTag()).isCorrect()){
            model.getCurrentQuizStatistics().incAnswerCorrectCount();
            this.view.flashCorrectAnswer(view);
        } else{
            outReplayIndexList.add(questionIndex);
            model.getCurrentQuizStatistics().incAnswerIncorrectCount();
            this.view.flashIncorrectAnswer(view);
        }
    }

    public void finishQuestion(){
        if(currentQuestion == quizSize) {
            model.getCurrentQuizStatistics().stopTimer();
            model.getCurrentQuizStatistics().incQuizCount();
            model.updateQuizStatistics(quizIndex);
            if(!playingByIndex){
                model.endQuiz();
            }
            Intent intent = new Intent(currentActivity, switchActivityClass);
            intent.putExtra("quizIndex", quizIndex);
            currentActivity.startActivityForResult(intent, REPLAY__REQUEST_CODE);
        }
        else {
            currentQuestion++;
            updateQuestionIndex();
            view.updateQuestioneer(quizIndex, questionIndex, currentQuestion, quizSize);
        }
    }

    public void update(Observable o, Object arg) {
        if(arg == "question done"){
            finishQuestion();
        }
    }

    private void replayQuestions(){
        questionIndex = 0;
        currentQuestion = 1;
        outReplayIndexList.clear();
        model.startQuiz();
        onCreate();
    }

    private void replayQuestionsByIndex(){
        playingByIndex = true;
        currentQuestion = 1;
        inReplayIndexList = (ArrayList)outReplayIndexList.clone();
        outReplayIndexList.clear();
        model.startQuiz();
        onCreate();
    }

    public void onActivityResult(int requestCode, Intent data){
        if(requestCode == REPLAY__REQUEST_CODE){
            if(data.getBooleanExtra("replayByIndex", false)){
                replayQuestionsByIndex();
            } else {
                replayQuestions();
            }
        } else {
            view.closeQuestioneer();
        }
    }
}
