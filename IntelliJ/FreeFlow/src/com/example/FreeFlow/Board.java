package com.example.FreeFlow;

/**
 * Created by Sami on 05.09.14.
 */

import android.content.Context;
import android.graphics.*;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Board extends View {

    private int NUM_CELLS = 3;

    protected static int m_cellWidth;
    protected static int m_cellHeight;
    protected static int m_paddingLeft;
    protected static int m_paddingTop;

    public Puzzle mPuzzle = null;

    private int prevPath;

    public int pathsConnected;
    public int moves;

    TextView flowsConnected;
    TextView movesMade;
    TextView best;

    private boolean firstPath = true;
    private int m_drawPath;

    private boolean vibrateconnedt = true, GlobalVibrate = true, GlobalSound = true, vibsound = false, vibsoundcut = false;

    List<Circle[]> mCircles = new ArrayList<Circle[]>();


    private Rect m_rect = new Rect();
    private Paint m_paintGrid  = new Paint();

    List<Cellpath> mCellPath = new ArrayList<Cellpath>();
    private boolean onCircle = false;
    private boolean onPath = false;
    private boolean onDrawCircle = false;
    private Vibrator v;
    public MediaPlayer mpblop,mpsword;


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

        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mpblop = MediaPlayer.create(context, R.raw.blop);
        mpsword = MediaPlayer.create(context, R.raw.sword_strike);

        //bla

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

        loadLevel();
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

            for (Cellpath cell: mCellPath){
                if(cell.isTouched(new Coordinate(xToCol(x),yToRow(y)))){
                    onPath=true;
                    m_drawPath = mCellPath.indexOf(cell);
                    if(firstPath) {
                        prevPath = m_drawPath;
                        firstPath = false;
                    }
                }
            }

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
                        vibsoundcut=true;

                    }
                }
            }

            Coordinate currentcoord = new Coordinate(c, r);


            for(Circle[] cir : mCircles){
                if(mCircles.indexOf(cir)!=m_drawPath){
                    if(cir[0].position.equals(currentcoord) || cir[1].position.equals(currentcoord) ){
                        onDrawCircle = false;
                        break;
                    }
                onDrawCircle = true;

                }
            }


            if(onDrawCircle) {
                if (!mCellPath.get(m_drawPath).isConnected()) {
                    if (!mCellPath.get(m_drawPath).isEmpty()) {
                        List<Coordinate> coordinateList = mCellPath.get(m_drawPath).getCoordinates();
                        Coordinate last = coordinateList.get(coordinateList.size() - 1);
                        if (areNeighbours(last.getCol(), last.getRow(), c, r)) {
                            mCellPath.get(m_drawPath).append(new Coordinate(c, r));
                            invalidate();
                            vibrateconnedt=true;

                            vibsound=false;

                        }
                    }
                }
                else{

                       vibsound=true;


                }
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){

            pathsConnected = 0;
            for (Cellpath aMCellPath : mCellPath) {
                aMCellPath.isConnected();
                if (aMCellPath.isConnected) {
                    pathsConnected = pathsConnected + 1;
                }
            }

            if(prevPath != m_drawPath){
                moves = moves + 1;
                prevPath = m_drawPath;
            }

            movesMade.setText("Moves: "+ moves);
            flowsConnected.setText("Flows: " + pathsConnected + "/" + mCellPath.size());


            onCircle = false;
            onPath = false;
            if(vibsound) {
                if (GlobalVibrate)
                    v.vibrate(50);
                if (GlobalSound) {
                    mpblop.seekTo(0);
                    mpblop.start();
                }
            }
           else if(vibsoundcut){
                if (GlobalVibrate)
                    v.vibrate(50);
                if (GlobalSound) {
                    mpsword.seekTo(0);
                    mpsword.start();
                }
            }
            vibsound = vibsoundcut = false;

        }
        return true;
    }
    public void loadLevel(){
        if(mPuzzle==null) return;
        int pathsConnected = 0;

        pathsConnected = 0;

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
            String[] CircleSting = str.split(" ");

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


        movesMade.setText("Moves: " + 0);
        flowsConnected.setText("Flows: " + pathsConnected + "/" + mCellPath.size());
        best.setText("Best: " + 0);

       if(GlobalVibrate)
            v.vibrate(50);
       if(GlobalSound)
            mpblop.start();
        invalidate();
    }
    public void setLevel(Puzzle puz){
        mPuzzle=puz;
        if(mPuzzle!=null)
            NUM_CELLS = Integer.parseInt(mPuzzle.getSize());
        invalidate();
    }


    public void setTextFields(TextView flows, TextView moves, TextView bestMoves){

        flowsConnected = flows;
        movesMade = moves;
        best = bestMoves;


    }


}