package com.example.FreeFlow;

import java.util.List;

/**
 * Created by Sami on 15.09.14.
 */
public class Global {

    private static Global mInstance = new Global();
    public List<Pack> mPacks;
    public List<Challenge> mChallenge;
    public DBHelper dbHelper;

    private Global() {


    }

    public static Global getInstance() {

        return mInstance;
    }

}
