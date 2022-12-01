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
        assertNotEquals(p2, test.getPiece(3,4));

        //checks edge case: 0
        test.setPiece(0, 0, p2);
        assertEquals(p2, test.getPiece(0,0));
        assertNotEquals(p3, test.getPiece(0,0));

        //checks edge case: 7
        test.setPiece(7, 7, p3);
        assertEquals(p3, test.getPiece(7,7));
        assertNotEquals(p1, test.getPiece(7,7));

    }

    @Test
    public void setKingWhite() {
        ChessState state = new ChessState();
        Piece p = state.getPiece(0, 7);
        state.setKingWhite(p.getX(), p.getY());
        assertEquals(0, state.getKingWhite().getX());
        assertEquals(7, state.getKingWhite().getY());
    }

    @Test
    public void setKingBlack() {
        ChessState state = new ChessState();
        Piece p = state.getPiece(0, 7);
        state.setKingBlack(p.getX(), p.getY());
        assertEquals(0, state.getKingBlack().getX());
        assertEquals(7, state.getKingBlack().getY());
    }

    @Test
    public void getKingWhite() {
        ChessState state = new ChessState();
        Piece king = state.getKingWhite();
        assertEquals(king, state.getKingWhite());
    }

    @Test
    public void getKingBlack() {
        ChessState state = new ChessState();
        Piece king = state.getKingBlack();
        assertEquals(king, state.getKingBlack());
    }

    @Test
    public void setHighlightCheck() {
        ChessState test = new ChessState();
        test.setHighlightCheck(5,2);
        assertEquals(3, test.getDrawing(5,2));

        //edge cases
        test.setHighlightCheck(0,0);
        assertEquals(3, test.getDrawing(0,0));

        test.setHighlightCheck(7,7);
        assertEquals(3, test.getDrawing(7,7));
    }

    @Test
    public void setHighlight() {
        ChessState test = new ChessState();
        test.setHighlight(6,3);
        assertEquals(1, test.getDrawing(6,3));

        //edge cases
        test.setHighlight(0,0);
        assertEquals(1, test.getDrawing(0,0));

        test.setHighlight(7,7);
        assertEquals(1, test.getDrawing(7,7));
    }

    @Test
    public void setCircles() {
    }

    @Test
    public void removeHighlight() {
    }

    @Test
    public void removeCircle() {
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

    //Jonathan
    @Test
    public void getWhiteCapturedPieces() {
        ChessState state = new ChessState();
        assertEquals(0, state.getWhiteCapturedPieces().size());
        Piece p = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.WHITE, 0, 0);
        state.getWhiteCapturedPieces().add(p);
        assertEquals(p, state.getWhiteCapturedPieces().get(0));
    }

    //Jonathan
    @Test
    public void getBlackCapturedPieces() {
        ChessState state = new ChessState();
        assertEquals(0, state.getBlackCapturedPieces().size());
        Piece p = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.BLACK, 0, 0);
        state.getBlackCapturedPieces().add(p);
        assertEquals(p, state.getBlackCapturedPieces().get(0));
    }

    //Jonathan
    @Test
    public void addWhiteCapturedPiece() {
        ChessState state = new ChessState();
        Piece p = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.WHITE, 0, 0);
        state.addWhiteCapturedPiece(p);
        assertEquals(state.getWhiteCapturedPieces().get(0), p);
    }
}