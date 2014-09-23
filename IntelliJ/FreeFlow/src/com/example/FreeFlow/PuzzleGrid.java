package com.example.FreeFlow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

/**
 * Created by Sami on 23.09.14.
 */
public class PuzzleGrid extends Activity {

    Challenge mChallenge;
    Context mContext;
    Global mGlobal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_grid);
        mGlobal = Global.getInstance();
        mContext = getApplicationContext();

        Bundle b = getIntent().getExtras();
        int cId = b.getInt("id");
        for(int i = 0; i < mGlobal.mChallenge.size(); i++){
            if(mGlobal.mChallenge.get(i).mId == cId){
                mChallenge = mGlobal.mChallenge.get(i);
            }
        }



        GridView gridview = (GridView) findViewById(R.id.gridview);



        PuzzleAdapter adapter = new PuzzleAdapter(mContext,
                R.layout.grid_puzzle_square, mChallenge.mPuzzle);

        gridview.setAdapter(adapter);



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            }
        });
    }

}
