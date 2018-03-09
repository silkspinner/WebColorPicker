package com.nimsdev.webcolorpicker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    // widget handles
    private SeekBar redSeekbar;
    private SeekBar greenSeekbar;
    private SeekBar blueSeekbar;

    private TextView redPercentTextView;
    private TextView greenPercentTextView;
    private TextView bluePercentTextView;
    private TextView hexTextView;

    private Button colorButton;

    private String[] hexVals = {"00", "33", "66", "99", "CC", "FF"};

    // seekbar values
    private int redValue = 0;
    private int greenValue = 0;
    private int blueValue = 0;

    // seekbar default values
    private int redDefaultValue = 3;
    private int greenDefaultValue = 3;
    private int blueDefaultValue = 3;

    //define the shared pref object
    private SharedPreferences savedValues;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the default values for the preferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //inflate widgets
        redSeekbar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekbar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekbar = (SeekBar) findViewById(R.id.blueSeekBar);

        redPercentTextView = (TextView) findViewById(R.id.redPercentTextView);
        greenPercentTextView = (TextView) findViewById(R.id.greenPercentTextView);
        bluePercentTextView = (TextView) findViewById(R.id.bluePercentTextView);
        hexTextView = (TextView) findViewById(R.id.hexTextView);
        colorButton = (Button) findViewById(R.id.colorButton);

        redSeekbar.setOnSeekBarChangeListener(this);
        greenSeekbar.setOnSeekBarChangeListener(this);
        blueSeekbar.setOnSeekBarChangeListener(this);

        //initialize color values
        redValue = 1;
        greenValue = 1;
        blueValue = 1;

        updateDisplay();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        //handle item events
        switch (item.getItemId()) {

            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void resetDefaultValues(View view) {

        //readSettings(view);

        redValue = redDefaultValue;
        greenValue = greenDefaultValue;
        blueValue = blueDefaultValue;

        redSeekbar.setProgress(redValue);
        greenSeekbar.setProgress(greenValue);
        blueSeekbar.setProgress(blueValue);

        updateDisplay();
    }

    @Override
    public void onPause(){

        //save the instance variablea
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putInt("redValue", redValue);
        editor.putInt("greenValue", greenValue);
        editor.putInt("blueValue", blueValue);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onResume(){

        super.onResume();

        // set default values
        redDefaultValue = Integer.parseInt(prefs.getString("list_preference_red", "0"));
        greenDefaultValue = Integer.parseInt(prefs.getString("list_preference_green", "0"));
        blueDefaultValue =  Integer.parseInt(prefs.getString("list_preference_blue", "0"));

        // set seekbars to saved values
        redValue = savedValues.getInt("redValue", 0 );
        greenValue = savedValues.getInt("greenValue", 0);
        blueValue = savedValues.getInt("blueValue", 0);

        redSeekbar.setProgress(redValue);
        greenSeekbar.setProgress(greenValue);
        blueSeekbar.setProgress(blueValue);

        updateDisplay();
    }

    public void updateDisplay() {
        // update display from RGB values

        redPercentTextView.setText(Integer.toString(redValue * 20) + "%");
        greenPercentTextView.setText(Integer.toString(greenValue * 20) + "%");
        bluePercentTextView.setText(Integer.toString(blueValue * 20) + "%");

        String hexValueStr = "#";
        hexValueStr += hexVals[redValue];
        hexValueStr += hexVals[greenValue];
        hexValueStr += hexVals[blueValue];

        hexTextView.setText(hexValueStr);
        colorButton.setBackgroundColor(Color.parseColor(hexValueStr));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int seekValue, boolean b) {
        switch (seekBar.getId())
        {
            case R.id.redSeekBar:
                redValue = seekValue;
                //redPercentTextView.setText(Integer.toString(redValue * 20) + "%");
                updateDisplay();
                break;
            case R.id.greenSeekBar:
                greenValue = seekValue;
                //greenPercentTextView.setText(Integer.toString(greenValue * 20) + "%");
                updateDisplay();
                break;
            case R.id.blueSeekBar:
                blueValue = seekValue;
                //bluePercentTextView.setText(Integer.toString(blueValue * 20) + "%");
                updateDisplay();
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateDisplay();
    }

}
