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
        ChessState chessState = new ChessState((ChessState) info);

        availablePieces = new ArrayList<Piece>();

        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                Piece temp = chessState.getPiece(i, k);
                if (playerNum == 0 && temp.getPieceColor() == Piece.ColorType.WHITE) {
                    availablePieces.add(temp);
                } else if (playerNum == 1 && temp.getPieceColor() == Piece.ColorType.BLACK) {
                    availablePieces.add(temp);
                }
            }
        }

        Collections.shuffle(availablePieces);

        for (int i = 0; i < availablePieces.size(); i++) {
            selection = availablePieces.get(i);

            Piece.ColorType color = selection.getPieceColor();

            int xVal = selection.getX();
            int yVal = selection.getY();
            game.sendAction(new ChessSelectAction(this, xVal, yVal));
            sleep(1);

            if (selection.getPieceType() == Piece.PieceType.PAWN) {
                Pawn pawn = new Pawn(selection, chessState, color);

                ints = new ArrayList<>();
                if (pawn.getX().size() > 0) {
                    for (int j = 0; j < pawn.getX().size(); j++) {
                        ints.add(j);
                    }
                }
                if (ints.size() > 0) {
                    Collections.shuffle(ints);
                    xVal = pawn.getX().get(ints.get(0));
                    yVal = pawn.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                }
            } else if (selection.getPieceType() == Piece.PieceType.KNIGHT) {
                Knight knight = new Knight(selection, chessState, color);

                ints = new ArrayList<>();
                for (int j = 0; j < knight.getX().size(); j++) {
                    ints.add(j);
                }
                if (ints.size() > 0) {
                    Collections.shuffle(ints);
                    xVal = knight.getX().get(ints.get(0));
                    yVal = knight.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                }
            } else if (selection.getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(selection, chessState, color);
                ints = new ArrayList<>();
                for (int j = 0; j < bishop.getX().size(); j++) {
                    ints.add(j);
                }
                if (ints.size() > 0) {
                    Collections.shuffle(ints);
                    xVal = bishop.getX().get(ints.get(0));
                    yVal = bishop.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                }
            } else if (selection.getPieceType() == Piece.PieceType.ROOK) {
                Rook rook = new Rook(selection, chessState, color);
                ints = new ArrayList<>();
                for (int j = 0; j < rook.getX().size(); j++) {
                    ints.add(j);
                }
                if (ints.size() > 0) {
                    Collections.shuffle(ints);
                    xVal = rook.getX().get(ints.get(0));
                    yVal = rook.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                }
            } else if (selection.getPieceType() == Piece.PieceType.QUEEN) {
                Queen queen = new Queen(selection, chessState, color);
                ints = new ArrayList<>();
                for (int j = 0; j < queen.getX().size(); j++) {
                    ints.add(j);
                }
                if (ints.size() > 0) {
                    Collections.shuffle(ints);
                    xVal = queen.getX().get(ints.get(0));
                    yVal = queen.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                }
            } else if (selection.getPieceType() == Piece.PieceType.KING) {
                King king = new King(selection, chessState, color);
                ints = new ArrayList<>();
                for (int j = 0; j < king.getX().size(); j++) {
                    ints.add(j);
                }
                if (ints.size() > 0) {
                    Collections.shuffle(ints);

                    xVal = king.getX().get(ints.get(0));
                    yVal = king.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                }
            }


            if (ints.size() != 0) {
                break;
            }
        }
    }
}

