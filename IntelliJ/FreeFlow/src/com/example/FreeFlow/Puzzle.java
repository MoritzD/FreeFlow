package com.example.FreeFlow;

/**
 * Created by Sami on 22.09.14.
 */
public class Puzzle {

    String mId;
    String mSize;
    String mFlows;
    String mPackId;
    String mChallengeId;

    public Puzzle(String id, String size, String flows, String packId, String challengeId) {

        mId = id;
        mSize = size;
        mFlows = flows;
        mPackId = packId;
        mChallengeId = challengeId;

    }

    public String getId() {

        return mId;

    }

    public String getSize() {

        return mSize;
    }

    public String getFlows() {

        return mFlows;
    }


}
