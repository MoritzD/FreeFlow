package com.example.FreeFlow;

import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 05.09.14.
 */

public class Cellpath {
    protected Paint m_paintPath  = new Paint();
    protected Path m_path = new Path();
    protected Coordinate m_start;
    protected Coordinate m_end;
    protected boolean isConnected;


    public Cellpath(int color, Coordinate start, Coordinate end){

        m_start = start;
        m_end = end;
        isConnected = false;

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setColor(color);
        m_paintPath.setStrokeWidth(50);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );
    }

    protected ArrayList<Coordinate> m_coords = new ArrayList<Coordinate>();

    public void append( Coordinate co ) {
        int idx = m_coords.indexOf(  co );
        if ( idx >= 0 ) {
            for ( int i=m_coords.size()-1; i > idx; --i ) {
                m_coords.remove(i);
            }
        }
        else {
            m_coords.add(co);
        }
    }

    public List<Coordinate> getCoordinates() {
        return m_coords;
    }

    public void reset() {
        m_coords.clear();
    }

    public boolean isEmpty() {
        return m_coords.isEmpty();
    }


    public boolean isTouched(Coordinate t_co) {

        for (Coordinate e_co : m_coords) {
            if (e_co.equals(t_co)) {
                return true;
            }
        }
        return false;

    }

    public void cutPath(Coordinate t_co) {

        int idx = m_coords.indexOf(  t_co );
        if ( idx >= 1 ) {
            for ( int i = m_coords.size()-1; i > idx-1; --i ) {
                m_coords.remove(i);
                Log.d("Cellpath", "The Path is cut!");
            }
        }
    }

    public boolean contains(Coordinate con_co){

        for (Coordinate e_co : m_coords) {
            if (e_co.equals(con_co)) {
                return true;
            }
        }
        return false;

    }

    public boolean isConnected(){

        if ((m_coords.contains(m_start) && m_coords.contains(m_end))) {
            isConnected = true;
            return true;
        }
        else {
            isConnected = false;
            return false;
        }
    }
}