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