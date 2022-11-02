package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class Rook {
    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private int x;
    private int y;

    public Rook(Piece piece) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
    }

    public void rookMovement(ChessState state, Piece.ColorType color) {
        boolean stopLeft = false;
        boolean stopRight = false;
        boolean stopUp = false;
        boolean stopDown = false;

        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                if (state.getPiece(x - i, y).getPieceColor() != color && !stopLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y);
                    stopLeft = true;
                }
                if (state.getPiece(x - i, y).getPieceColor() == color) {
                    stopLeft = true;
                }
                if (!stopLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y);
                }
            }
            if (y - i >= 0) {
                if (state.getPiece(x, y - i).getPieceColor() != color && !stopUp) {
                    xMovement.add(x);
                    yMovement.add(y - i);
                    stopUp = true;
                }
                if (state.getPiece(x, y - i).getPieceColor() == color) {
                    stopUp = true;
                }
                if (!stopUp) {
                    xMovement.add(x);
                    yMovement.add(y - i);
                }
            }
            if (y + i < 8) {
                if (state.getPiece(x, y + i).getPieceColor() != color && !stopDown) {
                    xMovement.add(x);
                    yMovement.add(y + i);
                    stopDown = true;
                }
                if (state.getPiece(x, y + i).getPieceColor() == color) {
                    stopDown = true;
                }
                if (!stopDown) {
                    xMovement.add(x);
                    yMovement.add(y + i);
                }
            }
            if (x + i < 8) {
                if (state.getPiece(x + i, y).getPieceColor() != color && !stopRight) {
                    xMovement.add(x + i);
                    yMovement.add(y);
                    stopRight = true;
                }
                if (state.getPiece(x + i, y).getPieceColor() == color) {
                    stopRight = true;
                }
                if (!stopRight) {
                    xMovement.add(x + i);
                    yMovement.add(y);
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
}
