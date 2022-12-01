package com.example.chessgame.chess.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

import com.example.chessgame.GameFramework.utilities.FlashSurfaceView;
import com.example.chessgame.R;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

public class WhiteCaptureSurfaceView extends FlashSurfaceView {

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

    private int width;
    private int height;

    private int xLoc;
    private int yLoc;

    private Paint paint;

    protected ChessState chessState;

    public WhiteCaptureSurfaceView(Context context) {
        super(context);
        init();
    }

    public WhiteCaptureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
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

        whitePawnImage = Bitmap.createScaledBitmap(whitePawnImage, width, height, false);
        whiteRookImage = Bitmap.createScaledBitmap(whiteRookImage, width, height, false);
        whiteKnightImage = Bitmap.createScaledBitmap(whiteKnightImage, width, height, false);
        whiteKingImage = Bitmap.createScaledBitmap(whiteKingImage, width, height, false);
        whiteQueenImage = Bitmap.createScaledBitmap(whiteQueenImage, width, height, false);
        whiteBishopImage = Bitmap.createScaledBitmap(whiteBishopImage, width, height, false);
        blackPawnImage = Bitmap.createScaledBitmap(blackPawnImage, width, height, false);
        blackRookImage = Bitmap.createScaledBitmap(blackRookImage, width, height, false);
        blackKnightImage = Bitmap.createScaledBitmap(blackKnightImage, width, height, false);
        blackKingImage = Bitmap.createScaledBitmap(blackKingImage, width, height, false);
        blackQueenImage = Bitmap.createScaledBitmap(blackQueenImage, width, height, false);
        blackBishopImage = Bitmap.createScaledBitmap(blackBishopImage, width, height, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(chessState == null) return;

        //canvas.drawRect(10,10,30,30, paint);


        xLoc = yLoc = 25;
        for (Piece p : chessState.getWhiteCapturedPieces()) {
//            Log.d("Testing", p.getPieceType().toString());

            if (p.getPieceColor() == Piece.ColorType.WHITE) {
                if (p.getPieceType() == Piece.PieceType.PAWN) {
                    canvas.drawBitmap(whitePawnImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.BISHOP) {
                    canvas.drawBitmap(whiteBishopImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.KNIGHT) {
                    canvas.drawBitmap(whiteKnightImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.ROOK) {
                    canvas.drawBitmap(whiteRookImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.QUEEN) {
                    canvas.drawBitmap(whiteQueenImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.KING) {
                    canvas.drawBitmap(whiteKingImage, xLoc, yLoc, paint);
                    increment();
                }
            } else if (p.getPieceColor() == Piece.ColorType.BLACK) {
                if (p.getPieceType() == Piece.PieceType.PAWN) {
                    canvas.drawBitmap(blackPawnImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.BISHOP) {
                    canvas.drawBitmap(blackBishopImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.KNIGHT) {
                    canvas.drawBitmap(blackKnightImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.ROOK) {
                    canvas.drawBitmap(blackRookImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.QUEEN) {
                    canvas.drawBitmap(blackQueenImage, xLoc, yLoc, paint);
                    increment();
                } else if (p.getPieceType() == Piece.PieceType.KING) {
                    canvas.drawBitmap(blackKingImage, xLoc, yLoc, paint);
                    increment();
                }
            }
        }

    }

    private void init(){
        width = height = 75;
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void setState(ChessState state) {
        chessState = state;
    }

    public void increment(){
        xLoc += width + 10;
    }

}
