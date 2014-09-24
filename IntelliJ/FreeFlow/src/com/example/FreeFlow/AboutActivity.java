package com.example.FreeFlow;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by moe on 24.09.14.
 */
public class AboutActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void ButtonClicked(View view){
        finish();
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
}