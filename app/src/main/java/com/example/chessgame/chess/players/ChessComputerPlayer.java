package com.example.chessgame.chess.players;

import com.example.chessgame.GameFramework.infoMessage.GameInfo;
import com.example.chessgame.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgame.GameFramework.players.GameComputerPlayer;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
import com.example.chessgame.chess.chessActionMessage.ChessPromotionAction;
import com.example.chessgame.chess.chessActionMessage.ChessSelectAction;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;
import com.example.chessgame.chess.pieces.Bishop;
import com.example.chessgame.chess.pieces.King;
import com.example.chessgame.chess.pieces.Knight;
import com.example.chessgame.chess.pieces.Pawn;
import com.example.chessgame.chess.pieces.Queen;
import com.example.chessgame.chess.pieces.Rook;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
        //Ignore illegal move info too
        if (info instanceof IllegalMoveInfo) return;
        //just in case there is any other types of info, ignore it
        if(!(info instanceof ChessState)){
            return;
        }

        ChessState chessState = new ChessState((ChessState) info);
        //if(chessState.isPromoting){return;}
        if (chessState.getWhoseMove() == 1 && playerNum == 0) {
            return;
        }
        if (chessState.getWhoseMove() == 0 && playerNum == 1) {
            return;
        }

        // all of the pieces that can move on the computers side
        availablePieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (chessState.getDrawing(i, k) == 1) {
                    return;
                }
                if (chessState.getDrawing(i, k) == 3) {
                    sleep(1);
                }
                Piece p = chessState.getPiece(i, k);
                if (playerNum == 0 && p.getPieceColor() == Piece.ColorType.WHITE) {
                    availablePieces.add(p);
                } else if (playerNum == 1 && p.getPieceColor() == Piece.ColorType.BLACK) {
                    availablePieces.add(p);
                }
            }
        }
        // randomly shuffle the pieces in the array
        Collections.shuffle(availablePieces);
        selection = availablePieces.get(0);
        // create variables to hold the x and y of the position selected
        int xVal = selection.getX();
        int yVal = selection.getY();
        // call the selection game action
        game.sendAction(new ChessSelectAction(this, xVal, yVal));
        // check if the piece is one that can move
        ChessState chessState2 = (ChessState) game.getGameState();
        for (int i = 1; i < availablePieces.size(); i++) {
            if (!chessState2.getCanMove()) {
                selection = availablePieces.get(i);
                xVal = selection.getX();
                yVal = selection.getY();
                game.sendAction(new ChessSelectAction(this, xVal, yVal));
            } else {
                break;
            }
        }
        sleep(1);

        if(chessState2.getGameOver()) {
            return;
        }
        // an arraylist that holds the index values of the two movement arraylists (x and y)
        ArrayList<Integer> index = new ArrayList<>();
        // add all of the indexes into the ints value
        for (int i = 0; i < chessState2.getNewMovementsX().size(); i++) {
            index.add(i);
        }
        // shuffle the indexes so a random x and y value can be taken
        Collections.shuffle(index);
        // set the x and y values to the new movements array at the index
        xVal = chessState2.getNewMovementsX().get(index.get(0));
        yVal = chessState2.getNewMovementsY().get(index.get(0));
        // if the piece is a pawn look for promotion
        if (selection.getPieceType() == Piece.PieceType.PAWN) {
            if (selection.getPieceColor() == Piece.ColorType.BLACK) {
                if (yVal == 7) {
                    sendPromotionAction(xVal, yVal, Piece.ColorType.BLACK);
                }
            } else if (selection.getPieceColor() == Piece.ColorType.WHITE) {
                if (yVal == 0) {
                    sendPromotionAction(xVal, yVal, Piece.ColorType.WHITE);
                }
            }
        }
        // send the new move action
        game.sendAction(new ChessMoveAction(this, xVal, yVal));
    }

    public void sendPromotionAction(int xVal, int yVal, Piece.ColorType type) {
        Random rand = new Random();
        int choice = rand.nextInt(4);
        switch (choice) {
            case (0):
                game.sendAction(new ChessPromotionAction(this,
                        new Piece(Piece.PieceType.QUEEN, type, xVal, yVal), xVal, yVal));
                break;
            case (1):
                game.sendAction(new ChessPromotionAction(this,
                        new Piece(Piece.PieceType.KNIGHT, type, xVal, yVal), xVal, yVal));
                break;
            case (2):
                game.sendAction(new ChessPromotionAction(this,
                        new Piece(Piece.PieceType.BISHOP, type, xVal, yVal), xVal, yVal));
                break;
            case (3):
                game.sendAction(new ChessPromotionAction(this,
                        new Piece(Piece.PieceType.ROOK, type, xVal, yVal), xVal, yVal));
                break;
        }
    }
}


