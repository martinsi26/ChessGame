package com.example.chessgame.chess;


import android.util.Log;

import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.GameFramework.utilities.MessageBox;
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
import com.example.chessgame.chess.players.ChessHumanPlayer;

import java.util.ArrayList;

import java.util.jar.Attributes;

public class ChessLocalGame extends LocalGame {

    //Tag for logging
    private static final String TAG = "ChessLocalGame";
    // piece that was selected by row and column
    private int tempRow;
    private int tempCol;

    // all of the initial movements of the piece selected
    private ArrayList<Integer> initialMovementsX = new ArrayList<>();
    private ArrayList<Integer> initialMovementsY = new ArrayList<>();

    // all of the valid movements so the king isn't in check
    private ArrayList<Integer> newMovementsX = new ArrayList<>();
    private ArrayList<Integer> newMovementsY = new ArrayList<>();


    /**
     * Constructor for the ChessLocalGame.
     */
    public ChessLocalGame() {

        // perform superclass initialization
        super();

        // create a new, standard ChessState object
        super.state = new ChessState();
    }


    /**
     * Constructor for the ChessLocalGame with loaded chessState
     *
     * @param chessState
     */
    public ChessLocalGame(ChessState chessState) {
        super();
        super.state = new ChessState(chessState);
    }

    /**
     * This is where you should initialize anything specific to the
     * number of players.  For example you may need to init your
     * game state or part of it.  Loading data could also happen here.
     *
     * @param players
     */

    @Override
    public void start(GamePlayer[] players) {
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
     * @param p the player to notify
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
     * @param playerIdx the player's player-number (ID)
     * @return true iff the player is allowed to move
     */
    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == ((ChessState) state).getWhoseMove();
    }

    /**
     * Makes a move on behalf of a player.
     *
     * @param action The move that the player has sent to the game
     * @return Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        ChessState state = (ChessState) super.state;

        // get the 0/1 id of the player whose move it is
        int whoseMove = state.getWhoseMove();
        if (action instanceof ChessSelectAction) {
            ChessSelectAction select = (ChessSelectAction) action;
            int row = select.getRow();
            int col = select.getCol();

            // remove the highlights if there are any previous ones
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (state.getDrawing(i, j) == 1) {
                        state.removeHighlight();
                        state.removeCircle();
                    }
                }
            }

            // remove the red highlight from being in check
            state.removeHighlightCheck();

            // highlight the piece they tapped
            state.setHighlight(row, col);

            // save temps for the row and col for movement later
            tempRow = row;
            tempCol = col;

            // the selected piece
            Piece p = state.getPiece(row, col);

            // find all movements of piece selected
            findMovement(state, p);

            // make fake movements and determine if that movement allows the
            // players own king be in check
            moveToNotBeInCheck(state, p.getPieceColor());

            // display all positions in arraylist as dots on the board
            state.setCircles(newMovementsX, newMovementsY);

            // return true to skip changing turns
            return true;
        } else if (action instanceof ChessMoveAction) {
            ChessMoveAction move = (ChessMoveAction) action;
            int row = move.getRow();
            int col = move.getCol();

            // if they have no selected piece movement shouldn't occur
            if (tempRow == -1 || tempCol == -1) {
                return false;
            }

            //very specific castling case- the selected piece and piece being moved to are the same color
            if(state.getPiece(tempRow, tempCol).getPieceColor() == state.getPiece(row, col).getPieceColor()){
                if(tempRow == 4 && tempCol == 7 && row == 7 && col == 7){

                }
            }

            //updates potential hasMoved variables
            if(state.getPiece(tempRow, tempCol).getPieceColor() == Piece.ColorType.WHITE){
                if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.KING) {
                    state.setWhiteKingHasMoved(true);
                }
                else if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.ROOK && tempCol == 7){
                    state.setWhiteRook1HasMoved(true);
                }
                else if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.ROOK && tempCol == 0){
                    state.setWhiteRook2HasMoved(true);
                }
            }
            else if(state.getPiece(tempRow, tempCol).getPieceColor() == Piece.ColorType.BLACK){
                if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.KING) {
                    state.setBlackKingHasMoved(true);
                }
                else if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.ROOK && tempCol == 7){
                    state.setBlackRook1HasMoved(true);
                }
                else if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.ROOK && tempCol == 0){
                    state.setBlackRook2HasMoved(true);
                }
            }

            Piece tempP = state.getPiece(tempRow, tempCol);

            // determine what team is moving (white/black) and move the piece
            if (tempP.getPieceColor() == Piece.ColorType.WHITE) {
                if (!setMovement(state, row, col, Piece.ColorType.WHITE)) {
                    state.removeHighlight();
                    state.removeCircle();
                    return false;
                }
            } else if (tempP.getPieceColor() == Piece.ColorType.BLACK) {
                if (!setMovement(state, row, col, Piece.ColorType.BLACK)) {
                    state.removeHighlight();
                    state.removeCircle();
                    return false;
                }
            }

            // make sure all highlights and dots are already removed
            state.removeCircle();

            // make it the other player's turn
            state.setWhoseMove(1 - whoseMove);



            // return true, indicating the it was a legal move
            return true;
        }
        // return true, indicating the it was a legal move
        return false;
    }

    /**
     * Finds all of the positions the Piece p can move to with normal movements
     *
     * @param state the current state of the game
     * @param p     the piece that is currently selected
     */
    public void findMovement(ChessState state, Piece p) {
        // make sure the arraylists are empty before they are filled
        initialMovementsX.clear();
        initialMovementsY.clear();

        // search through each type of piece and generate all of the movements
        // of that piece and add them to the initialMovement arraylists.
        if (p.getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(p, state, p.getPieceColor());
            for (int i = 0; i < pawn.getX().size(); i++) {
                initialMovementsX.add(pawn.getX().get(i));
                initialMovementsY.add(pawn.getY().get(i));
            }
        } else if (p.getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(p, state, p.getPieceColor());
            for (int i = 0; i < knight.getX().size(); i++) {
                initialMovementsX.add(knight.getX().get(i));
                initialMovementsY.add(knight.getY().get(i));
            }
        } else if (p.getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(p, state, p.getPieceColor());
            for (int i = 0; i < bishop.getX().size(); i++) {
                initialMovementsX.add(bishop.getX().get(i));
                initialMovementsY.add(bishop.getY().get(i));
            }
        } else if (p.getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(p, state, p.getPieceColor());
            for (int i = 0; i < rook.getX().size(); i++) {
                initialMovementsX.add(rook.getX().get(i));
                initialMovementsY.add(rook.getY().get(i));
            }
        } else if (p.getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(p, state, p.getPieceColor());
            for (int i = 0; i < queen.getX().size(); i++) {
                initialMovementsX.add(queen.getX().get(i));
                initialMovementsY.add(queen.getY().get(i));
            }
        } else if (p.getPieceType() == Piece.PieceType.KING) {
            King king = new King(p, state, p.getPieceColor());
            for (int i = 0; i < king.getX().size(); i++) {
                initialMovementsX.add(king.getX().get(i));
                initialMovementsY.add(king.getY().get(i));
            }
        }
    }

    /**
     * Determines if the current players king is in check with a certain
     * piece movement
     *
     * @param state      the copied state displaying a movement
     * @param teamColor  the color the player that is making a movement
     * @param enemyColor the color of the other player
     * @return Determines if a king is in check
     */
    public boolean checkForCheck(ChessState state, Piece.ColorType teamColor, Piece.ColorType enemyColor) {
        // search through every piece of the enemy and generate its general movement
        // with its position on the board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = state.getPiece(row, col);
                if (piece.getPieceColor() == enemyColor) {
                    if (piece.getPieceType() == Piece.PieceType.PAWN) {
                        Pawn pawn = new Pawn(piece, state, enemyColor);
                        state.setCircles(pawn.getXAttack(), pawn.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.KNIGHT) {
                        Knight knight = new Knight(piece, state, enemyColor);
                        state.setCircles(knight.getXAttack(), knight.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.BISHOP) {
                        Bishop bishop = new Bishop(piece, state, enemyColor);
                        state.setCircles(bishop.getXAttack(), bishop.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.ROOK) {
                        Rook rook = new Rook(piece, state, enemyColor);
                        state.setCircles(rook.getXAttack(), rook.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.QUEEN) {
                        Queen queen = new Queen(piece, state, enemyColor);
                        state.setCircles(queen.getXAttack(), queen.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.KING) {
                        King king = new King(piece, state, enemyColor);
                        state.setCircles(king.getXAttack(), king.getYAttack());
                    }
                }
            }
        }

        // determine what king will be in check with the movements generated
        Piece king = null;
        if (teamColor == Piece.ColorType.WHITE) {
            king = state.getKingWhite();
        } else if (teamColor == Piece.ColorType.BLACK) {
            king = state.getKingBlack();
        }
        if (king != null) {
            // if the king can be attacked by the enemy it means the king is in check
            if (state.getDrawing(king.getX(), king.getY()) == 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * Looks through all movements of the piece selected and determines
     * if that movement causes the players king to be in check
     *
     * @param state the current state of the game
     * @param color the color of the player that is making a move
     */
    public void moveToNotBeInCheck(ChessState state, Piece.ColorType color) {
        // make sure the arraylists are empty
        newMovementsX.clear();
        newMovementsY.clear();

        // iterate through all of the initial movements of the selected piece
        for (int i = 0; i < initialMovementsX.size(); i++) {

            // create a copied state so the current state is not affected yet
            ChessState copyState = new ChessState(state);

            // make one of the initial movements on the copied state
            makeTempMovement(copyState, initialMovementsX.get(i), initialMovementsY.get(i));

            // determine if the player is white or black so that can be passed
            // in as a parameter
            if (color == Piece.ColorType.WHITE) {

                // determine if the movement causes the players king to be in check
                if (!checkForCheck(copyState, color, Piece.ColorType.BLACK)) {

                    // if the player is not in check add that movement to the new
                    // arraylist so it can be saved
                    newMovementsX.add(initialMovementsX.get(i));
                    newMovementsY.add(initialMovementsY.get(i));
                }
            } else if (color == Piece.ColorType.BLACK) {
                if (!checkForCheck(copyState, color, Piece.ColorType.WHITE)) {
                    newMovementsX.add(initialMovementsX.get(i));
                    newMovementsY.add(initialMovementsY.get(i));
                }
            }
        }

        //loops for getting rid of crossing-check moves for castling
        boolean castle57Exists =false;
        boolean castle67Exists =false;
        boolean castle27Exists =false;
        boolean castle37Exists =false;
        boolean castle20Exists =false;
        boolean castle30Exists =false;
        boolean castle50Exists =false;
        boolean castle60Exists =false;
        int index1 = -1;
        int index2 = -1;
        //for white
        if(state.getPiece(4, 7).getPieceType() == Piece.PieceType.KING &&
                state.getPiece(4, 7).getPieceColor() == Piece.ColorType.WHITE) {
            for (int i = 0; i < newMovementsX.size(); i++) {
                int x = newMovementsX.get(i);
                int y = newMovementsY.get(i);
                if (x == 5 && y == 7) {
                    castle57Exists = true;
                }
                if(x == 6 && y == 7){
                    castle67Exists = true;
                    index1 = i;
                }
            }
            if(!castle57Exists && castle67Exists){
                newMovementsX.remove(index1);
                newMovementsY.remove(index1);
            }
            for (int i = 0; i < newMovementsX.size(); i++) {
                int x = newMovementsX.get(i);
                int y = newMovementsY.get(i);
                if (x == 2 && y == 7) {
                    castle27Exists = true;
                    index2 = i;
                }
                if(x == 3 && y == 7){
                    castle37Exists = true;
                }
            }
            if(!castle37Exists && castle27Exists){
                newMovementsX.remove(index2);
                newMovementsY.remove(index2);
            }
        }
        //for black
        else if(state.getPiece(4, 0).getPieceType() == Piece.PieceType.KING &&
                state.getPiece(4, 0).getPieceColor() == Piece.ColorType.BLACK) {
            for (int i = 0; i < newMovementsX.size(); i++) {
                int x = newMovementsX.get(i);
                int y = newMovementsY.get(i);
                if (x == 5 && y == 0) {
                    castle50Exists = true;
                }
                if(x == 6 && y == 0){
                    castle60Exists = true;
                    index1 = i;
                }
            }
            if(!castle50Exists && castle60Exists){
                newMovementsX.remove(index1);
                newMovementsY.remove(index1);
            }
            for (int i = 0; i < newMovementsX.size(); i++) {
                int x = newMovementsX.get(i);
                int y = newMovementsY.get(i);
                if (x == 2 && y == 0) {
                    castle20Exists = true;
                    index2 = i;
                }
                if(x == 3 && y == 0){
                    castle30Exists = true;
                }
            }
            if(!castle30Exists && castle20Exists){
                newMovementsX.remove(index2);
                newMovementsY.remove(index2);
            }
        }
    }

    /**
     * Creates a fake movement on the copied state
     *
     * @param state the copied state of the game
     * @param row   the row position of the selected piece
     * @param col   the column position of the selected piece
     */
    public void makeTempMovement(ChessState state, int row, int col) {
        // create the temp piece with the selected position (tempRow, tempCol)
        Piece tempPiece = state.getPiece(tempRow, tempCol);

        // if they are moving a king determine who's king (white/black) and
        // update the position of the king so when it checks for if the king
        // is in check it knows the new position
        if (tempPiece.getPieceType() == Piece.PieceType.KING) {
            if (tempPiece.getPieceColor() == Piece.ColorType.WHITE) {
                state.setKingWhite(row, col);
            } else if (tempPiece.getPieceColor() == Piece.ColorType.BLACK) {
                state.setKingBlack(row, col);
            }
        }
        // make the location the piece is moving to become the selected piece
        state.setPiece(row, col, tempPiece);

        // make the selected piece become empty since the piece has moved
        state.setPiece(tempRow, tempCol, state.emptyPiece);
    }

    /**
     * Move the piece that was selected to the new position
     * that the player wants to move to
     *
     * @param state the current state of the game
     * @param row   the row of the position the player is moving to
     * @param col   the column of the position the player is moving to
     * @param color the color of the piece they selected previously
     * @return tells weather the move was valid and happened
     */
    public boolean setMovement(ChessState state, int row, int col, Piece.ColorType color) {
        // if they selected a dot/ring then move
        if (state.getDrawing(row, col) == 2 || state.getDrawing(row, col) == 4) {

            //adds captured piece to captured pieces array t
            if (state.getPiece(row, col).getPieceType() != Piece.PieceType.EMPTY) {
                state.addWhiteCapturedPiece(state.getPiece(row, col));
            }
            for (Piece p : state.getWhiteCapturedPieces()) {
                Log.d("Testing", p.getPieceType().toString());
            }
            Piece tempPiece = state.getPiece(tempRow, tempCol);
            Piece castlingTempPiece = state.getPiece(row, col);

            //very specific castling case- the selected piece is a king and moving two squares
            if(state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.KING && (Math.abs(row-tempRow) == 2)){
                // for each sub case- this takes care of moving the rook, the king then moves normally
                if(tempRow == 4 && tempCol == 7 && row == 6 && col == 7){
                    state.setPiece(5,7, state.getPiece(7,7));
                    state.setPiece(7,7,state.emptyPiece);
                }
                else if(tempRow == 4 && tempCol == 7 && row == 2 && col == 7){
                    state.setPiece(3,7, state.getPiece(0,7));
                    state.setPiece(0,7,state.emptyPiece);
                }
                else if(tempRow == 4 && tempCol == 0 && row == 6 && col == 0){
                    state.setPiece(5,0, state.getPiece(7,0));
                    state.setPiece(7,0,state.emptyPiece);
                }
                else if(tempRow == 4 && tempCol == 0 && row == 2 && col == 0){
                    state.setPiece(3,0, state.getPiece(0,0));
                    state.setPiece(0,0,state.emptyPiece);
                }

            }


            // change the location of the king to be at the new square if it is going to be moved
            if (tempPiece.getPieceType() == Piece.PieceType.KING) {
                if (tempPiece.getPieceColor() == Piece.ColorType.WHITE) {
                    state.setKingWhite(row, col);
                } else if (tempPiece.getPieceColor() == Piece.ColorType.BLACK) {
                    state.setKingBlack(row, col);
                }
            }

            // set the new position to be the piece they originally selected
            state.setPiece(row, col, tempPiece);

            /////////////////////////////////////////////////////////////////////////
            // No idea what's going, commenting out for now since it is hard coded //
            /////////////////////////////////////////////////////////////////////////
            //boolean isCapture = state.getPiece(row,col).getPieceType() != Piece.PieceType.EMPTY;
            //ChessHumanPlayer chp = (ChessHumanPlayer) players[0];
            //state.setPiece(row,col,checkPromotion(state.getPiece(tempRow,tempCol),col));
            //TODO put display moves log here
            //chp.displayMovesLog(row,col,tempRow,state,isCapture);

            // change the piece at the selection to be an empty piece
            state.setPiece(tempRow, tempCol, state.emptyPiece);

            // remove all highlights
            state.removeHighlight();

            // reset temp values so only selections may occur
            tempRow = -1;
            tempCol = -1;

            // remove all the circles after moving
            state.removeCircle();
            state.setKingInCheck(false);
            if (color == Piece.ColorType.BLACK) {
                if (checkForCheck(state, Piece.ColorType.WHITE, color)) {
                    state.setHighlightCheck(state.getKingWhite().getX(), state.getKingWhite().getY());
                    state.setKingInCheck(true);
                }
            } else if (color == Piece.ColorType.WHITE) {
                if (checkForCheck(state, Piece.ColorType.BLACK, color)) {
                    state.setHighlightCheck(state.getKingBlack().getX(), state.getKingBlack().getY());
                    state.setKingInCheck(true);
                }
            }
            return true;
        } else {
            // if they didn't select a dot they don't move
            return false;
        }
    }
}
