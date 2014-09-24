package com.example.FreeFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 22.09.14.
 */
public class Challenge {

    public String mName;
    public int mId;
    protected String mPackfile;
    List<Puzzle> mPuzzle = new ArrayList<Puzzle>();

    public Challenge(String name, int id, String packfile, List<Puzzle> puzzles) {

        mName = name;
        mPuzzle = puzzles;
        mId = id;
        mPackfile = packfile;

    }


}
