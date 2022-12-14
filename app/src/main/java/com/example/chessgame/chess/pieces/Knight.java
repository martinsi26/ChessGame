package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class Knight {

    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private ArrayList<Integer> xMovementAttack;
    private ArrayList<Integer> yMovementAttack;

    private int x;
    private int y;

    public Knight(Piece piece, ChessState state, Piece.ColorType color) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
        xMovementAttack = new ArrayList<>();
        yMovementAttack = new ArrayList<>();
        knightMovement(state, color);
    }

    public void knightMovement(ChessState state, Piece.ColorType color) {
        if (x > 1 && y > 0) {
            if (state.getPiece(x - 2, y - 1).getPieceColor() != color) {
                xMovement.add(x - 2);
                yMovement.add(y - 1);
            }
            xMovementAttack.add(x - 2);
            yMovementAttack.add(y - 1);
        }
        if (x > 0 && y > 1) {
            if (state.getPiece(x - 1, y - 2).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y - 2);
            }
            xMovementAttack.add(x - 1);
            yMovementAttack.add(y - 2);
        }
        if (x < 6 && y < 7) {
            if (state.getPiece(x + 2, y + 1).getPieceColor() != color) {
                xMovement.add(x + 2);
                yMovement.add(y + 1);
            }
            xMovementAttack.add(x + 2);
            yMovementAttack.add(y + 1);
        }
        if (x < 7 && y < 6) {
            if (state.getPiece(x + 1, y + 2).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y + 2);
            }
            xMovementAttack.add(x + 1);
            yMovementAttack.add(y + 2);
        }
        if (x > 1 && y < 7) {
            if (state.getPiece(x - 2, y + 1).getPieceColor() != color) {
                xMovement.add(x - 2);
                yMovement.add(y + 1);
            }
            xMovementAttack.add(x - 2);
            yMovementAttack.add(y + 1);
        }
        if (x > 0 && y < 6) {
            if (state.getPiece(x - 1, y + 2).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y + 2);
            }
            xMovementAttack.add(x - 1);
            yMovementAttack.add(y + 2);
        }
        if (x < 6 && y > 0) {
            if (state.getPiece(x + 2, y - 1).getPieceColor() != color) {
                xMovement.add(x + 2);
                yMovement.add(y - 1);
            }
            xMovementAttack.add(x + 2);
            yMovementAttack.add(y - 1);
        }
        if (x < 7 && y > 1) {
            if (state.getPiece(x + 1, y - 2).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y - 2);
            }
            xMovementAttack.add(x + 1);
            yMovementAttack.add(y - 2);
        }
    }

    public ArrayList<Integer> getX() {
        return xMovement;
    }

    public ArrayList<Integer> getY() {
        return yMovement;
    }

    public ArrayList<Integer> getXAttack() {
        return xMovementAttack;
    }

    public ArrayList<Integer> getYAttack() {
        return yMovementAttack;
    }
}
