package com.kwizgeeq.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * Created by Henrik on 08/05/2017.
 *
 * * @author Henrik Håkansson
 * revised by Are Ehnberg, Marcus Olsson Lindvärn and Anton Kimfors
 */

public class ImageFileHandler {

    public static Uri getImageURI(File storageDir, Context context){
        File photoFile = null;
        Uri photoURI = null;

        try {
            photoFile = createImageFile(storageDir);
        } catch (IOException ex) {
            // Error occurred while creating the File
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(context,"com.fileprovider",
                    photoFile);
            /*photoURI = Uri.fromFile(photoFile);*/

        }

        return photoURI;
    }

    private static File createImageFile(File storageDir) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }
}
