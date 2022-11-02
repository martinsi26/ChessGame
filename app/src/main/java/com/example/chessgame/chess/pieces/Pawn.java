package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class Pawn {

    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private int x;
    private int y;

    public Pawn(Piece piece) {
        x = piece.getX();
        y = piece.getY();
    }

    public void pawnMovement(ChessState state, Piece.ColorType color) {
        // swap the color for the purpose of this method later
        if (color == Piece.ColorType.WHITE) {
            color = Piece.ColorType.BLACK;
        } else if (color == Piece.ColorType.BLACK) {
            color = Piece.ColorType.WHITE;
        }
        if (y == 6) {
            if (state.getPiece(x, y - 1).getPieceColor() == Piece.ColorType.EMPTY) {
                xMovement.add(x);
                yMovement.add(y - 1);
            }
            if (state.getPiece(x, y - 2).getPieceColor() == Piece.ColorType.EMPTY) {
                xMovement.add(x);
                yMovement.add(y - 2);
            }
        } else if (y > 0) {
            if (state.getPiece(x, y - 1).getPieceColor() == Piece.ColorType.EMPTY) {
                xMovement.add(x);
                yMovement.add(y - 1);
            }
        }
        if (x > 0 && y > 0) {
            if (state.getPiece(x - 1, y - 1).getPieceColor() == color) {
                xMovement.add(x - 1);
                yMovement.add(y - 1);
            }
        }
        if (x < 7 && y > 0) {
            if (state.getPiece(x + 1, y - 1).getPieceColor() == color) {
                xMovement.add(x + 1);
                yMovement.add(y - 1);
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
