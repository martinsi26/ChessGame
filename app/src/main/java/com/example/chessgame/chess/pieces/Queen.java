package com.example.chessgame.chess.pieces;

import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

import java.util.ArrayList;

public class Queen {
    private ArrayList<Integer> xMovement;
    private ArrayList<Integer> yMovement;

    private int x;
    private int y;

    private Piece.ColorType colorInverse;

    public Queen(Piece piece, ChessState state, Piece.ColorType color) {
        x = piece.getX();
        y = piece.getY();
        xMovement = new ArrayList<>();
        yMovement = new ArrayList<>();
        if(color == Piece.ColorType.WHITE) {
            colorInverse = Piece.ColorType.BLACK;
        } else if (color == Piece.ColorType.BLACK) {
            colorInverse = Piece.ColorType.WHITE;
        }
        queenMovement(state, color);
    }

    public void queenMovement(ChessState state, Piece.ColorType color) {
        diagonal(state, color);
        side(state, color);
    }

    public void diagonal(ChessState state, Piece.ColorType color) {

        boolean stopUpLeft = false;
        boolean stopUpRight = false;
        boolean stopDownLeft = false;
        boolean stopDownRight = false;

        for (int i = 1; i < 8; i++) {
            if (y - i >= 0 && x - i >= 0) {
                if (state.getPiece(x - i, y - i).getPieceColor() == colorInverse && !stopUpLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y - i);
                    stopUpLeft = true;
                }
                if (state.getPiece(x - i, y - i).getPieceColor() == color) {
                    stopUpLeft = true;
                }
                if (!stopUpLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y - i);
                }
            }
            if (y - i >= 0 && x + i < 8) {
                if (state.getPiece(x + i, y - i).getPieceColor() == colorInverse && !stopUpRight) {
                    xMovement.add(x + i);
                    yMovement.add(y - i);
                    stopUpRight = true;
                }
                if (state.getPiece(x + i, y - i).getPieceColor() == color) {
                    stopUpRight = true;
                }
                if (!stopUpRight) {
                    xMovement.add(x + i);
                    yMovement.add(y - i);
                }
            }
            if (y + i < 8 && x - i >= 0) {
                if (state.getPiece(x - i, y + i).getPieceColor() == colorInverse && !stopDownLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y + i);
                    stopDownLeft = true;
                }
                if (state.getPiece(x - i, y + i).getPieceColor() == color) {
                    stopDownLeft = true;
                }
                if (!stopDownLeft) {
                    xMovement.add(x - i);
                    yMovement.add(y + i);
                }
            }
            if (y + i < 8 && x + i < 8) {
                if (state.getPiece(x + i, y + i).getPieceColor() == colorInverse && !stopDownRight) {
                    xMovement.add(x + i);
                    yMovement.add(y + i);
                    stopDownRight = true;
                }
                if (state.getPiece(x + i, y + i).getPieceColor() == color) {
                    stopDownRight = true;
                }
                if (!stopDownRight) {
                    xMovement.add(x + i);
                    yMovement.add(y + i);
                }
            }
        }
    }

    public void side(ChessState state, Piece.ColorType color) {
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
