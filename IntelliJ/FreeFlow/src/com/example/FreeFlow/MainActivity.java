package com.example.FreeFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    int test1,test2,test3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }
    public void buttonClick (View view) {
        Button button = (Button) view;
        int id = button.getId();
        if (id == R.id.buttonPlay) {
            startActivity(new Intent(this, PlayActivity.class));
        }
    }

}
