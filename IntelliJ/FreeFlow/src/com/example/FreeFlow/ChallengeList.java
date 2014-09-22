package com.example.FreeFlow;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

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


}
