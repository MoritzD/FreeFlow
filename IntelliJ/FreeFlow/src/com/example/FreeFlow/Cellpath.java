package com.example.FreeFlow;

import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 05.09.14.
 */

public class Cellpath {
    protected Paint m_paintPath  = new Paint();
    protected Path m_path = new Path();

    public Cellpath(int color){

        m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setColor(color);
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );
    }

    private ArrayList<Coordinate> m_coords = new ArrayList<Coordinate>();

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
}