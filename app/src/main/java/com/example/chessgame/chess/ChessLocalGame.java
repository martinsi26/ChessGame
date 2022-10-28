package com.example.chessgame.chess;

import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;

public class ChessLocalGame extends LocalGame {

    //Tag for logging
    private static final String TAG = "ChessLocalGame";

    // the number of moves that have been played so far, used to
    // determine whether the game is over
    protected int moveCount;

    /**
     * Constructor for the TTTLocalGame.
     */
    public ChessLocalGame() {

        // perform superclass initialization
        super();

        // create a new, standard ChessState object
        super.state = new ChessState();
    }

    /**
     * Constructor for the TTTLocalGame with loaded tttState
     * @param chessState
     */
    public ChessLocalGame(ChessState chessState){
        super();
        super.state = new ChessState(chessState);
    }

    /**
     *  This is where you should initialize anything specific to the
     *  number of players.  For example you may need to init your
     *  game state or part of it.  Loading data could also happen here.
     *
     * 	 @param players
     */
    @Override
    public void start(GamePlayer[] players)
    {
        super.start(players);
    }

    @Override
    protected String checkIfGameOver() {
        return null;
    }

    /**
     * Notify the given player that its state has changed. This should involve sending
     * a GameInfo object to the player. If the game is not a perfect-information game
     * this method should remove any information from the game that the player is not
     * allowed to know.
     *
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        p.sendInfo(new ChessState(((ChessState) state)));
    }

    /**
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     * 		true iff the player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((ChessState)state).getWhoseMove();
    }

    /**
     * Makes a move on behalf of a player.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return
     * 			Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {

        // get the row and column position of the player's move
        ChessMoveAction tm = (ChessMoveAction) action;
        ChessState state = (ChessState) super.state;

        int newRow = tm.getNewRow();
        int newCol = tm.getNewCol();

        int currentRow = tm.getCurrentRow();
        int currentCol = tm.getCurrentCol();

        // get the 0/1 id of the player whose move it is
        int whoseMove = state.getWhoseMove();

        // move the players piece to the new location
        state.setPiece(newRow, newCol, state.getPiece(currentRow, currentCol));

        // make the it's original location become blank
        state.setPiece(currentRow, currentCol, state.emptyPiece);

        // make it the other player's turn
        state.setWhoseMove(1 - whoseMove);

        // bump the move count
        moveCount++;

        // return true, indicating the it was a legal move
        return true;
    }
}
