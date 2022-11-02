package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class Knight {

    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private int x;
    private int y;

    public Knight(Piece piece) {
        x = piece.getX();
        y = piece.getY();
    }

    public void knightMovement(ChessState state, Piece.ColorType color) {
        if (x > 1 && y > 0) {
            if (state.getPiece(x - 2, y - 1).getPieceColor() != color) {
                xMovement.add(x - 2);
                yMovement.add(y - 1);
            }
        }
        if (x > 0 && y > 1) {
            if (state.getPiece(x - 1, y - 2).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y - 2);
            }
        }
        if (x < 6 && y < 7) {
            if (state.getPiece(x + 2, y + 1).getPieceColor() != color) {
                xMovement.add(x + 2);
                yMovement.add(y + 1);
            }
        }
        if (x < 7 && y < 6) {
            if (state.getPiece(x + 1, y + 2).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y + 2);
            }
        }
        if (x > 1 && y < 7) {
            if (state.getPiece(x - 2, y + 1).getPieceColor() != color) {
                xMovement.add(x - 2);
                yMovement.add(y + 1);
            }
        }
        if (x > 0 && y < 6) {
            if (state.getPiece(x - 1, y + 2).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y + 2);
            }
        }
        if (x < 6 && y > 0) {
            if (state.getPiece(x + 2, y - 1).getPieceColor() != color) {
                xMovement.add(x + 2);
                yMovement.add(y - 1);
            }
        }
        if (x < 7 && y > 1) {
            if (state.getPiece(x + 1, y - 2).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y - 2);
            }
        }
    }

    public ArrayList<Integer> getX() {
        return xMovement;
    }

    public ArrayList<Integer> getY() {
        return yMovement;
    }
}
