package com.example.FreeFlow;

import java.util.List;

/**
 * Created by Sami on 15.09.14.
 */
public class Global {

    public List<Pack> mPacks;
    public List<Challenge> mChallenge;

    private static Global mInstance = new Global();

    public static Global getInstance() {

        return mInstance;
    }

    private Global(){


    }

}
