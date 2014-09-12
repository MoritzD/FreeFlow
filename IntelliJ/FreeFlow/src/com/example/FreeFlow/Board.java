package com.example.FreeFlow;

/**
 * Created by Sami on 05.09.14.
 */

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class Board extends View {

    private final int NUM_CELLS = 5;
    private int m_cellWidth;
    private int m_cellHeight;

    private Circle[][] m_circles;

    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();
   // private Paint m_paintPath  = new Paint();
   // private Path m_path = new Path();

    private Cellpath[] m_cellPath;

    private int xToCol( int x ) {
        return (x - getPaddingLeft()) / m_cellWidth;
    }

    private int yToRow( int y ) {
        return (y - getPaddingTop()) / m_cellHeight;
    }

    private int colToX( int col ) {
        return col * m_cellWidth + getPaddingLeft() ;
    }

    private int rowToY( int row ) {
        return row * m_cellHeight + getPaddingTop() ;
    }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);

        m_paintGrid.setStyle( Paint.Style.STROKE );
        m_paintGrid.setColor( Color.GRAY );

       /* m_paintPath.setStyle( Paint.Style.STROKE );
        m_paintPath.setColor(Color.GREEN);
        m_paintPath.setStrokeWidth(32);
        m_paintPath.setStrokeCap( Paint.Cap.ROUND );
        m_paintPath.setStrokeJoin( Paint.Join.ROUND );
        m_paintPath.setAntiAlias( true );
*/

    }

    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);
        setMeasuredDimension( size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom() );
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;
        Circle[][] m1_circles = {{new Circle(colToX(0),rowToY(0),Color.GREEN,m_cellWidth),new Circle(colToX(1),rowToY(4),Color.GREEN,m_cellWidth)},
                {new Circle(colToX(2),rowToY(0),Color.RED,m_cellWidth),new Circle(colToX(1),rowToY(3),Color.RED,m_cellWidth)},
                {new Circle(colToX(4),rowToY(0),Color.WHITE,m_cellWidth),new Circle(colToX(3),rowToY(3),Color.WHITE,m_cellWidth)},
                {new Circle(colToX(2),rowToY(1),Color.YELLOW,m_cellWidth),new Circle(colToX(2),rowToY(4),Color.YELLOW,m_cellWidth)},
                {new Circle(colToX(4),rowToY(1),Color.BLUE,m_cellWidth),new Circle(colToX(3),rowToY(4),Color.BLUE,m_cellWidth)}};
        m_circles=m1_circles;
        m_cellPath = new Cellpath[m_circles.length];
        for(int i = 0; i< m_cellPath.length;i++ ){
            m_cellPath[i] = new Cellpath(m_circles[i][0].m_color);
        }
    }

    @Override
    protected void onDraw( Canvas canvas ) {

        for(int i=0; i<5;i++){
            for (int e =0;e<2;e++){
                m_circles[i][e].draw(canvas);
            }
        }

        for ( int r=0; r<NUM_CELLS; ++r ) {
            for (int c = 0; c<NUM_CELLS; ++c) {
                int x = colToX( c );
                int y = rowToY( r );
                m_rect.set(x, y, x + m_cellWidth, y + m_cellHeight);
                canvas.drawRect( m_rect, m_paintGrid );
            }
        }
        m_cellPath[0].m_path.reset();
        if ( !m_cellPath[0].isEmpty() ) {

            List<Coordinate> colist = m_cellPath[0].getCoordinates();
            Coordinate co = colist.get( 0 );
            m_cellPath[0].m_path.moveTo( colToX(co.getCol()) + m_cellWidth / 2,
                    rowToY(co.getRow()) + m_cellHeight / 2 );

            for ( int i=1; i<colist.size(); ++i ) {

                co = colist.get(i);
                m_cellPath[0].m_path.lineTo( colToX(co.getCol()) + m_cellWidth / 2,
                        rowToY(co.getRow()) + m_cellHeight / 2 );

            }
        }
        canvas.drawPath( m_cellPath[0].m_path, m_cellPath[0].m_paintPath);
    }

    private boolean areNeighbours( int c1, int r1, int c2, int r2 ) {

        return Math.abs(c1-c2) + Math.abs(r1-r2) == 1;
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        int c = xToCol( x );
        int r = yToRow( y );

        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {
            m_cellPath[0].reset();
            m_cellPath[0].append( new Coordinate(c,r) );
        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {

            if ( !m_cellPath[0].isEmpty() ) {
                List<Coordinate> coordinateList = m_cellPath[0].getCoordinates();
                Coordinate last = coordinateList.get(coordinateList.size()-1);
                if ( areNeighbours(last.getCol(),last.getRow(), c, r)) {
                    m_cellPath[0].append(new Coordinate(c, r));
                    invalidate();

                }
            }
        }
        return true;
    }

    /*public void setColor(int color){
        m_paintPath.setColor(color);
        invalidate();


    }*/


}