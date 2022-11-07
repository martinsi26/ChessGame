package com.example.chessgame.chess.players;

import com.example.chessgame.GameFramework.infoMessage.GameInfo;
import com.example.chessgame.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgame.GameFramework.players.GameComputerPlayer;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
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

        ArrayList<Piece> availablePieces = new ArrayList<Piece>();

        for(int i = 0; i < 7; i++){
            for(int k = 0; k < 7; k++){
                Piece temp = chessState.getPiece(i, k);
                if(playerNum == 0 && temp.getPieceColor() == Piece.ColorType.WHITE) {
                    availablePieces.add(temp);
                }
                else if(playerNum == 1 && temp.getPieceColor() == Piece.ColorType.BLACK){
                    availablePieces.add(temp);
                }
            }
        }

        Collections.shuffle(availablePieces);

        for(Piece p : availablePieces){
            int xVal = p.getX();
            int yVal = p.getY();
            //sends the selection action
            game.sendAction(new ChessMoveAction(this, yVal, xVal));
            //need to send a possible coord to move to
            sleep(1);
            //first check which piece is selected and assemble a list of possible moves
            //iterate through these moves
            if(p.getPieceType() == Piece.PieceType.PAWN){
                Pawn pawn = new Pawn(p, chessState, p.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for(int i = 0; i < pawn.getX().size(); i++){
                    ints.add(i);
                }
                Collections.shuffle(ints);
                for(int k = 0; k < ints.size(); k++){
                    xVal = pawn.getX().get(ints.get(k));
                    yVal = pawn.getY().get(ints.get(k));
                    game.sendAction(new ChessMoveAction(this, yVal, xVal));
                }
            }
            else if(p.getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(p, chessState, p.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < bishop.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);
                for (int k = 0; k < ints.size(); k++) {
                    xVal = bishop.getX().get(ints.get(k));
                    yVal = bishop.getY().get(ints.get(k));
                    game.sendAction(new ChessMoveAction(this, yVal, xVal));
                }
            }
            else if(p.getPieceType() == Piece.PieceType.KNIGHT) {
                Knight knight = new Knight(p, chessState, p.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < knight.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);
                for (int k = 0; k < ints.size(); k++) {
                    xVal = knight.getX().get(ints.get(k));
                    yVal = knight.getY().get(ints.get(k));
                    game.sendAction(new ChessMoveAction(this, yVal, xVal));
                }
            }
            else if(p.getPieceType() == Piece.PieceType.ROOK) {
                Rook knight = new Rook(p, chessState, p.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < knight.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);
                for (int k = 0; k < ints.size(); k++) {
                    xVal = knight.getX().get(ints.get(k));
                    yVal = knight.getY().get(ints.get(k));
                    game.sendAction(new ChessMoveAction(this, yVal, xVal));
                }
            }
            else if(p.getPieceType() == Piece.PieceType.QUEEN) {
                Queen knight = new Queen(p, chessState, p.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < knight.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);
                for (int k = 0; k < ints.size(); k++) {
                    xVal = knight.getX().get(ints.get(k));
                    yVal = knight.getY().get(ints.get(k));
                    game.sendAction(new ChessMoveAction(this, yVal, xVal));
                }
            }
            else if(p.getPieceType() == Piece.PieceType.KING) {
                King knight = new King(p, chessState, p.getPieceColor());
                ArrayList<Integer> ints = new ArrayList<>();
                for (int i = 0; i < knight.getX().size(); i++) {
                    ints.add(i);
                }
                Collections.shuffle(ints);
                for (int k = 0; k < ints.size(); k++) {
                    xVal = knight.getX().get(ints.get(k));
                    yVal = knight.getY().get(ints.get(k));
                    game.sendAction(new ChessMoveAction(this, yVal, xVal));
                }
            }
                //if none work then move on to the next piece

        }
        /*
        // pick x and y positions at random (0-7)
        int xVal = (int)(8 * Math.random());
        int yVal = (int)(8 * Math.random());




        // delay for a second to make opponent think we're thinking
        //sleep(1);

        // Submit our move to the game object. We haven't even checked it it's
        // our turn, or that that position is unoccupied. If it was not our turn,
        // we'll get a message back that we'll ignore. If it was an illegal move,
        // we'll end up here again (and possibly again, and again). At some point,
        // we'll end up randomly pick a move that is legal.
        game.sendAction(new ChessMoveAction(this, yVal, xVal)); */
    }
}
