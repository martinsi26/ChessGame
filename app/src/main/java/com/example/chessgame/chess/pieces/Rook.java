package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class Rook {
    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private ArrayList<Integer> xMovementAttack;
    private ArrayList<Integer> yMovementAttack;

    private int x;
    private int y;

    private Piece.ColorType colorInverse;

    public Rook(Piece piece, ChessState state, Piece.ColorType color) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
        xMovementAttack = new ArrayList<>();
        yMovementAttack = new ArrayList<>();
        if(color == Piece.ColorType.WHITE) {
            colorInverse = Piece.ColorType.BLACK;
        } else if (color == Piece.ColorType.BLACK) {
            colorInverse = Piece.ColorType.WHITE;
        }
        rookMovement(state, color);
    }

    public void rookMovement(ChessState state, Piece.ColorType color) {
        boolean stopLeft = false;
        boolean stopRight = false;
        boolean stopUp = false;
        boolean stopDown = false;

        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                if (state.getPiece(x - i, y).getPieceColor() == colorInverse && !stopLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y);
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y);
                    stopLeft = true;
                }
                if (state.getPiece(x - i, y).getPieceColor() == color) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y);
                    stopLeft = true;
                }
                if (!stopLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y);
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y);
                }
            }
            if (y - i >= 0) {
                if (state.getPiece(x, y - i).getPieceColor() == colorInverse && !stopUp) {
                    xMovement.add(x);
                    yMovement.add(y - i);
                    xMovementAttack.add(x);
                    yMovementAttack.add(y - i);
                    stopUp = true;
                }
                if (state.getPiece(x, y - i).getPieceColor() == color) {
                    xMovementAttack.add(x);
                    yMovementAttack.add(y - i);
                    stopUp = true;
                }
                if (!stopUp) {
                    xMovement.add(x);
                    yMovement.add(y - i);
                    xMovementAttack.add(x);
                    yMovementAttack.add(y - i);
                }
            }
            if (y + i < 8) {
                if (state.getPiece(x, y + i).getPieceColor() == colorInverse && !stopDown) {
                    xMovement.add(x);
                    yMovement.add(y + i);
                    xMovementAttack.add(x);
                    yMovementAttack.add(y + i);
                    stopDown = true;
                }
                if (state.getPiece(x, y + i).getPieceColor() == color) {
                    xMovementAttack.add(x);
                    yMovementAttack.add(y + i);
                    stopDown = true;
                }
                if (!stopDown) {
                    xMovement.add(x);
                    yMovement.add(y + i);
                    xMovementAttack.add(x);
                    yMovementAttack.add(y + i);
                }
            }
            if (x + i < 8) {
                if (state.getPiece(x + i, y).getPieceColor() == colorInverse && !stopRight) {
                    xMovementAttack.add(x);
                    yMovementAttack.add(y + i);
                    xMovement.add(x + i);
                    yMovement.add(y);
                    stopRight = true;
                }
                if (state.getPiece(x + i, y).getPieceColor() == color) {
                    xMovementAttack.add(x);
                    yMovementAttack.add(y + i);
                    stopRight = true;
                }
                if (!stopRight) {
                    xMovement.add(x + i);
                    yMovement.add(y);
                    xMovementAttack.add(x);
                    yMovementAttack.add(y + i);
                }
            }
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
