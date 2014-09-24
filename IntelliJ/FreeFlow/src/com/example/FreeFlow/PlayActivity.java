package com.example.FreeFlow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sami on 08.09.14.
 */
public class PlayActivity extends Activity {
    Board board = null;
    int challegeId;
    int puzzleId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        Bundle b = getIntent().getExtras();
        challegeId = b.getInt("challengeId");
        puzzleId = b.getInt("puzzleId");

        getActionBar().setDisplayHomeAsUpEnabled(true);

        //SharedPreferences settings = getSharedPreferences("ColorPref", MODE_PRIVATE);

        //int color = settings.getInt("pathColor", Color.CYAN);

        board = (Board) findViewById(R.id.board);

        SharedPreferences settings = getSharedPreferences( "SoundVibration", MODE_PRIVATE );

        Boolean Vib = settings.getBoolean( "Vibrate", true );
        Boolean Sou =  settings.getBoolean( "Sound", true );

        board.setVibrate(Vib);
        board.setSound(Sou);
        board.setActivity(this);

        board.setLevel(Global.getInstance().mChallenge.get(challegeId).mPuzzle.get(puzzleId));

        board.setTextFields((TextView) findViewById(R.id.flowsConnected),
                (TextView) findViewById(R.id.movesMade),
                (TextView) findViewById(R.id.bestMoves));




        //board.loadLevel(Global.getInstance().mChallenge.get(0).mPuzzle.get(0));
        //board.setColor(color);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences( "SoundVibration", MODE_PRIVATE );

        Boolean Vib = settings.getBoolean( "Vibrate", true );
        Boolean Sou =  settings.getBoolean( "Sound", true );

        board.setVibrate(Vib);
        board.setSound(Sou);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            default:
                  onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void startDialog() {
        Intent intent = new Intent(getApplicationContext(), DialogActivity.class);
        Bundle b = new Bundle();
        b.putInt("challengeId",challegeId);
        b.putInt("puzzleId", puzzleId);
        intent.putExtras(b);
        startActivityForResult(intent, 1);



        //finish();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
               int newChallenge = data.getIntExtra("challengeId",0);
               int newPuzzle = data.getIntExtra("puzzleId",0);

                board.setLevel(Global.getInstance().mChallenge.get(newChallenge).mPuzzle.get(newPuzzle));
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
