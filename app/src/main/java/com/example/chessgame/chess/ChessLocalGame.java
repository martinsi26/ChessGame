package com.example.chessgame.chess;

import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;
import com.example.chessgame.chess.pieces.Bishop;
import com.example.chessgame.chess.pieces.King;
import com.example.chessgame.chess.pieces.Knight;
import com.example.chessgame.chess.pieces.Pawn;
import com.example.chessgame.chess.pieces.Queen;
import com.example.chessgame.chess.pieces.Rook;

public class ChessLocalGame extends LocalGame {

    //Tag for logging
    private static final String TAG = "ChessLocalGame";

    // the number of moves that have been played so far, used to
    // determine whether the game is over
    protected int moveCount;

    private int tempRow;
    private int tempCol;

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

        int row = tm.getRow();
        int col = tm.getCol();

        // get the 0/1 id of the player whose move it is
        int whoseMove = state.getWhoseMove();

        // white specific
        if(whoseMove == 0) {

            // check if they selected a piece or selected a new position to move
            // if the piece is white they are doing a selection
            if (state.getPiece(row, col).getPieceColor() == Piece.ColorType.WHITE) {

                // remove the highlights if there are any previous ones
                for(int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {
                        if(state.getHighlight(i,j) == 1) {
                            state.removeHighlight();
                        }
                    }
                }

                // highlight the piece they clicked
                state.setHighlight(row, col);

                // save temps for the row and col for movement later
                tempRow = row;
                tempCol = col;

                findMovement(state, row, col, Piece.ColorType.WHITE);

                // return true to skip changing turns
                return true;
            }

            // if the piece is empty or black they are doing a movement
            else {
                // unless the position they are moving to is not a circle
                // then they are not doing anything
                if(!setMovement(state,row,col)) {
                    return false;
                }
            }
        }

        // black specific
        if(whoseMove == 1) {

            // check if they selected a piece or selected a new position to move
            // if the piece is black they are doing a selection
            if (state.getPiece(row, col).getPieceColor() == Piece.ColorType.BLACK) {

                // remove the highlights if there are any previous ones
                for(int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {
                        if(state.getHighlight(i,j) == 1) {
                            state.removeHighlight();
                        }
                    }
                }

                // highlight the piece they clicked
                state.setHighlight(row, col);

                // save temps for the row and col for movement later
                tempRow = row;
                tempCol = col;

                findMovement(state, row, col, Piece.ColorType.BLACK);

                // return true to skip changing turns
                return true;
            }

            // if the piece is empty or white they are doing a movement
            else {
                if(!setMovement(state,row,col)) {
                    return false;
                }
            }
        }

        // make it the other player's turn
        state.setWhoseMove(1 - whoseMove);

        // bump the move count
        moveCount++;

        // return true, indicating the it was a legal move
        return true;
    }

    public void findMovement(ChessState state, int row, int col, Piece.ColorType color) {
        if (state.getPiece(row, col).getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(state.getPiece(row,col), state, color);
            state.setCircles(pawn.getX(), pawn.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(state.getPiece(row,col), state, color);
            state.setCircles(knight.getX(), knight.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(state.getPiece(row,col), state, color);
            state.setCircles(bishop.getX(), bishop.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(state.getPiece(row,col), state, color);
            state.setCircles(rook.getX(), rook.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(state.getPiece(row,col), state, color);
            state.setCircles(queen.getX(), queen.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KING) {
            King king = new King(state.getPiece(row,col), state, color);
            state.setCircles(king.getX(), king.getY());
        }
    }

    public boolean setMovement(ChessState state, int row, int col) {
        // if they have no selected a piece movement shouldn't occur
        if (tempRow == -1 || tempCol == -1) {
            return false;
        }
        if(state.getCircles(row,col) == 2) {


            // set the new position to be the piece they originally selected
            state.setPiece(row, col, state.getPiece(tempRow, tempCol));

            // change the piece at the selection to be an empty piece
            state.setPiece(tempRow, tempCol, state.emptyPiece);

            // reset temp values so only selections may occur
            tempRow = -1;
            tempCol = -1;

            // remove the highlighted square and all the circles after moving
            state.removeHighlight();
            state.removeCircles();

            return true;
        } else {
            return false;
        }
    }
}
