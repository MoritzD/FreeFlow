package com.example.FreeFlow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 05.09.14.
 */

public class Cellpath {

    private ArrayList<Coordinate> m_path = new ArrayList<Coordinate>();

    public void append( Coordinate co ) {
        int idx = m_path.indexOf(  co );
        if ( idx >= 0 ) {
            for ( int i=m_path.size()-1; i > idx; --i ) {
                m_path.remove(i);
            }
        }
        else {
            m_path.add(co);
        }
    }

    public List<Coordinate> getCoordinates() {
        return m_path;
    }

    public void reset() {
        m_path.clear();
    }

    public boolean isEmpty() {
        return m_path.isEmpty();
    }
}