package com.kwizgeeq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.kwizgeeq.R;
import com.kwizgeeq.controller.SplashScreenController;
import com.kwizgeeq.view.SplashScreenView;

/*
* @author Marcus Olsson Lindvärn
* revised by Anton Kimfors, Henrik Håkansson and Are Ehnberg
*/

public class SplashScreenActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private SplashScreenController controller;
    private SplashScreenView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        view = new SplashScreenView((ImageView) findViewById(R.id.imgLogo));
        controller = new SplashScreenController(view);
        view.addObserver(controller);

        new Handler().postDelayed(new Runnable() {
 
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {


                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreenActivity.this, QuizListActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}