package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.sql.Array;
import java.util.ArrayList;

public class Bishop {
    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private ArrayList<Integer> xMovementAttack;
    private ArrayList<Integer> yMovementAttack;

    private ArrayList<Integer> xAttackThrough;
    private ArrayList<Integer> yAttackThrough;

    private int x;
    private int y;

    private Piece.ColorType colorInverse;

    public Bishop(Piece piece, ChessState state, Piece.ColorType color) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
        xMovementAttack = new ArrayList<>();
        yMovementAttack = new ArrayList<>();
        xAttackThrough = new ArrayList<>();
        yAttackThrough = new ArrayList<>();
        if(color == Piece.ColorType.WHITE) {
            colorInverse = Piece.ColorType.BLACK;
        } else if (color == Piece.ColorType.BLACK) {
            colorInverse = Piece.ColorType.WHITE;
        }
        bishopMovement(state, color);
    }

    public void bishopMovement(ChessState state, Piece.ColorType color) {
        boolean stopUpLeft = false;
        boolean stopUpRight = false;
        boolean stopDownLeft = false;
        boolean stopDownRight = false;
        boolean stop = false;

        for (int i = 1; i < 8; i++) {
            if (y - i >= 0 && x - i >= 0) {
                if (state.getPiece(x - i, y - i).getPieceColor() == colorInverse && !stopUpLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y - i);
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y - i);
                    stopUpLeft = true;
                }
                if (state.getPiece(x - i, y - i).getPieceColor() == colorInverse && !stopUpLeft) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y - i);
                }
                if (state.getPiece(x - i, y - i).getPieceColor() == color) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y - i);
                    stop = true;
                    stopUpLeft = true;
                }
                if (state.getPiece(x - i, y - i).getPieceColor() != color && !stop) {
                    xAttackThrough.add(x - i);
                    yAttackThrough.add(y - i);
                }
                if (!stopUpLeft) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y - i);
                    xMovement.add(x - i);
                    yMovement.add(y - i);
                }
            }
            if (y - i >= 0 && x + i < 8) {
                if (state.getPiece(x + i, y - i).getPieceColor() == colorInverse && !stopUpRight) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y - i);
                    xMovement.add(x + i);
                    yMovement.add(y - i);
                    stopUpRight = true;
                }
                if (state.getPiece(x + i, y - i).getPieceColor() == colorInverse && !stopUpRight) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y - i);
                }
                if (state.getPiece(x + i, y - i).getPieceColor() == color) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y - i);
                    stopUpRight = true;
                    stop = true;
                }
                if (state.getPiece(x + i, y - i).getPieceColor() != color && !stop) {
                    xAttackThrough.add(x + i);
                    yAttackThrough.add(y - i);
                }
                if (!stopUpRight) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y - i);
                    xMovement.add(x + i);
                    yMovement.add(y - i);
                }
            }
            if (y + i < 8 && x - i >= 0) {
                if (state.getPiece(x - i, y + i).getPieceColor() == colorInverse && !stopDownLeft) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y + i);
                    xMovement.add(x - i);
                    yMovement.add(y + i);
                    stopDownLeft = true;
                }
                if (state.getPiece(x - i, y + i).getPieceColor() == colorInverse && !stopDownLeft) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y + i);
                }
                if (state.getPiece(x - i, y + i).getPieceColor() == color) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y + i);
                    stopDownLeft = true;
                    stop = true;
                }
                if (state.getPiece(x - i, y + i).getPieceColor() != color && !stop) {
                    xAttackThrough.add(x - i);
                    yAttackThrough.add(y + i);
                }
                if (!stopDownLeft) {
                    xMovementAttack.add(x - i);
                    yMovementAttack.add(y + i);
                    xMovement.add(x - i);
                    yMovement.add(y + i);
                }
            }
            if (y + i < 8 && x + i < 8) {
                if (state.getPiece(x + i, y + i).getPieceColor() == colorInverse && !stopDownRight) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y + i);
                    xMovement.add(x + i);
                    yMovement.add(y + i);
                    stopDownRight = true;
                }
                if (state.getPiece(x + i, y + i).getPieceColor() == colorInverse && !stopDownRight) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y + i);
                }
                if (state.getPiece(x + i, y + i).getPieceColor() == color) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y + i);
                    stopDownRight = true;
                    stop = true;
                }
                if (state.getPiece(x + i, y + i).getPieceColor() != color && !stop) {
                    xAttackThrough.add(x + i);
                    yAttackThrough.add(y + i);
                }
                if (!stopDownRight) {
                    xMovementAttack.add(x + i);
                    yMovementAttack.add(y + i);
                    xMovement.add(x + i);
                    yMovement.add(y + i);
                }
            }
        }
    }

    public ArrayList<Integer> getXAttack() {
        return xMovementAttack;
    }

    public ArrayList<Integer> getYAttack() {
        return yMovementAttack;
    }

    public ArrayList<Integer> getX() {
        return xMovement;
    }

    public ArrayList<Integer> getY() {
        return yMovement;
    }

    public ArrayList<Integer> getXAttackThrough() {
        return xAttackThrough;
    }

    public ArrayList<Integer> getYAttackThrough() {
        return yAttackThrough;
    }
}
