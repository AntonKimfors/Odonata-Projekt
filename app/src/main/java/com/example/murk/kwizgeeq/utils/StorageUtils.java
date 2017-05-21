package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.example.murk.kwizgeeq.model.KwizGeeQ;
import com.example.murk.kwizgeeq.model.UserQuiz;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by akimfors on 2017-05-16.
 */

public class StorageUtils {

    //TODO: DELETE CLASS??



    public static void saveQuizList(Context context) {
        //TODO: Try saving the data

        ArrayList<UserQuiz> quizlist = KwizGeeQ.getInstance().getUserQuizList();

        Gson gson = new Gson();
        String jsonquizlist = gson.toJson(quizlist);

        try
        {
            //File quizFile = new File(context.getFilesDir(), "quiz.txt");
            File quizFile = getFileDirectory(context);
            FileWriter fileWriter = new FileWriter(quizFile, false);
            BufferedWriter writer = new BufferedWriter((fileWriter));
            writer.write(jsonquizlist);
            writer.close();
        }
        catch (Exception e)
        {
            Log.e("Persistance", "Error saving file " + e.getMessage());
        }
    }//end saveQuizList

    public static void readQuizList(Context context){
        try
        {
            File quizFile = getFileDirectory(context);
            FileReader fileReader = new FileReader(quizFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String jsonquizlist = reader.readLine();
            Gson gson = new Gson();
            Type collectionType = new TypeToken<ArrayList<UserQuiz>>(){}.getType();

            ArrayList quizlist = gson.fromJson(jsonquizlist, collectionType);

            //TODO: Setter for KwizGeeq QuizList
            //KwizGeeQ.getInstance().getUserQuizList() = quizlist;

        }catch (Exception e){
            Log.e("Peresistence", "Could not read quizlist. Error " + e.getMessage());
        }


    } //end readQuizList



    public static void saveImage(Context context, Bitmap bitmap){
        File fileDirectory = getFileDirectory(context);
        File imageFile = new File(fileDirectory, getImageName());

        try{
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public static File getFileDirectory(Context context) {
        String storageType = StorageType.PRIVATE_EXTERNAL; //Choose what sort of storage type

        if (storageType.equals(StorageType.INTERNAL)) {
            return context.getFilesDir(); //If internal, just return it
        } else {
            if (isExternalStorageWritable()) {
                if (storageType.equals((StorageType.PRIVATE_EXTERNAL))) {
                    return context.getExternalFilesDir(null); //get private externel files dir //TODO: Why is parameter null?
                } else {
                    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //Get public external file dir with standard "DIRECTORY_PICTURES"
                }
            } else {
                return context.getFilesDir(); //If not writable, return internal anyways
            }
        }
    }//end getFileDirectory


    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }//end isExternalStorageWritable

    public static String getImageName(){
        //TODO: REturn a image name
        return "image.jpg";
    }


}
