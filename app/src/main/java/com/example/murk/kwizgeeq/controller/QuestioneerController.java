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

    private int quizIndex;
    private int questionIndex;
    private boolean replayingByIndex;
    private int REPLAY__REQUEST_CODE = 1;

    public QuestioneerController(Activity activity, QuestioneerView view) {
        this.view = view;
        this.model = KwizGeeQ.getInstance();
        this.currentActivity = activity;
        this.outReplayIndexList = new ArrayList<>();
        this.quizIndex = activity.getIntent().getIntExtra("quizIndex", 0);
        this.questionIndex = 0;
        this.replayingByIndex = false;
    }

    public void onCreate() {
        view.updateQuizRelatedItems(quizIndex);
        view.updateQuestioneer(quizIndex, questionIndex);
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
        if(questionIndex + 1 == model.getQuiz(quizIndex).getQuestions().size()) {
            model.getCurrentQuizStatistics().incQuizCount();
            model.getCurrentQuizStatistics().stopTimer();
            model.updateQuizStatistics(quizIndex);
            model.endQuiz();
            Intent intent = new Intent(currentActivity, switchActivityClass);
            intent.putExtra("quizIndex", quizIndex);
            currentActivity.startActivityForResult(intent, REPLAY__REQUEST_CODE);
        }
        else {
            questionIndex++;
            view.updateQuestioneer(quizIndex, questionIndex);
        }
    }

    public void update(Observable o, Object arg) {
        if(arg == "question done"){
            finishQuestion();
        }
    }

    private void replayQuestions(){
        questionIndex = 0;
        outReplayIndexList.clear();
        model.startQuiz();
        onCreate();
    }

    private void replayQuestionsByIndex(){
        replayingByIndex = true;
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
