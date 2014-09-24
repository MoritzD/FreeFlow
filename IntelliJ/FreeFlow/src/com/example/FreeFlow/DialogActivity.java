package com.example.FreeFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by moe on 24.09.14.
 */
public class DialogActivity extends Activity {
    int challegeId;
    int puzzleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        challegeId = b.getInt("challengeId");
        puzzleId = b.getInt("puzzleId");

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

    }
    public void buttonClickDialog(View view){
        Button button = (Button) view;
        int id = button.getId();
        Intent intent,resultIntent;
        Bundle b;
        switch (id){
            case R.id.btn_next:
/*
                intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                b = new Bundle();
                b.putInt("challengeId",challegeId);
                b.putInt("puzzleId", puzzleId);
                intent.putExtras(b);
                startActivity(intent);*/

                resultIntent = new Intent();
                resultIntent.putExtra("challengeId", challegeId);
                resultIntent.putExtra("puzzleId",puzzleId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                break;
            case R.id.btn_again:
                /*intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                b = new Bundle();
                b.putInt("challengeId",challegeId);
                b.putInt("puzzleId", puzzleId);
                intent.putExtras(b);
                startActivity(intent);
                finish();*/
                resultIntent = new Intent();
                resultIntent.putExtra("challengeId", challegeId);
                resultIntent.putExtra("puzzleId",puzzleId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                break;
            case R.id.btn_pref:
                /*intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                b = new Bundle();
                b.putInt("challengeId",challegeId);
                b.putInt("puzzleId", puzzleId);
                intent.putExtras(b);
                startActivity(intent);
                finish();*/

                resultIntent = new Intent();
                resultIntent.putExtra("challengeId", challegeId);
                resultIntent.putExtra("puzzleId",puzzleId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                break;
        }

    }
}