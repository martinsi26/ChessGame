package com.example.chessgame.chess.infoMessage;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChessStateTest {

    @Test
    public void getPiece() {
        ChessState state = new ChessState();
        Piece p = state.getPiece(0,7);
        assertEquals(0,p.getX());
        assertEquals(7,p.getY());
    }

    @Test
    public void setPiece() {
        ChessState test = new ChessState();
        Piece p1 = new Piece(Piece.PieceType.PAWN, Piece.ColorType.BLACK, 3,3);
        Piece p2 = new Piece(Piece.PieceType.KING, Piece.ColorType.WHITE, 2, 2);
        Piece p3 = new Piece(Piece.PieceType.QUEEN, Piece.ColorType.WHITE, 4, 4);

        //checks arbitrary location
        test.setPiece(3, 4, p1);
        assertEquals(p1, test.getPiece(3,4));

        //checks edge case: 0
        test.setPiece(0, 0, p2);
        assertEquals(p2, test.getPiece(0,0));

        //checks edge case: 7
        test.setPiece(7, 7, p3);
        assertEquals(p3, test.getPiece(7,7));

    }

    @Test
    public void setKingWhite() {

    }

    @Test
    public void setKingBlack() {
    }

    @Test
    public void getKingWhite() {
    }

    @Test
    public void getKingBlack() {
    }

    @Test
    public void setHighlightCheck() {
    }

    @Test
    public void setHighlight() {
    }

    @Test
    public void setCircles() {
    }

    @Test
    public void removeHighlight() {
    }

    @Test
    public void removeDot() {
    }

    @Test
    public void testRemoveDot() {
    }

    @Test
    public void removeHighlightCheck() {
    }

    @Test
    public void getDrawing() {
    }

    @Test
    public void getWhoseMove() {
        ChessState state = new ChessState();
        assertEquals(0,state.getWhoseMove());
    }

    @Test
    public void setWhoseMove() {
        ChessState state = new ChessState();
        state.setWhoseMove(1);
        assertEquals(1,state.getWhoseMove());
    }

    @Test
    public void getWhiteCapturedPieces() {
    }

    @Test
    public void getBlackCapturedPieces() {
    }

    @Test
    public void addWhiteCapturedPiece() {
    }
}