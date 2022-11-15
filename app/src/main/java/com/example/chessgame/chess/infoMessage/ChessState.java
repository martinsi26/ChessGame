package com.example.chessgame.chess.infoMessage;


import com.example.chessgame.GameFramework.infoMessage.GameState;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains the state of a Chess game.
 *
 * @author Simon
 * @author Jayven
 * @author Jonathan
 * @author Sebastian
 * @version October 2022
 */
public class ChessState extends GameState implements Serializable {

    private Piece[][] pieces; // An array that holds all of the pieces and their position
    private int[][] board; // An array that determines what kind of drawing should be made
    private int turnCount;
    private Piece kingBlack;
    private Piece kingWhite;

    private ArrayList<Piece> whiteCapturedPieces;
    private ArrayList<Piece> blackCapturedPieces;

    public Piece emptyPiece;

    //0: white
    //1: black
    private int playerToMove;

    public ChessState() {
        pieces = new Piece[8][8];
        board = new int[8][8];
        whiteCapturedPieces = new ArrayList<>();
        blackCapturedPieces = new ArrayList<>();

        // Setting the initial position of all of the pieces
        for (int row = 0; row < pieces.length; row++) {
            for (int col = 0; col < pieces[row].length; col++) {
                if (col == 0) {
                    pieces[0][col] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.BLACK, 0, col);
                    pieces[1][col] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.BLACK, 1, col);
                    pieces[2][col] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.BLACK, 2, col);
                    pieces[3][col] = new Piece(Piece.PieceType.QUEEN, Piece.ColorType.BLACK, 3, col);
                    pieces[4][col] = new Piece(Piece.PieceType.KING, Piece.ColorType.BLACK, 4, col);
                    pieces[5][col] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.BLACK, 5, col);
                    pieces[6][col] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.BLACK, 6, col);
                    pieces[7][col] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.BLACK, 7, col);
                } else if (col == 1) {
                    pieces[row][1] = new Piece(Piece.PieceType.PAWN, Piece.ColorType.BLACK, row, 1);
                } else if (col == 6) {
                    pieces[row][6] = new Piece(Piece.PieceType.PAWN, Piece.ColorType.WHITE, row, 6);
                } else if (col == 7) {
                    pieces[0][col] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.WHITE, 0, col);
                    pieces[1][col] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.WHITE, 1, col);
                    pieces[2][col] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.WHITE, 2, col);
                    pieces[3][col] = new Piece(Piece.PieceType.QUEEN, Piece.ColorType.WHITE, 3, col);
                    pieces[4][col] = new Piece(Piece.PieceType.KING, Piece.ColorType.WHITE, 4, col);
                    pieces[5][col] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.WHITE, 5, col);
                    pieces[6][col] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.WHITE, 6, col);
                    pieces[7][col] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.WHITE, 7, col);
                } else {
                    pieces[row][col] = new Piece(Piece.PieceType.EMPTY, Piece.ColorType.EMPTY, row, col);
                }
            }
        }
        kingWhite = new Piece(Piece.PieceType.KING, Piece.ColorType.WHITE, 4, 7);
        kingBlack = new Piece(Piece.PieceType.KING, Piece.ColorType.BLACK, 4, 0);
        emptyPiece = new Piece(Piece.PieceType.EMPTY, Piece.ColorType.EMPTY, 0, 0);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = 0;
            }
        }
        playerToMove = 0;
        turnCount = 0;
    }

    // Copy Constructor
    public ChessState(ChessState other) {
        pieces = new Piece[8][8];
        board = new int[8][8];

        //copy captured pieces
        whiteCapturedPieces = new ArrayList<>();
        blackCapturedPieces = new ArrayList<>();

        for(int i = 0; i < whiteCapturedPieces.size(); i++){
            whiteCapturedPieces.add(other.whiteCapturedPieces.get(i));
        }
        for(int i = 0; i < blackCapturedPieces.size(); i++){
            blackCapturedPieces.add(other.blackCapturedPieces.get(i));
        }

        //copies pieces into copy
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                Piece.PieceType tempPieceType = other.pieces[i][j].getPieceType();
                Piece.ColorType tempColorType = other.pieces[i][j].getPieceColor();
                int tempX = other.pieces[i][j].getX();
                int tempY = other.pieces[i][j].getY();
                pieces[i][j] = new Piece(tempPieceType, tempColorType, tempX, tempY);
            }
        }

        //copies what needs to be drawn on the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = other.board[i][j];
            }
        }

        Piece.PieceType kingWhiteTempPieceType = other.kingWhite.getPieceType();
        Piece.ColorType kingWhiteTempColorType = other.kingWhite.getPieceColor();
        int kingWhiteTempX = other.kingWhite.getX();
        int kingWhiteTempY = other.kingWhite.getY();
        kingWhite = new Piece(kingWhiteTempPieceType, kingWhiteTempColorType, kingWhiteTempX, kingWhiteTempY);

        Piece.PieceType kingBlackTempPieceType = other.kingBlack.getPieceType();
        Piece.ColorType kingBlackTempColorType = other.kingBlack.getPieceColor();
        int kingBlackTempX = other.kingBlack.getX();
        int kingBlackTempY = other.kingBlack.getY();
        kingBlack = new Piece(kingBlackTempPieceType, kingBlackTempColorType, kingBlackTempX, kingBlackTempY);

        Piece.PieceType emptyTempPieceType = other.emptyPiece.getPieceType();
        Piece.ColorType emptyTempColorType = other.emptyPiece.getPieceColor();
        int emptyTempX = other.emptyPiece.getX();
        int emptyTempY = other.emptyPiece.getY();
        emptyPiece = new Piece(emptyTempPieceType, emptyTempColorType, emptyTempX, emptyTempY);
        
        playerToMove = other.playerToMove;
        turnCount = other.turnCount;
    }

    public Piece getPiece(int row, int col) {
        return pieces[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        piece.setY(col);
        piece.setX(row);
        pieces[row][col] = piece;
    }

    public void setKingWhite(int row, int col) {
        kingWhite.setX(row);
        kingWhite.setY(col);
    }

    public void setKingBlack(int row, int col) {
        kingBlack.setX(row);
        kingBlack.setY(col);
    }

    public Piece getKingWhite() {
        return kingWhite;
    }

    public Piece getKingBlack() {
        return kingBlack;
    }

    public void setHighlightCheck(int row, int col) {
        board[row][col] = 3;
    }

    public void setHighlight(int row, int col) {
        board[row][col] = 1;
    }

    public void setCircles(ArrayList<Integer> row, ArrayList<Integer> col) {
        for(int i = 0; i < row.size(); i++) {
            board[row.get(i)][col.get(i)] = 2;
        }
    }

    public void removeHighlight() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    board[i][j] = 0;
                }
            }
        }
    }

    public void removeDot() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 2) {
                    board[i][j] = 0;
                }
            }
        }
    }

    public void removeDot(int row, int col) {
        if (board[row][col] == 2) {
            board[row][col] = 0;
        }
    }

    public void removeHighlightCheck() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 3) {
                    board[i][j] = 0;
                }
            }
        }
    }

    public int getDrawing(int row, int col) {
        return board[row][col];
    }

    public int getWhoseMove() {
        return playerToMove;
    }

    public void setWhoseMove(int id) {
        playerToMove = id;
    }

    public ArrayList<Piece> getWhiteCapturedPieces(){return this.whiteCapturedPieces;}
    public ArrayList<Piece> getBlackCapturedPieces(){return this.blackCapturedPieces;}

    public void addWhiteCapturedPiece(Piece p){
        whiteCapturedPieces.add(p);
    }

}

