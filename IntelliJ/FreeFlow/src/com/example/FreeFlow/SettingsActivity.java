package com.example.FreeFlow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

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

        getActionBar().setDisplayHomeAsUpEnabled(true);

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
            ScoreAdapter scoreAdapter = new ScoreAdapter(this);
            scoreAdapter.dropBD();
        }
        if(id == R.id.buttonsave){
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_settings:

                break;
            default:
                onBackPressed();
        }
        return true;
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }*/

}