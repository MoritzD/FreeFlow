package com.example.FreeFlow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sami on 24.09.14.
 */
public class ScoreAdapter {

    SQLiteDatabase db;
    DBHelper dbHelper;
    Context mContext;

    public ScoreAdapter( Context context ) {
        mContext = context;
    }

    public ScoreAdapter openToRead() {
        dbHelper = new DBHelper( mContext );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public ScoreAdapter openToWrite() {
        dbHelper = new DBHelper( mContext );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertScore( String pack, int challenge, int levelid, int score ) {
        String[] cols = DBHelper.TableScoreCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], pack);
        contentValues.put( cols[2], ((Integer)challenge).toString() );
        contentValues.put( cols[3], ((Integer)levelid).toString() );
        contentValues.put( cols[4], ((Integer)score).toString() );
        openToWrite();
        long value = db.insert(DBHelper.TableScore, null, contentValues );
        close();
        return value;
    }

    public long updateScore( String pack, int challenge, int levelid, int score ) {
        String[] cols = DBHelper.TableScoreCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], pack);
        contentValues.put( cols[2], ((Integer)challenge).toString() );
        contentValues.put( cols[3], ((Integer)levelid).toString() );
        contentValues.put( cols[4], ((Integer)score).toString() );
        openToWrite();
        long value = db.update(DBHelper.TableScore, contentValues,
                "pack = " + "'" + pack + "'" + " AND " + "challengeid = "
                + ((Integer)challenge).toString() + " AND "
                        + "levelid = " + ((Integer)levelid).toString(), null );
        close();
        return value;
    }

    public Cursor queryScores() {
        openToRead();
        Cursor cursor = db.query( DBHelper.TableScore,
                DBHelper.TableScoreCols, null, null, null, null, null);
        return cursor;
    }

    public Cursor queryScore(String pack, int challenge, int levelid) {
        openToRead();
        String[] cols = DBHelper.TableScoreCols;
        Cursor cursor = db.query( DBHelper.TableScore,
                cols,  "pack = " + "\'" + pack + "\'" + " AND " + "challengeid = "
                        + challenge + " AND "
                        + "levelid = " + levelid + ";", null, null, null, null);
        return cursor;
    }

}

