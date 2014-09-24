package com.example.FreeFlow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Sami on 08.09.14.
 */
public class PlayActivity extends Activity {
    Board board = null;
    onClickListener ocl;

    ImageButton prev;
    ImageButton reset;
    ImageButton next;
    int challengeId;
    int puzzleId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        ocl = new onClickListener();


        Bundle b = getIntent().getExtras();
        challengeId = b.getInt("challengeId");
        puzzleId = b.getInt("puzzleId");

        getActionBar().setDisplayHomeAsUpEnabled(true);

        prev = (ImageButton) findViewById(R.id.btn_prev);
        reset = (ImageButton) findViewById(R.id.btn_reset);
        next = (ImageButton) findViewById(R.id.btn_next_p);

        //int color = settings.getInt("pathColor", Color.CYAN);

        prev.setOnClickListener(ocl);
        reset.setOnClickListener(ocl);
        next.setOnClickListener(ocl);


        board = (Board) findViewById(R.id.board);

        SharedPreferences settings = getSharedPreferences( "SoundVibration", MODE_PRIVATE );

        Boolean Vib = settings.getBoolean( "Vibrate", true );
        Boolean Sou =  settings.getBoolean( "Sound", true );

        board.setVibrate(Vib);
        board.setSound(Sou);
        board.setActivity(this);

        board.setLevel(Global.getInstance().mChallenge.get(challengeId).mPuzzle.get(puzzleId));

        board.setTextFields((TextView) findViewById(R.id.flowsConnected),
                (TextView) findViewById(R.id.movesMade),
                (TextView) findViewById(R.id.bestMoves));


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences settings = getSharedPreferences( "SoundVibration", MODE_PRIVATE );

        Boolean Vib = settings.getBoolean( "Vibrate", true );
        Boolean Sou =  settings.getBoolean( "Sound", true );
        board.loadScoresFromDBAndSetHighscore();
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
        b.putInt("challengeId",challengeId);
        b.putInt("puzzleId", puzzleId);
        intent.putExtras(b);
        startActivityForResult(intent, 1);

        //finish();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                //int newChalenge = data.getIntExtra("challengeId",0);
                int newPuzzleId = data.getIntExtra("puzzleId",0);

                //if(newPuzzleId != puzzleId) {
                    board.setLevel(Global.getInstance().mChallenge.get(challengeId).mPuzzle.get(newPuzzleId));
                    board.loadLevel();
                    puzzleId = newPuzzleId;
                //}
                //else{
                 //   board.resetBoard();
                //}
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    protected class onClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.btn_prev:
                    if(puzzleId-1>=0) {
                        board.setLevel(Global.getInstance().mChallenge.get(challengeId).mPuzzle.get(puzzleId - 1));
                        board.loadLevel();
                        puzzleId = puzzleId - 1;
                    }
                    else{

                    }
                    break;
                case R.id.btn_reset:
                    board.resetBoard();
                    break;
                case R.id.btn_next_p:
                    if(puzzleId+1<Global.getInstance().mChallenge.get(challengeId).mPuzzle.size()) {
                        board.setLevel(Global.getInstance().mChallenge.get(challengeId).mPuzzle.get(puzzleId + 1));
                        board.loadLevel();
                        puzzleId = puzzleId + 1;
                    }
                    else{
                        //bam
                    }
                    break;
            }
        }
    }
}
