package com.example.FreeFlow;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by moe on 12.09.14.
 */
public class Circle {
    public int x,y,m_cellSize;
    public int m_color;
    private Rect m_rect;
    private ShapeDrawable m_shape;
    protected Coordinate position;

    public Circle(Coordinate co, int color,int cellSize){

        this.x = co.getX();
        this.y = co.getY();
        position = co;
        m_color=color;
        m_cellSize=cellSize;
        m_shape = new ShapeDrawable( new OvalShape() );
        m_rect = new Rect();
    }

    public void draw (Canvas canvas){
        m_rect.set(x, y, x + m_cellSize, y + m_cellSize);
        m_rect.inset(m_cellSize / 10, m_cellSize / 10);
        m_shape.setBounds(m_rect);
        m_shape.getPaint().setColor(m_color);
        m_shape.draw(canvas);
    }


    public boolean isTouched(int t_x, int t_y){

        if( t_x == x && t_y == y){
            return true;
        }
        else return false;
    }

}
