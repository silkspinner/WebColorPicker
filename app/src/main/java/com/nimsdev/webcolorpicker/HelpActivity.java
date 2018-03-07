package com.nimsdev.webcolorpicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void displaySettings(View view) {
        //start settings activity
        startActivity( new Intent(this, SettingsActivity.class));

    }
}
