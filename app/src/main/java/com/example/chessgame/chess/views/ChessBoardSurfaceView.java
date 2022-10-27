package com.example.chessgame.chess.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.example.chessgame.GameFramework.utilities.FlashSurfaceView;
import com.example.chessgame.R;
import com.example.chessgame.chess.infoMessage.ChessState;

public class ChessBoardSurfaceView extends FlashSurfaceView {

    //variables for creating board
    private float top = 40;
    private float left = 40;
    private float size = 115;
    private float bottom = top + size;
    private float right = left + size;

    //paint variables
    protected Paint imagePaint;
    private Paint colorSquare;
    private Paint highlightPaint;
    private Paint dotPaint;
    private Paint textPaint;

    protected ChessState state;

    //images for chess pieces
    protected Bitmap whitePawnImage;
    protected Bitmap whiteKnightImage;
    protected Bitmap whiteBishopImage;
    protected Bitmap whiteRookImage;
    protected Bitmap whiteKingImage;
    protected Bitmap whiteQueenImage;
    protected Bitmap blackPawnImage;
    protected Bitmap blackKnightImage;
    protected Bitmap blackBishopImage;
    protected Bitmap blackKingImage;
    protected Bitmap blackQueenImage;
    protected Bitmap blackRookImage;

    public ChessBoardSurfaceView(Context context) {
        super(context);
    }

    public ChessBoardSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        whitePawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.wp);
        whiteKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.wn);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.wb);
        whiteRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.wr);
        whiteKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.wk);
        whiteQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.wq);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.wb);
        blackPawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.bp);
        blackBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.bb);
        blackKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.bn);
        blackRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.br);
        blackKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.bk);
        blackQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.bq);
        blackBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.bb);
        whitePawnImage = Bitmap.createScaledBitmap(whitePawnImage,120,120,false);
        whiteRookImage = Bitmap.createScaledBitmap(whiteRookImage,120,120,false);
        whiteKnightImage = Bitmap.createScaledBitmap(whiteKnightImage,120,120,false);
        whiteKingImage = Bitmap.createScaledBitmap(whiteKingImage,120,120,false);
        whiteQueenImage = Bitmap.createScaledBitmap(whiteQueenImage,120,120,false);
        whiteBishopImage = Bitmap.createScaledBitmap(whiteBishopImage,120,120,false);
        blackPawnImage = Bitmap.createScaledBitmap(blackPawnImage,120,120,false);
        blackRookImage = Bitmap.createScaledBitmap(blackRookImage,120,120,false);
        blackKnightImage = Bitmap.createScaledBitmap(blackKnightImage,120,120,false);
        blackKingImage = Bitmap.createScaledBitmap(blackKingImage,120,120,false);
        blackQueenImage = Bitmap.createScaledBitmap(blackQueenImage,120,120,false);
        blackBishopImage = Bitmap.createScaledBitmap(blackBishopImage,120,120,false);

        colorSquare = new Paint();
        colorSquare.setColor(Color.WHITE);
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        highlightPaint = new Paint();
        highlightPaint.setColor(Color.YELLOW);
        dotPaint = new Paint();
        dotPaint.setColor(Color.LTGRAY);
        imagePaint = new Paint();
        imagePaint.setColor(Color.WHITE);

    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //this is board initialization
        for(int j = 0; j < 8; j++) {
            for(int i = 0; i < 8; i++) {

                //alternate colors
                if ((i % 2 == 0 && j % 2 != 0) || (j % 2 == 0 && i % 2 != 0)) {
                    colorSquare.setColor(Color.rgb(1, 50, 32));
                } else {
                    colorSquare.setColor(Color.WHITE);
                }

                //draw rectangle
                canvas.drawRect(left + (right - left) * i, top + (bottom - top) * j,
                        right + (right - left) * i, bottom + (bottom - top) * j, colorSquare);
            }
        }

        //draw all the pieces
        for(int i = 0; i < pieces.length; i++) {
            for(int j = 0; j < pieces[i].length; j++) {
                if(pieces[i][j] == 1) {
                    canvas.drawBitmap(whitePawnImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == 2) {
                    canvas.drawBitmap(whiteBishopImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == 3) {
                    canvas.drawBitmap(whiteKnightImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == 4) {
                    canvas.drawBitmap(whiteRookImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == 5) {
                    canvas.drawBitmap(whiteQueenImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == 6) {
                    canvas.drawBitmap(whiteKingImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                }
                if(pieces[i][j] == -1) {
                    canvas.drawBitmap(blackPawnImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == -2) {
                    canvas.drawBitmap(blackBishopImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == -3) {
                    canvas.drawBitmap(blackKnightImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == -4) {
                    canvas.drawBitmap(blackRookImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == -5) {
                    canvas.drawBitmap(blackQueenImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                } else if(pieces[i][j] == -6) {
                    canvas.drawBitmap(blackKingImage, 40 + (i * 115), 40 + (j * 115), imagePaint);
                }
            }
        }

    }
}
