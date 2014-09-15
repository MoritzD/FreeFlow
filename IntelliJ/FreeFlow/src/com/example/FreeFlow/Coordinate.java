package com.example.FreeFlow;

/**
 * Created by Sami on 05.09.14.
 */
public class Coordinate {

    private int m_col;
    private int m_row;

    Coordinate( int col, int row ) {
        m_col = col;
        m_row = row;
    }


    public int getX() {
        return m_col * Board.m_cellWidth + Board.m_paddingLeft;
    }

    public int getY() {
        return m_row * Board.m_cellHeight + Board.m_paddingTop;
    }


    public int getCol() {
        return m_col;
    }

    public int getRow() {
        return m_row;
    }

    @Override
    public boolean equals( Object other ) {
        if ( !(other instanceof Coordinate) ) {
            return false;
        }
        Coordinate otherCo = (Coordinate) other;
        return otherCo.getCol() == this.getCol()&& otherCo.getRow() == this.getRow();
    }
}