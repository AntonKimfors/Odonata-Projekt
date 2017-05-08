package com.example.murk.kwizgeeq.activity;

import android.content.Intent;

/**
 * Created by Are on 04/05/2017.
 */

public interface NavigatableActivity {

    public void startActivity(Intent intent);
    public void startActivityForResult(Intent intent, int requestCode);
    public void onActivityResult(int requestCode, int resultCode, Intent data);
    public void finish();

}
