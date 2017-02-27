package com.rishindrareddy.conwaygol;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class gridActivity extends View {


    private int ColumnCount =12;    //columns in the grid
    private int RowCount=12;        //rows in the grid
    private int cWidth, cHeight;    //for cell width and height
    private Paint bg = new Paint();  //color for background and the lines in the grid
    private Paint dead = new Paint();   //color of dead cell
    private Paint live = new Paint();    //color for live cell
    private Paint line = new Paint();
    public boolean[][] currentGen;     //2D array of cell values of current generation.
    public boolean[][] futureGen;      //2D array of cell values of future generation

    public gridActivity(Context context) {
        super(context);
        Log.d("rishi", "constructor");
        adjustGrid();   //to adjust the cell width and height
        setWillNotDraw(false);
    }

    public gridActivity(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        bg.setStyle(Paint.Style.FILL_AND_STROKE);
        bg.setColor(Color.BLACK);
        line.setStyle(Paint.Style.FILL_AND_STROKE);
        line.setColor(Color.WHITE);
        line.setStrokeWidth(2);
        live.setStyle(Paint.Style.FILL_AND_STROKE);
        live.setColor(Color.YELLOW);
        dead.setStyle(Paint.Style.FILL_AND_STROKE);
        dead.setColor(Color.LTGRAY);
    }


    private void adjustGrid(){

        int w =getWidth(); //cell width and height
        int h=getHeight();

        w = w / 12;   //computing the width and height estimates of the grid
        h = h / 12;

        currentGen = new boolean[12][12];



        setcWidth(w);   //setting the width and row values
        setcHeight(h);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas){

        Log.d("rishi", "draw");

        canvas.drawColor(Color.BLACK);  //canvas background color as white.

        int width = getWidth();
        int height = getHeight();

        int cellWidth = getcWidth();
        int cellHeight = getcHeight();




        for(int i=0;i < 12;i++){
            for(int j=0;j < 12;j++){
                if(currentGen[i][j]){   //if the cell is live then fill it with yellow
                   canvas.drawRect(i * cellWidth, j * cellHeight,(i + 1) * cellWidth, (j + 1) * cellHeight,live);
                }else{
                    canvas.drawRect(i * cellWidth, j * cellHeight,(i + 1) * cellWidth, (j + 1) * cellHeight,dead);
                }   //if the cell is dead fill it with gray
            }
        }

        for (int i = 1; i < 12; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, line);
        }

        for (int i = 1; i < 12; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, line);
        }

        //drawing lines between rows and columns to separate cells.



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldWidth, int oldHeight) {
        super.onSizeChanged(w, h, oldWidth, oldHeight);
        adjustGrid();
    }


    //for capturing the touch event of the user and making changes in the grid
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        int w =getcWidth();
        int h=getcHeight();

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            int c = (int)(motionEvent.getX() / w);  //to compute the column and row number of the cell that is touched.
            int r = (int)(motionEvent.getY() / h);

            currentGen[c][r] = !currentGen[c][r];


        }
        invalidate();
        return true;

    }

    public int getcWidth() {
        return cWidth;
    }

    public int getcHeight() {
        return cHeight;
    }

    public void setcWidth(int W){
        this.cWidth=W;
    }

    public void setcHeight(int H){
        this.cHeight=H;
    }





    public void nextGen(){

        int neighbours;
        futureGen = new boolean[12][12];

        for(int i=0;i<12;i++){
            for(int j=0;j<12;j++){
                neighbours=checkNeighbours(currentGen,i,j);

                if(neighbours>3 || neighbours<2)
                    futureGen[i][j]=false;

                else if (neighbours == 3) {
                    futureGen[i][j] = true;
                }
                else if (currentGen[i][j]) {
                    if ((neighbours >= 2) && (neighbours <= 3)) {
                        futureGen[i][j] = true;
                    }
                }
            }
        }

        currentGen = futureGen;
        invalidate();
    }

    public int checkNeighbours(boolean[][] grid,int i,int j){

        int count=0;

        //our loop will check neighbour of the cell including the cell, so we subtract one less.
        if(grid[i][j])
            count=-1;

        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, 11); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, 11); y++) {
                if(grid[x][y])
                    count++;
            }
        }
        return count;
    }

    public void resetGame(){
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Warning !");
        alertDialog.setMessage("Please confirm !! Do you want to reset the game ?");

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CONFIRM",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        currentGen = new boolean[12][12];

                        invalidate();
                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();

    }
}
