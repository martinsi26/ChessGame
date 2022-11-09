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

public class ChessComputerPlayer2 extends GameComputerPlayer {

    private Piece selection;
    private ArrayList<Piece> availablePieces;

    /**
     * constructor
     *
     * @param name the player's name (e.g., "John")
     */
    public ChessComputerPlayer2(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;

        if (info instanceof IllegalMoveInfo) return;
        ChessState chessState = new ChessState((ChessState) info);

        if (selection == null) {

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
        }

        for (int h = 0; h < availablePieces.size(); h++) {


            selection = availablePieces.get(h);
            int xVal = selection.getX();
            int yVal = selection.getY();
            game.sendAction(new ChessSelectAction(this, xVal, yVal));
            sleep(1);

            //need to send a possible coord to move to
            //sleep(1);
            //first check which piece is selected and assemble a list of possible moves
            //iterate through these moves
            if (selection.getPieceType() == Piece.PieceType.PAWN) {
                Pawn pawn = new Pawn(selection, chessState, selection.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < pawn.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);

                if (ints.size() > 0) {
                    xVal = pawn.getX().get(ints.get(0));
                    yVal = pawn.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                    selection = null;
                    for(int i = 0; i < availablePieces.size(); i++) {
                        availablePieces.remove(i);
                    }

                }


            } else if (selection.getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(selection, chessState, selection.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < bishop.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);

                if (ints.size() > 0) {
                    xVal = bishop.getX().get(ints.get(0));
                    yVal = bishop.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                    selection = null;
                    for(int i = 0; i < availablePieces.size(); i++) {
                        availablePieces.remove(i);
                    }
                }


            } else if (selection.getPieceType() == Piece.PieceType.KNIGHT) {
                Knight knight = new Knight(selection, chessState, selection.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < knight.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);

                if (ints.size() > 0) {
                    xVal = knight.getX().get(ints.get(0));
                    yVal = knight.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                    selection = null;
                    for(int i = 0; i < availablePieces.size(); i++) {
                        availablePieces.remove(i);
                    }
                }

            } else if (selection.getPieceType() == Piece.PieceType.ROOK) {
                Rook rook = new Rook(selection, chessState, selection.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < rook.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);

                if (ints.size() > 0) {
                    xVal = rook.getX().get(ints.get(0));
                    yVal = rook.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                    selection = null;
                    for(int i = 0; i < availablePieces.size(); i++) {
                        availablePieces.remove(i);
                    }
                }


            } else if (selection.getPieceType() == Piece.PieceType.QUEEN) {
                Queen queen = new Queen(selection, chessState, selection.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < queen.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);

                if (ints.size() > 0) {
                    xVal = queen.getX().get(ints.get(0));
                    yVal = queen.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                    selection = null;
                    for(int i = 0; i < availablePieces.size(); i++) {
                        availablePieces.remove(i);
                    }
                }


            } else if (selection.getPieceType() == Piece.PieceType.KING) {
                King king = new King(selection, chessState, selection.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < king.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);

                if (ints.size() > 0) {
                    xVal = king.getX().get(ints.get(0));
                    yVal = king.getY().get(ints.get(0));
                    game.sendAction(new ChessMoveAction(this, xVal, yVal));
                    selection = null;
                    for(int i = 0; i < availablePieces.size(); i++) {
                        availablePieces.remove(i);
                    }
                }

            }
            //if none work then move on to the next piece

            if(selection == null){
                break;
            }


        }
    }
}
