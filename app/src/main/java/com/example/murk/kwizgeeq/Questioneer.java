package com.example.murk.kwizgeeq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Questioneer extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar); // (for Custom theme)

        setContentView(R.layout.activity_questioneer);

    }
}
