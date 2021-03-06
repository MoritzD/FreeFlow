package com.example.FreeFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Sami on 23.09.14.
 */
public class PuzzleGrid extends Activity {

    Challenge mChallenge;
    Context mContext;
    Global mGlobal;
    private int challengeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_grid);
        mGlobal = Global.getInstance();
        mContext = getApplicationContext();

        Bundle b = getIntent().getExtras();
        final int cId = b.getInt("id");
        challengeId = cId;
        for (int i = 0; i < mGlobal.mChallenge.size(); i++) {
            if (mGlobal.mChallenge.get(i).mId == cId) {
                mChallenge = mGlobal.mChallenge.get(i);
            }
        }

        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Choose Level");

        final GridView gridview = (GridView) findViewById(R.id.gridview);


        PuzzleAdapter adapter = new PuzzleAdapter(mContext,
                R.layout.grid_puzzle_square, mChallenge.mPuzzle);

        gridview.setAdapter(adapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                Bundle b = new Bundle();
                b.putInt("challengeId", challengeId - 1);

                Puzzle clicked = (Puzzle) gridview.getItemAtPosition(position);
                b.putInt("puzzleId", Integer.parseInt(clicked.getId()) - 1);
                intent.putExtras(b);
                startActivity(intent);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            default:
                onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
