package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by akimfors on 2017-05-16.
 */

public class storageUtils {

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


}
