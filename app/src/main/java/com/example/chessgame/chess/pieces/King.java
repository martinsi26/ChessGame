package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class King {
    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private ArrayList<Integer> xMovementAttack;
    private ArrayList<Integer> yMovementAttack;

    private int x;
    private int y;

    private Piece.ColorType colorInverse;

    public King(Piece piece, ChessState state, Piece.ColorType color) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
        xMovementAttack = new ArrayList<>();
        yMovementAttack = new ArrayList<>();
        kingMovement(state, color);
    }

    public void kingMovement(ChessState state, Piece.ColorType color) {
        if(x > 0) {
            if(state.getPiece(x - 1, y).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y);
            }
            xMovementAttack.add(x - 1);
            yMovementAttack.add(y);
        }
        if (x < 7) {
            if (state.getPiece(x + 1, y).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y);
            }
            xMovementAttack.add(x + 1);
            yMovementAttack.add(y);
        }
        if (y > 0) {
            if(state.getPiece(x, y - 1).getPieceColor() != color) {
                xMovement.add(x);
                yMovement.add(y - 1);
            }
            xMovementAttack.add(x);
            yMovementAttack.add(y - 1);
        }
        if (y < 7) {
            if (state.getPiece(x, y + 1).getPieceColor() != color) {
                xMovement.add(x);
                yMovement.add(y + 1);
            }
            xMovementAttack.add(x);
            yMovementAttack.add(y + 1);
        }
        if (x > 0 && y > 0) {
            if(state.getPiece(x - 1, y - 1).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y - 1);
            }
            xMovementAttack.add(x - 1);
            yMovementAttack.add(y - 1);
        }
        if (x > 0 && y < 7) {
            if(state.getPiece(x - 1, y + 1).getPieceColor() != color) {
                xMovement.add(x - 1);
                yMovement.add(y + 1);
            }
            xMovementAttack.add(x - 1);
            yMovementAttack.add(y + 1);
        }
        if (x < 7 && y > 0) {
            if(state.getPiece(x + 1, y - 1).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y - 1);
            }
            xMovementAttack.add(x + 1);
            yMovementAttack.add(y - 1);
        }
        if (x < 7 && y < 7) {
            if(state.getPiece(x + 1, y + 1).getPieceColor() != color) {
                xMovement.add(x + 1);
                yMovement.add(y + 1);
            }
            xMovementAttack.add(x + 1);
            yMovementAttack.add(y + 1);
        }

        if(state.getPiece(x, y).getPieceColor() == Piece.ColorType.WHITE){
            if(!state.getWhiteKingHasMoved() && !state.getWhiteRook1HasMoved()){
                xMovement.add();
                yMovement.add();
            }
        }
        else if(state.getPiece(row, col).getPieceColor() == Piece.ColorType.BLACK){
            if(state.getPiece(row, col).getPieceType() == Piece.PieceType.KING) {
                //WE NEED TO CHECK THAT THE SPACES ARE BLANK
                state.setBlackKingHasMoved(true);
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
