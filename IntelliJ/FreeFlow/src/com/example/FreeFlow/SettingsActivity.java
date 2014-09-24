package com.example.FreeFlow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

/**
 * Created by moe on 24.09.14.
 */
public class SettingsActivity extends Activity {

    Switch Vibration,Sound;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Vibration=(Switch) findViewById(R.id.vibration);
        Sound = (Switch) findViewById(R.id.sound);


        SharedPreferences settings = getSharedPreferences( "SoundVibration", MODE_PRIVATE );

        Boolean Vib = settings.getBoolean( "Vibrate", true );
        Boolean Sou =  settings.getBoolean( "Sound", true );

        Vibration.setChecked(Vib);
        Sound.setChecked(Sou);

    }

    public void switchClicked(View view){
        Switch swi = (Switch) view;
        int id = swi.getId();
        SharedPreferences settings = getSharedPreferences( "SoundVibration", MODE_PRIVATE );

        if(id==R.id.vibration) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("Vibrate", swi.isChecked());
            editor.commit();
        }
        if(id==R.id.sound) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("Sound", swi.isChecked());
            editor.commit();
        }

    }
    public void ButtonClicked(View view){
        Button button = (Button) view;
        int id = button.getId();
        if(id == R.id.deleteButton){
            // TODO: Put code here to Delete Database.
        }
        if(id == R.id.buttonsave){
            finish();
        }
    }
}