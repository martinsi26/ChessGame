package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class King {
    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private int x;
    private int y;

    private Piece.ColorType colorInverse;

    public King(Piece piece, ChessState state, Piece.ColorType color) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
        kingMovement(state, color);
    }

    public void kingMovement(ChessState state, Piece.ColorType color) {
        if(x > 0) {
            if(state.getPiece(x - 1, y).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y);
            }
        }
        if (x < 7) {
            if (state.getPiece(x + 1, y).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y);
            }
        }
        if (y > 0) {
            if(state.getPiece(x, y - 1).getPieceColor() != color) {
                xMovement.add(x);
                yMovement.add(y - 1);
            }
        }
        if (y < 7) {
            if (state.getPiece(x, y + 1).getPieceColor() != color) {
                xMovement.add(x);
                yMovement.add(y + 1);
            }
        }
        if (x > 0 && y > 0) {
            if(state.getPiece(x - 1, y - 1).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y - 1);
            }
        }
        if (x > 0 && y < 7) {
            if(state.getPiece(x - 1, y + 1).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y + 1);
            }
        }
        if (x < 7 && y > 0) {
            if(state.getPiece(x + 1, y - 1).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y - 1);
            }
        }
        if (x < 7 && y < 7) {
            if(state.getPiece(x + 1, y + 1).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y + 1);
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