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

    private Paint paint;

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

        whitePawnImage = Bitmap.createScaledBitmap(whitePawnImage, width, width, false);
        whiteRookImage = Bitmap.createScaledBitmap(whiteRookImage, width, width, false);
        whiteKnightImage = Bitmap.createScaledBitmap(whiteKnightImage, width, width, false);
        whiteKingImage = Bitmap.createScaledBitmap(whiteKingImage, width, width, false);
        whiteQueenImage = Bitmap.createScaledBitmap(whiteQueenImage, width, width, false);
        whiteBishopImage = Bitmap.createScaledBitmap(whiteBishopImage, width, width, false);
        blackPawnImage = Bitmap.createScaledBitmap(blackPawnImage, width, width, false);
        blackRookImage = Bitmap.createScaledBitmap(blackRookImage, width, width, false);
        blackKnightImage = Bitmap.createScaledBitmap(blackKnightImage, width, width, false);
        blackKingImage = Bitmap.createScaledBitmap(blackKingImage, width, width, false);
        blackQueenImage = Bitmap.createScaledBitmap(blackQueenImage, width, width, false);
        blackBishopImage = Bitmap.createScaledBitmap(blackBishopImage, width, width, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(10,10,30,30, paint);
    }

    private void init(){
        width = 120;
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }
}
