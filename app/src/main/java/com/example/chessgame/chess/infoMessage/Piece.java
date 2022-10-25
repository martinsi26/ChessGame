package com.example.chessgame.chess.infoMessage;

import android.graphics.Color;

public class Piece {


    // An enum for the different types of pieces
    public enum PieceType {
        PAWN, BISHOP, ROOK, KNIGHT, KING, QUEEN, EMPTY
    }

    // An enum for the color of the pieces
    public enum ColorType {
        BLACK, WHITE, EMPTY
    }

    private PieceType pieceType;
    private ColorType pieceColor;
    private int x;
    private int y;

    public Piece(PieceType pieceType, ColorType pieceColor,int x, int y) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.x = x;
        this.y = y;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public ColorType getPieceColor() {
        return pieceColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    @Override
    public String toString() {
        if(pieceType == PieceType.PAWN) {
            return "P\t";
        }else if(pieceType == PieceType.BISHOP) {
            return "B\t";
        }else if (pieceType == PieceType.KNIGHT){
            return "N\t";
        }else if (pieceType == PieceType.ROOK) {
            return "R\t";
        }else if(pieceType == PieceType.KING){
            return "K\t";
        }else if (pieceType == PieceType.QUEEN) {
            return "Q\t";
        }
        return "E\t";

    }
}

