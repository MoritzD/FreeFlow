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

    public void buttonClickDialog(View view) {
        Button button = (Button) view;
        int id = button.getId();
        Intent intent, resultIntent;
        Bundle b;
        switch (id) {
            case R.id.btn_pref:

                resultIntent = new Intent();
                // resultIntent.putExtra("challengeId", challegeId);
                if (puzzleId - 1 >= 0) {
                    resultIntent.putExtra("puzzleId", puzzleId - 1);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {

                }

                break;
            case R.id.btn_again:

                resultIntent = new Intent();
                //resultIntent.putExtra("challengeId", challegeId);
                resultIntent.putExtra("puzzleId", puzzleId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                break;
            case R.id.btn_next:

                resultIntent = new Intent();
                //resultIntent.putExtra("challengeId", challegeId);
                if (puzzleId + 1 < Global.getInstance().mChallenge.get(challegeId).mPuzzle.size()) {
                    resultIntent.putExtra("puzzleId", puzzleId + 1);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {

                }
                break;
        }

    }
}