package com.example.FreeFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 22.09.14.
 */
public class Challenge {

    List<Puzzle> mPuzzle = new ArrayList<Puzzle>();

    public String mName;
    public int mId;

    public Challenge (String name, int id, List<Puzzle> puzzles){

        mName = name;
        mPuzzle = puzzles;
        mId = id;

    }


}
