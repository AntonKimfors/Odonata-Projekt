package com.example.murk.kwizgeeq.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.os.Environment.getExternalStorageState;

/**
 * Created by akimfors on 2017-05-12.
 */

public class FileUtilites {

    public static void saveAssetImage(Context context, String assetName) {
        File fileDirectory = getFileDirectory(context);
        File fileToWrite = new File(fileDirectory, assetName);

        AssetManager assetManager = context.getAssets();
        try {
            InputStream in = assetManager.open(assetName);
            FileOutputStream out = new FileOutputStream(fileToWrite);
            copyFile(in ,out);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //FileOutputStream outOld = context.openFileOutput(fileToWrite.getAbsolutePath(), Context.MODE_PRIVATE);
    }

    public static File getFileDirectory(Context context) {
        String storageType = StorageType.PRIVATE_EXTERNAL;
        if (storageType.equals(StorageType.INTERNAL)) {
            return context.getFilesDir();
        } else{
            if(isExternalStorageWritable()){
                if(storageType.equals((StorageType.PRIVATE_EXTERNAL))){
                    return context.getExternalFilesDir(null);
                } else{
                    return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                }
            } else {
                return context.getFilesDir();
            }
        }
    }


    private static void copyFile(InputStream in, OutputStream out) throws IOException{
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public static File [] listFiles(Context context) {
        File fileDirectory = getFileDirectory(context);
        File [] filteredFiles = fileDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if(file.getAbsolutePath().contains(".jpg")) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        return filteredFiles;
    }

    public static void saveImage(Context context, Bitmap bitmap, String name) {
        File fileDirectory = getFileDirectory(context);
        File fileToWrite = new File(fileDirectory, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Checks if external storage is available for read and write
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }



}
