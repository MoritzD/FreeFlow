package com.example.FreeFlow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sami on 08.09.14.
 */
public class PlayActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        Bundle b = getIntent().getExtras();
        int challegeId = b.getInt("challengeId");
        int puzzleId = b.getInt("puzzleId");

        //SharedPreferences settings = getSharedPreferences("ColorPref", MODE_PRIVATE);

        //int color = settings.getInt("pathColor", Color.CYAN);

        Board board = (Board) findViewById(R.id.board);
        board.setLevel(Global.getInstance().mChallenge.get(challegeId).mPuzzle.get(puzzleId));
        board.setTextFields((TextView) findViewById(R.id.flowsConnected),
                (TextView) findViewById(R.id.movesMade),
                (TextView) findViewById(R.id.bestMoves));


        //board.loadLevel(Global.getInstance().mChallenge.get(0).mPuzzle.get(0));
        //board.setColor(color);

    }

}
