package com.example.murk.kwizgeeq.view;

import android.widget.ImageView;

import java.util.Observable;

/**
 * Created by Murk on 2017-05-16.
 *  * @author Marcus Olsson Lindvärn
 * revised by Anton Kimfors, Henrik Håkansson and Are Ehnberg
 */

public class SplashScreenView extends Observable {
    private ImageView splashImage;
    public SplashScreenView(ImageView splashImage){
        this.splashImage = splashImage;
    }
}
