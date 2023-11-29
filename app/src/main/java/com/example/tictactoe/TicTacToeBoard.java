package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Printer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


@SuppressLint("ViewConstructor")
public class TicTacToeBoard extends View {

    private final int boardColor;
    final int XColor;
    final int OColor;
    final int winningLineColor;
    private boolean winningLine = false;
    private final Paint paint = new Paint();
    private int cellSize = getWidth()/3;

    private  final GameLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        Log.d("game page started" ," S T A R T");

//        this.game = game; // Constructor of GameLogic class

        game = new GameLogic();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0,0);

        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor,0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor,0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor,0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor,0);
            Log.d("Try executed ", " T R Y");

        }finally {
            Log.d("Finally Called ", "F I N  a  l l y");
            a.recycle();
        }


    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width,height);

        int dimension = Math.min(getMeasuredWidth(),getMeasuredHeight());
        cellSize = dimension/3;
        Log.d("onMeasure Called ", "dimension and cellsize" + dimension + cellSize);
        setMeasuredDimension(dimension,dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        Log.d("On Draw is called"," it will draw things");
        drawGameBoard(canvas);

        drawMarker(canvas);

        if(winningLine){
            paint.setColor(winningLineColor);
            drawWinningLine(canvas);
        }

//        drawX(canvas,2,1);
//        drawO(canvas,1,1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x =event.getX();
        float y= event.getY();

        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN){
            int row =  (int)  Math.ceil(y/cellSize);
            int col = (int) Math.ceil(x/cellSize);

            if(!winningLine) { // if there is no winning line means no one wins and game cont.

               if (game.updateGameBoard(row, col)) {
                    invalidate();

                    if(game.winnerCheck()){
                        winningLine = true;
                        invalidate();
                    }

                    //updating the players turn
                    if (game.getPlayer() % 2 == 0) {
                        game.setPlayer(game.getPlayer() - 1);
                    } else {
                        game.setPlayer(game.getPlayer() + 1);
                    }

                }
            }

            invalidate();  //redraw our game board  aumatically calling our onDraw method again.

            return true;
        }
        return false;
    }
    private void drawGameBoard(Canvas canvas) {

        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        Log.d("draw Game Board is being called ","d r a w");
        for (int c=1; c<3; c++){
            canvas.drawLine(cellSize*c,0,cellSize*c,canvas.getWidth(),paint);
        }
        for(int r=1; r<3; r++){
            canvas.drawLine(0,cellSize*r,canvas.getWidth(),cellSize*r,paint);
        }
    }



    private void drawMarker(Canvas canvas){

        for(int r=0;r<3;r++){
            for(int c=0;c<3;c++){
                if(game.getGameBoard()[r][c]!=0){

                    if(game.getGameBoard()[r][c] == 1){
                        drawX(canvas, r, c);
                    }
                    else {
                        drawO(canvas, r, c);
                    }
                }
            }
        }
    }

    private void drawX(Canvas canvas, int row, int col){
        paint.setColor(XColor);

        //to draw straight line on the game board
        // (col+1)*cellsize these formula's will be used but it'll going to capture the whole cell
        // If we want to reduse it then we use reduction value like cellsize * 0.2 and used float for type conversion

        canvas.drawLine((float) ((col+1)*cellSize - cellSize* 0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float) (col*cellSize + cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);

        canvas.drawLine((float)(col*cellSize + cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)((col+1)*cellSize - cellSize*0.2),
                        (float)((row+1)*cellSize - cellSize*0.2),
                        paint);
    }

    private void drawO(Canvas canvas, int row, int col){
        paint.setColor(OColor);

        canvas.drawOval((float)(col *cellSize + cellSize*0.2),
                        (float)(row*cellSize + cellSize*0.2),
                        (float)((col*cellSize + cellSize) - cellSize *0.2),
                        (float)((row*cellSize + cellSize) - cellSize *0.2),
                        paint);
    }

    private void drawHorizontalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col,row*cellSize+ cellSize/2,
                cellSize*3, row*cellSize + cellSize/2,
                paint);
    }
    private void drawVerticalLine(Canvas canvas, int row, int col){
        canvas.drawLine(col*cellSize + cellSize/2, row,
                col*cellSize + cellSize/2, cellSize*3, paint);
    }
    private void drawDiagonalLinePos(Canvas canvas){
        canvas.drawLine(0, cellSize*3, cellSize*3, 0, paint);
    }
    private void drawDiagonalLineNeg(Canvas canvas){
        canvas.drawLine(0,0, cellSize*3, cellSize*3, paint);
    }

    private void drawWinningLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];

        switch(game.getWinType()[2]){
            case 1 :
                drawHorizontalLine(canvas, row,col);
                break;
            case 2 :
                drawVerticalLine(canvas,row,col);
                break;
            case 3:
                drawDiagonalLineNeg(canvas);
                break;
            case 4:
                drawDiagonalLinePos(canvas);
                break;
        }
    }
    public void setUpGame(Button playAgain, Button home, TextView playerDisplay, String[] name ){
        game.setPlayAgainBTN(playAgain);
        game.setHomeBTN(home);
        game.setPlayerTurn(playerDisplay);
        game.setPlayerNames(name);
    }

    public void resetGame(){
        game.resetGame();

        winningLine = false;
    }
}
