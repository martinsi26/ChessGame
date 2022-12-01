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
import com.example.chessgame.chess.infoMessage.Piece;

public class BlackCaptureSurfaceView extends FlashSurfaceView {
    protected Bitmap whitePawnImage;
    protected Bitmap whiteKnightImage;
    protected Bitmap whiteBishopImage;
    protected Bitmap whiteKingImage;
    protected Bitmap whiteQueenImage;
    protected Bitmap whiteRookImage;

    private int width;
    private int height;

    private int xLoc;
    private int yLoc;

    private Paint paint;

    protected ChessState chessState;

    public BlackCaptureSurfaceView(Context context) {
        super(context);
        init();
    }

    public BlackCaptureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        whitePawnImage = BitmapFactory.decodeResource(getResources(), R.drawable.wp);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.wb);
        whiteKnightImage = BitmapFactory.decodeResource(getResources(), R.drawable.wn);
        whiteRookImage = BitmapFactory.decodeResource(getResources(), R.drawable.wr);
        whiteKingImage = BitmapFactory.decodeResource(getResources(), R.drawable.wk);
        whiteQueenImage = BitmapFactory.decodeResource(getResources(), R.drawable.wq);
        whiteBishopImage = BitmapFactory.decodeResource(getResources(), R.drawable.wb);

        whitePawnImage = Bitmap.createScaledBitmap(whitePawnImage, width, height, false);
        whiteRookImage = Bitmap.createScaledBitmap(whiteRookImage, width, height, false);
        whiteKnightImage = Bitmap.createScaledBitmap(whiteKnightImage, width, height, false);
        whiteKingImage = Bitmap.createScaledBitmap(whiteKingImage, width, height, false);
        whiteQueenImage = Bitmap.createScaledBitmap(whiteQueenImage, width, height, false);
        whiteBishopImage = Bitmap.createScaledBitmap(whiteBishopImage, width, height, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(chessState == null) return;

        //canvas.drawRect(10,10,30,30, paint);


        xLoc = yLoc = 25;
        for (Piece p : chessState.getBlackCapturedPieces()) {
//            Log.d("Testing", p.getPieceType().toString());
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
