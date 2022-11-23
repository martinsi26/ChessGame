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