package com.example.FreeFlow;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Sami on 22.09.14.
 */
public class ChallengeList extends ListActivity {

    private Global mGlobals =  Global.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ChallengeAdapter adapter = new ChallengeAdapter(this, R.layout.list_challenge, mGlobals.mChallenge);

        setListAdapter(adapter);


    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        Intent intent = new Intent(getApplicationContext(), PuzzleGrid.class);
        Bundle b = new Bundle();

        Challenge clicked = (Challenge) l.getItemAtPosition(position);
        b.putInt("id", clicked.mId); //Your id
        b.putString("packfile", clicked.mPackfile);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }


}
