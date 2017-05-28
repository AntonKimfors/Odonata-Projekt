package com.example.murk.kwizgeeq.controller;

import android.content.Intent;
import android.view.View;

import com.example.murk.kwizgeeq.view.SplashScreenView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Murk on 2017-05-16.
 *
 *  * @author Marcus Olsson Lindvärn
 * revised by Anton Kimfors, Henrik Håkansson and Are Ehnberg
 */

public class SplashScreenController implements Observer {
    private SplashScreenView view;

    public SplashScreenController(SplashScreenView view){
        this.view = view;
    }

    public void update(Observable o, Object arg) {

    }
}
