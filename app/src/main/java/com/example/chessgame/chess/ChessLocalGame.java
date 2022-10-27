package com.example.chessgame.chess;

import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.chess.infoMessage.ChessState;

public class ChessLocalGame extends LocalGame {

    //constructor
    public ChessLocalGame() {
        // perform superclass initialization
        super();
        // create a new, unfilled-in TTTState object
        super.state = new ChessState();
    }

    //constructor with loaded chessState
    public ChessLocalGame(ChessState chessState){
        super();
        super.state = new ChessState(chessState);
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

    }

    @Override
    protected boolean canMove(int playerIdx) {
        return false;
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        return false;
    }
}
