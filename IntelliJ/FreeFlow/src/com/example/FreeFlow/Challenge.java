package com.example.FreeFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 22.09.14.
 */
public class Challenge {

    List<Puzzle> mPuzzle = new ArrayList<Puzzle>();

    String mName;

    public Challenge (String name, List<Puzzle> puzzles){

        mName = name;
        mPuzzle = puzzles;

    }


}
