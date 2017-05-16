package com.example.murk.kwizgeeq.view;

import android.widget.ImageView;

import java.util.Observable;

/**
 * Created by Murk on 2017-05-16.
 */

public class SplashScreenView extends Observable {
    private ImageView splashImage;
    public SplashScreenView(ImageView splashImage){
        this.splashImage = splashImage;
    }
}
