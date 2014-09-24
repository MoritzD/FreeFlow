package com.example.FreeFlow;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sami on 24.09.14.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "SCORE_DB";
    public static final int DB_VERSION = 1;

    public static final String TableScore = "score";
    public static final String[] TableScoreCols = { "_id", "pack", "challengeid", "levelid", "best" };
    //public static final String[] TableScoreCols = { "*" };

    private static final String sqlDropTableScore =
            "DROP TABLE IF EXISTS score;";

    private static final String sqlCreateTableScore =
            "CREATE TABLE IF NOT EXISTS score(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "pack TEXT NOT NULL," +
                    "challengeid INTEGER NOT NULL," +
                    "levelid INTEGER NOT NULL, " +
                    "best INTEGER " +
                    ");";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sqlCreateTableScore );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( sqlDropTableScore );
        onCreate( db );
    }
}
