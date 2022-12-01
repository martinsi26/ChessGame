package com.example.chessgame.chess.players;

import com.example.chessgame.GameFramework.infoMessage.GameInfo;
import com.example.chessgame.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgame.GameFramework.players.GameComputerPlayer;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
import com.example.chessgame.chess.chessActionMessage.ChessSelectAction;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;
import com.example.chessgame.chess.pieces.Bishop;
import com.example.chessgame.chess.pieces.King;
import com.example.chessgame.chess.pieces.Knight;
import com.example.chessgame.chess.pieces.Pawn;
import com.example.chessgame.chess.pieces.Queen;
import com.example.chessgame.chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collections;

public class ChessComputerPlayer extends GameComputerPlayer {

    private Piece selection;
    private ArrayList<Piece> availablePieces;
    private ArrayList<Integer> ints;

    /**
     * Constructor for the ChessComputerPlayer1 class
     */
    public ChessComputerPlayer(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;

        if (info instanceof IllegalMoveInfo) return;

        ChessState state = new ChessState((ChessState) info);

        if (state.getGameOver()) {
            return;
        }
        if (state.getWhoseMove() == 1 && playerNum == 0) {
            return;
        }
        if (state.getWhoseMove() == 0 && playerNum == 1) {
            return;
        }
        // all of the pieces that can move on the computers side
        availablePieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (state.getDrawing(i, k) == 1) {
                    return;
                }
                if (state.getDrawing(i, k) == 3) {
                    sleep(1);
                }
                Piece p = state.getPiece(i, k);
                if (playerNum == 0 && p.getPieceColor() == Piece.ColorType.WHITE) {
                    availablePieces.add(p);
                } else if (playerNum == 1 && p.getPieceColor() == Piece.ColorType.BLACK) {
                    availablePieces.add(p);
                }
            }
        }
        // randomly shuffle the pieces in the array
        Collections.shuffle(availablePieces);
        // make the computer selected whatever the first value in the array is now
        selection = availablePieces.get(0);
        // create variables to hold the x and y of the position selected
        int xVal = selection.getX();
        int yVal = selection.getY();
        // call the selection game action
        game.sendAction(new ChessSelectAction(this, xVal, yVal));
        // check if the piece is one that can move
        ChessState chessState = (ChessState) game.getGameState();
        for (int i = 1; i < availablePieces.size(); i++) {
            if (!chessState.getCanMove()) {
                selection = availablePieces.get(i);
                xVal = selection.getX();
                yVal = selection.getY();
                game.sendAction(new ChessSelectAction(this, xVal, yVal));
            } else {
                break;
            }
        }
        sleep(1);

        if (selection.getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(selection, chessState, selection.getPieceColor());
            ints = new ArrayList<>();
            for (int i = 0; i < pawn.getX().size(); i++) {
                ints.add(i);
            }
            Collections.shuffle(ints);
            xVal = pawn.getX().get(ints.get(0));
            yVal = pawn.getY().get(ints.get(0));
            game.sendAction(new ChessMoveAction(this, xVal, yVal));
        } else if (selection.getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(selection, chessState, selection.getPieceColor());
            ints = new ArrayList<>();
            for (int i = 0; i < bishop.getX().size(); i++) {
                ints.add(i);
            }
            Collections.shuffle(ints);
            xVal = bishop.getX().get(ints.get(0));
            yVal = bishop.getY().get(ints.get(0));
            game.sendAction(new ChessMoveAction(this, xVal, yVal));
        } else if (selection.getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(selection, chessState, selection.getPieceColor());
            ints = new ArrayList<>();
            for (int i = 0; i < knight.getX().size(); i++) {
                ints.add(i);
            }
            Collections.shuffle(ints);
            xVal = knight.getX().get(ints.get(0));
            yVal = knight.getY().get(ints.get(0));
            game.sendAction(new ChessMoveAction(this, xVal, yVal));
        } else if (selection.getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(selection, chessState, selection.getPieceColor());
            ints = new ArrayList<>();
            for (int i = 0; i < rook.getX().size(); i++) {
                ints.add(i);
            }
            Collections.shuffle(ints);
            xVal = rook.getX().get(ints.get(0));
            yVal = rook.getY().get(ints.get(0));
            game.sendAction(new ChessMoveAction(this, xVal, yVal));
        } else if (selection.getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(selection, chessState, selection.getPieceColor());
            ints = new ArrayList<>();
            for (int i = 0; i < queen.getX().size(); i++) {
                ints.add(i);
            }
            Collections.shuffle(ints);
            xVal = queen.getX().get(ints.get(0));
            yVal = queen.getY().get(ints.get(0));
            game.sendAction(new ChessMoveAction(this, xVal, yVal));
        } else if (selection.getPieceType() == Piece.PieceType.KING) {
            King king = new King(selection, chessState, selection.getPieceColor());
            ints = new ArrayList<>();
            for (int i = 0; i < king.getX().size(); i++) {
                ints.add(i);
            }
            Collections.shuffle(ints);
            xVal = king.getX().get(ints.get(0));
            yVal = king.getY().get(ints.get(0));
            game.sendAction(new ChessMoveAction(this, xVal, yVal));
        }
    }
}


