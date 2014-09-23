package com.example.FreeFlow;

/**
 * Created by Sami on 05.09.14.
 */

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    private final int NUM_CELLS = 5;

    protected static int m_cellWidth;
    protected static int m_cellHeight;
    protected static int m_paddingLeft;
    protected static int m_paddingTop;

    public Puzzle mPuzzle = null;

    private int m_drawPath;

    //private Circle[][] m_circles;
    List<Circle[]> mCircles = new ArrayList<Circle[]>();


    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();

    //private Cellpath[] m_cellPath;
    List<Cellpath> mCellPath = new ArrayList<Cellpath>();
    private boolean onCircle = false;
    private boolean onPath = false;
    private boolean onDrawCircle = false;

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

        onSizeChanged(this.getWidth(),this.getHeight(),this.getWidth(),this.getHeight());

    }


    @Override
    protected void onMeasure( int widthMeasureSpec, int heightMeasureSpec ) {

        super.onMeasure( widthMeasureSpec, heightMeasureSpec );

        int width  = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        int size = Math.min(width, height);

        setMeasuredDimension( size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom() );

        m_paddingLeft = getPaddingLeft();
        m_paddingTop = getPaddingTop();
    }

    @Override
    protected void onSizeChanged( int xNew, int yNew, int xOld, int yOld ) {
        int sw = Math.max(1, (int) m_paintGrid.getStrokeWidth());
        m_cellWidth  = (xNew - getPaddingLeft() - getPaddingRight() - sw) / NUM_CELLS;
        m_cellHeight = (yNew - getPaddingTop() - getPaddingBottom() - sw) / NUM_CELLS;

        /*Circle[][] m1_circles = {{new Circle(new Coordinate(0,0),Color.GREEN,m_cellWidth),new Circle(new Coordinate(1,4),Color.GREEN,m_cellWidth)},
                {new Circle(new Coordinate(2,0),Color.RED,m_cellWidth),new Circle(new Coordinate(1,3),Color.RED,m_cellWidth)},
                {new Circle(new Coordinate(4,0),Color.WHITE,m_cellWidth),new Circle(new Coordinate(3,3),Color.WHITE,m_cellWidth)},
                {new Circle(new Coordinate(2,1),Color.YELLOW,m_cellWidth),new Circle(new Coordinate(2,4),Color.YELLOW,m_cellWidth)},
                {new Circle(new Coordinate(4,1),Color.BLUE,m_cellWidth),new Circle(new Coordinate(3,4),Color.BLUE,m_cellWidth)}};

        m_circles=m1_circles;
        m_cellPath = new Cellpath[m_circles.length];

        for(int i = 0; i< m_cellPath.length;i++ ){

            m_cellPath[i] = new Cellpath(m_circles[i][0].m_color, m_circles[i][0].position, m_circles[i][1].position);
        }*/
        //loadLevel(Global.getInstance().mChallenge.get(0).mPuzzle.get(1));
        loadLevel();
        //messured=true;
    }

    @Override
    protected void onDraw( Canvas canvas ) {


        for(Circle[] circ : mCircles){
            for (int e =0;e<2;e++){
                circ[e].draw(canvas);
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

        for (Cellpath aM_cellPath : mCellPath) {
            aM_cellPath.m_path.reset();
            if (!aM_cellPath.isEmpty()) {

                List<Coordinate> colist = aM_cellPath.getCoordinates();
                Coordinate co = colist.get(0);
                aM_cellPath.m_path.moveTo(colToX(co.getCol()) + m_cellWidth / 2,
                        rowToY(co.getRow()) + m_cellHeight / 2);

                for (int i = 1; i < colist.size(); ++i) {

                    co = colist.get(i);
                    aM_cellPath.m_path.lineTo(colToX(co.getCol()) + m_cellWidth / 2,
                            rowToY(co.getRow()) + m_cellHeight / 2);

                }
            }
            canvas.drawPath(aM_cellPath.m_path, aM_cellPath.m_paintPath);
        }
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

        boolean found = false;


        if ( c >= NUM_CELLS || r >= NUM_CELLS ) {
            return true;
        }

        if ( event.getAction() == MotionEvent.ACTION_DOWN ) {

            /*for (int i = 0; i < m_cellPath.length; i++){
                if(m_cellPath[i].isTouched(new Coordinate(xToCol(x),yToRow(y)))){
                    onPath=true;
                    m_drawPath = i;
                }
            }*/

            for (Cellpath cell: mCellPath){
                if(cell.isTouched(new Coordinate(xToCol(x),yToRow(y)))){
                    onPath=true;
                    m_drawPath = mCellPath.indexOf(cell);
                }
            }



           /* for( int i = 0; i < m_circles.length; i++){
                for( int e = 0; e <= 1; e++){
                    if (m_circles[i][e].isTouched(colToX(c), rowToY(r))) {
                        m_drawPath = i;
                        onCircle = true;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }*/
            for( Circle[] cir : mCircles){
                for( int e = 0; e <= 1; e++){
                    if (cir[e].isTouched(colToX(c), rowToY(r))) {
                        m_drawPath = mCircles.indexOf(cir);
                        onCircle = true;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            /*if(onCircle){
                m_cellPath[m_drawPath].reset();
                m_cellPath[m_drawPath].append( new Coordinate(c,r) );
            }
            else if(onPath){
                m_cellPath[m_drawPath].append( new Coordinate(c,r) );
            }*/
            if(onCircle){
                mCellPath.get(m_drawPath).reset();
                mCellPath.get(m_drawPath).append( new Coordinate(c,r) );
            }
            else if(onPath){
                mCellPath.get(m_drawPath).append( new Coordinate(c,r) );
            }

            invalidate();
        }
        else if ( event.getAction() == MotionEvent.ACTION_MOVE ) {

            for (Cellpath aM_cellPath : mCellPath) {
                if(!aM_cellPath.equals(mCellPath.get(m_drawPath))){
                    if(aM_cellPath.contains(new Coordinate(xToCol(x), yToRow(y)))){
                        aM_cellPath.cutPath(new Coordinate(xToCol(x), yToRow(y)));
                    }
                }
            }

            Coordinate currentcoord = new Coordinate(c, r);

            /*for(int i = 0; i < m_circles.length; i++){
                if(i!=m_drawPath){
                    if(m_circles[i][0].position.equals(currentcoord) || m_circles[i][1].position.equals(currentcoord) ){
                        onDrawCircle = false;
                        break;
                    }
                onDrawCircle = true;

                }
            }*/
            for(Circle[] cir : mCircles){
                if(mCircles.indexOf(cir)!=m_drawPath){
                    if(cir[0].position.equals(currentcoord) || cir[1].position.equals(currentcoord) ){
                        onDrawCircle = false;
                        break;
                    }
                onDrawCircle = true;

                }
            }



            /*if(onDrawCircle) {
                if (!m_cellPath[m_drawPath].isConnected()) {
                    if (!m_cellPath[m_drawPath].isEmpty()) {
                        List<Coordinate> coordinateList = m_cellPath[m_drawPath].getCoordinates();
                        Coordinate last = coordinateList.get(coordinateList.size() - 1);
                        if (areNeighbours(last.getCol(), last.getRow(), c, r)) {
                            m_cellPath[m_drawPath].append(new Coordinate(c, r));
                            invalidate();

                        }
                    }
                }
            }*/
            if(onDrawCircle) {
                if (!mCellPath.get(m_drawPath).isConnected()) {
                    if (!mCellPath.get(m_drawPath).isEmpty()) {
                        List<Coordinate> coordinateList = mCellPath.get(m_drawPath).getCoordinates();
                        Coordinate last = coordinateList.get(coordinateList.size() - 1);
                        if (areNeighbours(last.getCol(), last.getRow(), c, r)) {
                            mCellPath.get(m_drawPath).append(new Coordinate(c, r));
                            invalidate();

                        }
                    }
                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            onCircle = false;
            onPath = false;

        }
        return true;
    }
    public void loadLevel(){
        if(mPuzzle==null) return;

        int red=1,green=0,blue=0;
         String[] points=mPuzzle.getFlows().split(",");
        mCircles.clear();
        mCellPath.clear();
        for(String str : points){
            str=str.replace("(","");
            str=str.replace(")","");
            while(str.startsWith(" ")){
                str = str.substring(1);
            }
            Log.d("aktueller String beim parsen:" , str);
            String[] CircleSting = str.split(" ");
            Log.d("aktuelle nummer beim parsen0:" , CircleSting[0]);
            Log.d("aktuelle nummer beim parsen1:" , CircleSting[1]);
            Log.d("aktuelle nummer beim parsen2:" , CircleSting[2]);
            Log.d("aktuelle nummer beim parsen3:" , CircleSting[3]);
            //Log.d("aktuelle nummer beim parsen3:" , CircleSting[4]);
            Log.d("anzahl:",""+CircleSting.length);
            mCircles.add(new Circle[]{new Circle(new Coordinate(Integer.parseInt(CircleSting[0]), Integer.parseInt(CircleSting[1])), Color.rgb(255*red,255*green,255*blue) , m_cellWidth),
                    new Circle(new Coordinate(Integer.parseInt(CircleSting[2]), Integer.parseInt(CircleSting[3])), Color.rgb(255*red,255*green,255*blue), m_cellWidth)});
            if(red==1&&green==0&&blue==0) {
                red=0;
                green=1;
            }
            else if(red==0&&green==1&&blue==0){
                green=0;
                blue=1;
            }
            else if(red==0&&green==0&&blue==1){
                red=1;
                green=1;
                blue=0;
            }
            else if(red==1&&green==1&&blue==0){
                red=1;
                green=0;
                blue=1;
            }
            else if(red==1&&green==0&&blue==1){
                red=0;
                green=1;
                blue=1;
            }
            else if(red==0&&green==1&&blue==1){
                red=1;
                green=1;
                blue=1;
            }
            else if(red==1&&green==1&&blue==1){
                red=0;
                green=0;
                blue=0;
            }
            else if(red==0&&green==0&&blue==0){
                red=1;
                green=0;
                blue=0;
            }


        }
        for(Circle[] cir : mCircles){
            mCellPath.add( new Cellpath(cir[0].m_color, cir[0].position, cir[1].position));
        }
        invalidate();
    }
    public void setLevel(Puzzle puz){
        mPuzzle=puz;
        invalidate();
    }


}