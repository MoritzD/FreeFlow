package com.example.FreeFlow;

/**
 * Created by Sami on 22.09.14.
 */
public class Puzzle {

    String mId;
    String mSize;
    String mFlows;

    public Puzzle (String id, String size, String flows){

        mId = id;
        mSize = size;
        mFlows = flows;
    }

    public String getId(){

        return mId;

    }

    public String getSize(){

        return mSize;
    }

    public String getFlows(){

        return mFlows;
    }


}
