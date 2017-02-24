package com.rishindrareddy.gameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by RishindraReddy on 2/24/2017.
 */

public class gameGridView extends View {

    private int ColumnCount =12;    //columns in the grid
    private int RowCount=12;        //rows in the grid
    private int cWidth, cHeight;    //for cell width and height
    private Paint bg = new Paint(Color.WHITE);  //color for background and the lines in the grid
    private Paint dead = new Paint(Color.GRAY);   //color of dead cell
    private Paint live = new Paint(Color.YELLOW);    //color for live cell
    private boolean[][] currentGen;     //2D array of cell values

    public gameGridView(Context context){
        this(context,null);
    }

    public gameGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        bg.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int width,int height,int prevW,int prevH){
        super.onSizeChanged(width, height, prevW, prevH);
        adjustGrid();
    }

    private void adjustGrid(){

        int w =getcWidth(); //cell width and height
        int h=getcHeight();

        w = w / getColumnCount();   //computing the width and height estimates of the grid
        h = h / getRowCount();

        setcWidth(w);   //setting the width and row values
        setcHeight(h);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);  //canvas background color as white.

        int cellWidth = getcWidth(); //cell height and Width
        int cellHeight = getcHeight();

        int width = getWidth(); //width and height of canvas
        int height = getHeight();

        int column = getColumnCount();  //columns and rows of the grid
        int row = getRowCount();

        for(int i=0;i < column;i++){
            for(int j=0;j < row;j++){
                if(currentGen[i][j]){   //if the cell is live then fill it with yellow
                    canvas.drawRect(i * width, j * height,(i + 1) * width, (j + 1) * height,live);
                }else{
                    canvas.drawRect(i * width, j * height,(i + 1) * width, (j + 1) * height,dead);
                }   //if the cell is dead fill it with gray
            }
        }

        //drawing lines between rows and columns to separate cells.

        for (int i = 1; i < column; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, bg);
        }

        for (int i = 1; i < row; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, bg);
        }

    }

        //for capturing the touch event of the user and making changes in the grid
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        int w =getcWidth();
        int h=getcHeight();

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            int c = (int)(motionEvent.getX() / w);  //to compute the column and row number o fthe cell that is touched.
            int r = (int)(motionEvent.getY() / h);

            currentGen[c][r] = ! currentGen[c][r];
        }
        return true;
    }





    public int getcHeight() {
        return cHeight;
    }

    public void setcHeight(int cHeight) {
        this.cHeight = cHeight;
    }

    public int getcWidth() {
        return cWidth;
    }

    public void setcWidth(int cWidth) {
        this.cWidth = cWidth;
    }

    public boolean[][] getCurrentGen() {
        return currentGen;
    }


    public int getColumnCount() {
        return ColumnCount;
    }

    public int getRowCount() {
        return RowCount;
    }

}
