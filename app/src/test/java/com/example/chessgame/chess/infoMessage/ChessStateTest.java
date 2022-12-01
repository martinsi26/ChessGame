package com.example.chessgame.chess.infoMessage;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class ChessStateTest {

    //Jayven
    @Test
    public void getPiece() {
        ChessState state = new ChessState();
        Piece p = state.getPiece(0,7);
        assertEquals(0,p.getX());
        assertEquals(7,p.getY());
    }

    //Sebastian
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

    //Simon
    @Test
    public void setKingWhite() {
        ChessState state = new ChessState();
        Piece p = state.getPiece(0, 7);
        state.setKingWhite(p.getX(), p.getY());
        assertEquals(0, state.getKingWhite().getX());
        assertEquals(7, state.getKingWhite().getY());
    }

    //Simon
    @Test
    public void setKingBlack() {
        ChessState state = new ChessState();
        Piece p = state.getPiece(0, 7);
        state.setKingBlack(p.getX(), p.getY());
        assertEquals(0, state.getKingBlack().getX());
        assertEquals(7, state.getKingBlack().getY());
    }

    //Simon
    @Test
    public void getKingWhite() {
        ChessState state = new ChessState();
        Piece king = state.getKingWhite();
        assertEquals(king, state.getKingWhite());
    }

    //Simon
    @Test
    public void getKingBlack() {
        ChessState state = new ChessState();
        Piece king = state.getKingBlack();
        assertEquals(king, state.getKingBlack());
    }

    //Sebastian
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

    //Sebastian
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

    //Jonathan and Simon
    @Test
    public void setCircles() {
        ChessState state = new ChessState();
        ArrayList<Integer> testX = new ArrayList<>();
        testX.add(7);
        testX.add(3);
        testX.add(0);
        ArrayList<Integer> testY = new ArrayList<>();
        testY.add(7);
        testY.add(3);
        testY.add(0);
        state.setCircles(testX,testY);
        assertEquals(4, state.getDrawing(7,7));
        assertEquals(2, state.getDrawing(3,3));
        assertEquals(4, state.getDrawing(0,0));
    }

    //Jonathan and Sebastian
    @Test
    public void removeHighlight() {
        ChessState test = new ChessState();
        test.setHighlight(6,3);
        assertEquals(1, test.getDrawing(6,3));

        //edge cases
        test.setHighlight(0,0);
        assertEquals(1, test.getDrawing(0,0));

        test.setHighlight(7,7);
        assertEquals(1, test.getDrawing(7,7));

        test.removeHighlight();

        assertEquals(0, test.getDrawing(6,3));
        assertEquals(0, test.getDrawing(0,0));
        assertEquals(0, test.getDrawing(7,7));
    }

    //Jonathan and Simon
    @Test
    public void removeCircle() {
        ChessState state = new ChessState();
        ArrayList<Integer> testX = new ArrayList<>();
        testX.add(7);
        testX.add(3);
        testX.add(0);
        ArrayList<Integer> testY = new ArrayList<>();
        testY.add(7);
        testY.add(3);
        testY.add(0);
        state.setCircles(testX,testY);
        assertEquals(4, state.getDrawing(7,7));
        assertEquals(2, state.getDrawing(3,3));
        assertEquals(4, state.getDrawing(0,0));
        state.removeCircle();
        assertEquals(0, state.getDrawing(7,7));
        assertEquals(0, state.getDrawing(3,3));
        assertEquals(0, state.getDrawing(0,0));
    }

    //Jonathan and Simon
    @Test
    public void removeHighlightCheck() {
        ChessState state = new ChessState();
        state.setHighlightCheck(0,0);
        assertEquals(3, state.getDrawing(0,0));
        state.removeHighlightCheck();
        assertEquals(0, state.getDrawing(0,0));
    }

    //Jonathan and Simon
    @Test
    public void getDrawing() {
        ChessState state = new ChessState();
        state.setHighlight(1, 1);
        assertEquals(1, state.getDrawing(1, 1));
        assertEquals(0, state.getDrawing(1, 2));
    }

    //Jayven
    @Test
    public void getWhoseMove() {
        ChessState state = new ChessState();
        assertEquals(0,state.getWhoseMove());
    }

    //Jayven
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