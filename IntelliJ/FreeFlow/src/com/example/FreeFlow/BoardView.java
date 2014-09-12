package com.example.FreeFlow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yngvi on 1.9.2014.
 */
public class BoardView extends View {

    private final int NUM_CELLS = 3;

    private Paint m_paint = new Paint();
    private Paint m_paintGrid = new Paint();
    private ShapeDrawable m_shape = new ShapeDrawable( new OvalShape() );
    private Rect m_rect = new Rect();

    private int m_cellSize;
    private String m_board = "";
    private OnMoveEventHandler m_handler = null;



    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paintGrid.setStyle(Paint.Style.STROKE);
        m_paintGrid.setColor( Color.GRAY );
    }

    @Override
    public void onMeasure( int widthMS, int heightMS ) {
        super.onMeasure( widthMS, heightMS );
        int size = Math.min( getMeasuredWidth(), getMeasuredHeight() );
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld) {
        m_cellSize =  xNew / NUM_CELLS;
    }

    private int xToCol( int x ) {
        return x / m_cellSize;
    }

    private int yToRow( int y ) {
        return y / m_cellSize;
    }

    private int colToX( int col ){
        return col * m_cellSize;
    }

    private int rowToY( int row ){
        return row * m_cellSize;
    }

    public boolean setBoard( String board ) {
        if ( board.length() == NUM_CELLS * NUM_CELLS) {
            m_board = board;
            invalidate();
        }
        return false;
    }

    public char getBoard( int col, int row ) {
        return m_board.charAt(col + row * NUM_CELLS);
    }

    public void setMoveHandler( OnMoveEventHandler handler) {
        m_handler = handler;
    }


    @Override
    protected void onDraw( Canvas canvas ) {

        for ( int r=0; r<NUM_CELLS; ++r ) {
            for (int c = 0; c<NUM_CELLS; ++c) {
                int x = colToX( c );
                int y = rowToY( r );
                m_rect.set(x, y, x + m_cellSize, y + m_cellSize);
                canvas.drawRect( m_rect, m_paintGrid );
            }
        }

        for ( int r=0; r<NUM_CELLS; ++r ) {
            for (int c = 0; c<NUM_CELLS; ++c) {
                int x = colToX( c );
                int y = rowToY( r );
                m_rect.set(x, y, x + m_cellSize, y + m_cellSize);
                m_rect.inset( m_cellSize / 10, m_cellSize / 10 );
                m_shape.setBounds(m_rect);
                char ch = getBoard( c, r );
                if ( ch == 'x' ) {
                    m_shape.getPaint().setColor(Color.RED);
                    m_shape.draw(canvas);
                }
                else if ( ch == 'o' ) {
                    m_shape.getPaint().setColor(Color.BLUE);
                    m_shape.draw(canvas);
                }
            }
        }
    }


    @Override
    public boolean onTouchEvent( MotionEvent event) {

        int x = (int) event.getX();  //NOTE: getHistoryX()
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int c = xToCol(x);
            int r = yToRow(y);
            if( m_handler != null) {
                m_handler.onMove(c, r);
            }
        }

        return true;

    }



}