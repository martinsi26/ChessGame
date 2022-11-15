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

    // boolean indicating if a king is in check
    private boolean isCheck = false;

    // x and y values of the locations that put the king in check
    private ArrayList<Integer> xKing = new ArrayList<>();
    private ArrayList<Integer> yKing = new ArrayList<>();

    // list of Piece that cause a king to be in check
    private ArrayList<Piece> piecesCauseCheck = new ArrayList<>();

    // list of all the x and y positions that block a king from
    // being in check or removes the piece that causes a king
    // to be in check
    private ArrayList<Integer> xBlock = new ArrayList<>();
    private ArrayList<Integer> yBlock = new ArrayList<>();

    // all of the pieces that are in blocking positions
    private ArrayList<Piece> pieceBlock = new ArrayList<>();
    // all of the positions on the board that block the king from an attacker
    private ArrayList<Piece> blockPosition = new ArrayList<>();

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

        if(action instanceof ChessSelectAction) {
            ChessSelectAction select = (ChessSelectAction) action;
            int row = select.getRow();
            int col = select.getCol();
            // remove the highlights if there are any previous ones
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (state.getDrawing(i, j) == 1) {
                        state.removeHighlight();
                        state.removeDot();
                    }
                }
            }
            // remove the red highlight from being in check
            state.removeHighlightCheck();
            // highlight the piece they clicked
            state.setHighlight(row, col);
            // save temps for the row and col for movement later
            tempRow = row;
            tempCol = col;

            Piece p = state.getPiece(row, col);
            if(p.getPieceColor() == Piece.ColorType.WHITE) {
                findMovement(state, row, col, Piece.ColorType.WHITE);
            } else if (p.getPieceColor() == Piece.ColorType.BLACK) {
                findMovement(state, row, col, Piece.ColorType.BLACK);
            }

            // return true to skip changing turns
            return true;
        } else if(action instanceof ChessMoveAction) {
            ChessMoveAction move = (ChessMoveAction) action;
            int row = move.getRow();
            int col = move.getCol();
            // if they have no selected piece movement shouldn't occur
            if (tempRow == -1 || tempCol == -1) {
                return false;
            }
            Piece tempP = state.getPiece(tempRow, tempCol);
            if(tempP.getPieceColor() == Piece.ColorType.WHITE) {
                if (!setMovement(state, row, col, Piece.ColorType.WHITE)) {
                    state.removeHighlight();
                    state.removeDot();
                    return false;
                }
            } else if (tempP.getPieceColor() == Piece.ColorType.BLACK) {
                if (!setMovement(state, row, col, Piece.ColorType.BLACK)) {
                    state.removeHighlight();
                    state.removeDot();
                    return false;
                }
            }
            // make sure all highlights and dots are already removed
            state.removeDot();

            // make it the other player's turn
            state.setWhoseMove(1 - whoseMove);

            // return true, indicating the it was a legal move
            return true;
        }
        // return true, indicating the it was a legal move
        return false;
    }

    /**
     * Generates all the movements that can be made by a player depending
     * on what type of piece they have selected
     *
     * @param state the current state of the game
     * @param row the row of the piece they have selected to move
     * @param col the column of the piece they have selected to move
     * @param color the color of the piece they have selected to move
     */
    public void findMovement(ChessState state, int row, int col, Piece.ColorType color) {
        // current piece that is selected for movement
        Piece p = state.getPiece(row, col);
        // if the king is not in check then movement occurs normally

        ////////////////////////////////////////////////////////////
        // need to add method that determines if a piece is moved //
        // that it doesn't cause the king to be in check          //
        ////////////////////////////////////////////////////////////
        if(color == Piece.ColorType.WHITE) {
            movingChecksSelf(state, Piece.ColorType.BLACK, color);
        } else if (color == Piece.ColorType.BLACK) {
            movingChecksSelf(state, Piece.ColorType.WHITE, color);
        }
        // remove all dots on board made from movingChecksSelf
        state.removeDot();
        // have to reset the highlight because the enemy might have drawn a dot
        // on the highlight in the movingChecksSelf method
        state.setHighlight(row, col);

        if (!getCheck()) {
            if (p.getPieceType() == Piece.PieceType.PAWN) {
                // generate all pawn movements
                Pawn pawn = new Pawn(p, state, color);
                // make circles on all movements
                state.setCircles(pawn.getX(), pawn.getY());
                for(int i = 0; i < pieceBlock.size(); i++) {
                    // if the piece is currently at a position that blocks check and is the
                    // only piece there
                    if(p.equals(pieceBlock.get(i))) {
                        state.removeDot();
                    }
                }
            } else if (p.getPieceType() == Piece.PieceType.KNIGHT) {
                Knight knight = new Knight(p, state, color);
                state.setCircles(knight.getX(), knight.getY());
                for(int i = 0; i < pieceBlock.size(); i++) {
                    if(p.equals(pieceBlock.get(i))) {
                        state.removeDot();
                    }
                }
            } else if (p.getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(p, state, color);
                state.setCircles(bishop.getX(), bishop.getY());
                for(int i = 0; i < pieceBlock.size(); i++) {
                    if(p.equals(pieceBlock.get(i))) {
                        for(int j = 0; j < bishop.getX().size(); j++) {
                            for(int k = 0; k < blockPosition.size(); k++) {
                                if (bishop.getX().get(j) != blockPosition.get(k).getX()
                                        || bishop.getY().get(j) != blockPosition.get(k).getY()) {
                                    state.removeDot(bishop.getX().get(j), bishop.getY().get(j));
                                }
                            }
                        }
                    }
                }
            } else if (p.getPieceType() == Piece.PieceType.ROOK) {
                Rook rook = new Rook(p, state, color);
                state.setCircles(rook.getX(), rook.getY());
                for(int i = 0; i < pieceBlock.size(); i++) {
                    if(p.equals(pieceBlock.get(i))) {
                        for(int j = 0; j < rook.getX().size(); j++) {
                            for(int k = 0; k < blockPosition.size(); k++) {
                                if (rook.getX().get(j) != blockPosition.get(k).getX()
                                        || rook.getY().get(j) != blockPosition.get(k).getY()) {
                                    state.removeDot(rook.getX().get(j), rook.getY().get(j));
                                }
                            }
                        }
                    }
                }
            } else if (p.getPieceType() == Piece.PieceType.QUEEN) {
                Queen queen = new Queen(p, state, color);
                state.setCircles(queen.getX(), queen.getY());
                for(int i = 0; i < pieceBlock.size(); i++) {
                    if(p.equals(pieceBlock.get(i))) {
                        for(int j = 0; j < queen.getX().size(); j++) {
                            for(int k = 0; k < blockPosition.size(); k++) {
                                if (queen.getX().get(j) != blockPosition.get(k).getX()
                                        || queen.getY().get(j) != blockPosition.get(k).getY()) {
                                    state.removeDot(queen.getX().get(j), queen.getY().get(j));
                                }
                            }
                        }
                    }
                }
            } else if (p.getPieceType() == Piece.PieceType.KING) {
                checkMoveToCheckKing(state, color);
                King king = new King(p, state, color);
                state.setCircles(king.getX(), king.getY());
                // remove all of the locations that cause the king to be in check (xKing, yKing)
                for (int i = 0; i < xKing.size(); i++) {
                    state.removeDot(xKing.get(i), yKing.get(i));
                }
            }
            for(int i = 0; i < blockPosition.size(); i++) {
                blockPosition.remove(i);
            }
        } else if (getCheck()) {
            // locates the positions that can block check
            findCheckBlock(color, state);
            // moves for being in check
            findMovementsWhenCheck(state, row, col, color);

        }
    }

    // needs updates

    public void movingChecksSelf(ChessState state, Piece.ColorType enemyColor, Piece.ColorType teamColor) {
        boolean left = false;
        boolean right = false;
        boolean up = false;
        boolean down = false;

        for(int i = 0; i < pieceBlock.size(); i++) {
            pieceBlock.remove(i);
        }
        Piece king = null;
        if(teamColor == Piece.ColorType.WHITE) {
            king = state.getKingWhite();
        } else if (teamColor == Piece.ColorType.BLACK) {
            king = state.getKingBlack();
        }
        if(king == null) {
            return;
        }
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = state.getPiece(row, col);
                if (piece.getPieceColor() == enemyColor) {
                    if (piece.getPieceType() == Piece.PieceType.BISHOP) {
                        left = false;
                        right = false;
                        up = false;
                        down = false;
                        Bishop bishop = new Bishop(piece, state, enemyColor);
                        state.setCircles(bishop.getXAttackThrough(), bishop.getYAttackThrough());
                        if (state.getDrawing(king.getX(), king.getY()) == 2) {
                            if (piece.getX() > king.getX()) {
                                left = true;
                            } else if (piece.getX() < king.getX()) {
                                right = true;
                            }
                            if (piece.getY() > king.getY()) {
                                up = true;
                            } else if (piece.getY() < king.getY()) {
                                down = true;
                            }

                            for (int i = 0; i < bishop.getXAttackThrough().size(); i++) {
                                Piece p = state.getPiece(bishop.getXAttackThrough().get(i), bishop.getYAttackThrough().get(i));
                                blockPosition.add(piece);
                                if (left && down) {
                                    if (p.getX() < piece.getX()) {
                                        if (p.getY() > piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                } else if (left && up) {
                                    if (p.getX() < piece.getX()) {
                                        if (p.getY() < piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                } else if (right && down) {
                                    if (p.getX() > piece.getX()) {
                                        if (p.getY() > piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                } else if (right && up) {
                                    if (p.getX() > piece.getX()) {
                                        if (p.getY() < piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < blockPosition.size(); i++) {
                            if (blockPosition.get(i).getPieceColor() == teamColor) {
                                pieceBlock.add(blockPosition.get(i));
                            }
                        }
                        if (pieceBlock.size() > 1) {
                            for (int j = 0; j < pieceBlock.size(); j++) {
                                pieceBlock.remove(j);
                            }
                        }
                    } else if (piece.getPieceType() == Piece.PieceType.ROOK) {
                        left = false;
                        right = false;
                        up = false;
                        down = false;
                        Rook rook = new Rook(piece, state, enemyColor);
                        state.setCircles(rook.getXAttackThrough(), rook.getYAttackThrough());
                        for (int i = 0; i < rook.getXAttackThrough().size(); i++) {
                            Piece p = state.getPiece(rook.getXAttackThrough().get(i), rook.getYAttackThrough().get(i));
                            if (state.getDrawing(king.getX(), king.getY()) == 2) {
                                blockPosition.add(piece);
                                if (piece.getX() > king.getX()) {
                                    right = true;
                                } else if (piece.getX() < king.getX()) {
                                    left = true;
                                }
                                if (piece.getY() > king.getY()) {
                                    down = true;
                                } else if (piece.getY() < king.getY()) {
                                    up = true;
                                }
                                if (left) {
                                    if (p.getX() < piece.getX()) {
                                        blockPosition.add(p);
                                    }
                                } else if (up) {
                                    if (p.getY() < piece.getY()) {
                                        blockPosition.add(p);
                                    }
                                } else if (right) {
                                    if (p.getX() > piece.getX()) {
                                        blockPosition.add(p);
                                    }
                                } else if (down) {
                                    if (p.getY() > piece.getY()) {
                                        blockPosition.add(p);
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < blockPosition.size(); i++) {
                            if (blockPosition.get(i).getPieceColor() == teamColor) {
                                pieceBlock.add(blockPosition.get(i));
                            }
                        }
                        if (pieceBlock.size() > 1) {
                            for (int j = 0; j < pieceBlock.size(); j++) {
                                pieceBlock.remove(j);
                            }
                        }
                    } else if (piece.getPieceType() == Piece.PieceType.QUEEN) {
                        left = false;
                        right = false;
                        up = false;
                        down = false;
                        Queen queen = new Queen(piece, state, enemyColor);
                        state.setCircles(queen.getXAttackThrough(), queen.getYAttackThrough());
                        for (int i = 0; i < queen.getXAttackThrough().size(); i++) {
                            Piece p = state.getPiece(queen.getXAttackThrough().get(i), queen.getYAttackThrough().get(i));
                            if (state.getDrawing(king.getX(), king.getY()) == 2) {
                                blockPosition.add(piece);
                                if (piece.getX() > king.getX()) {
                                    right = true;
                                } else if (piece.getX() < king.getX()) {
                                    left = true;
                                }
                                if (piece.getY() > king.getY()) {
                                    down = true;
                                } else if (piece.getY() < king.getY()) {
                                    up = true;
                                }
                                if (left && down) {
                                    if (p.getX() < piece.getX()) {
                                        if (p.getY() > piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                } else if (left && up) {
                                    if (p.getX() < piece.getX()) {
                                        if (p.getY() < piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                } else if (right && down) {
                                    if (p.getX() > piece.getX()) {
                                        if (p.getY() > piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                } else if (right && up) {
                                    if (p.getX() > piece.getX()) {
                                        if(p.getY() < piece.getY()) {
                                            blockPosition.add(p);
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < blockPosition.size(); i++) {
                            if (blockPosition.get(i).getPieceColor() == teamColor) {
                                pieceBlock.add(blockPosition.get(i));
                            }
                        }
                        if (pieceBlock.size() > 1) {
                            for (int j = 0; j < pieceBlock.size(); j++) {
                                pieceBlock.remove(j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds all of the positions that can be moved to in order to
     * block whatever piece is putting your kind in check
     *
     * @param state the current state of the game
     * @param color the color of the piece that is checking your king
     */
    public void findCheckBlock(Piece.ColorType color, ChessState state) {
        // make sure the arraylists are empty before filling them up
        for(int i = 0; i < xBlock.size(); i++) {
            xBlock.remove(i);
            yBlock.remove(i);
        }
        // if there are multiple pieces causing your king to be in check
        // then you cannot block the check instead the king must move
        if (piecesCauseCheck.size() == 1) {
            // generate the location the piece that is putting you in check
            // is at to make that a valid spot to move in order to remove
            // the check
            xBlock.add(piecesCauseCheck.get(0).getX());
            yBlock.add(piecesCauseCheck.get(0).getY());
            // if the piece putting you in check is a knight there is not position
            // you can move to block it
            if (piecesCauseCheck.get(0).getPieceType() == Piece.PieceType.KNIGHT) {
                return;
            }
            // add all of the positions that can block the attack depending on what
            // type of piece is attacking your king
            if (piecesCauseCheck.get(0).getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(piecesCauseCheck.get(0), state, color);
                for (int j = 0; j < bishop.getX().size(); j++) {
                    xBlock.add(bishop.getX().get(j));
                    yBlock.add(bishop.getY().get(j));
                }
            } else if (piecesCauseCheck.get(0).getPieceType() == Piece.PieceType.ROOK) {
                Rook rook = new Rook(piecesCauseCheck.get(0), state, color);
                for (int j = 0; j < rook.getX().size(); j++) {
                    xBlock.add(rook.getX().get(j));
                    yBlock.add(rook.getY().get(j));
                }
            } else if (piecesCauseCheck.get(0).getPieceType() == Piece.PieceType.QUEEN) {
                Queen queen = new Queen(piecesCauseCheck.get(0), state, color);
                for (int j = 0; j < queen.getX().size(); j++) {
                    xBlock.add(queen.getX().get(j));
                    yBlock.add(queen.getY().get(j));
                }
            }
        }
    }

    /**
     * Generates all of the movements that can be made to get
     * your king out of check
     *
     * @param state the current state of the game
     * @param row the row of the piece they have selected to move
     * @param col the column of the piece they have selected to move
     * @param color the color of the piece they have selected to move
     */
    public void findMovementsWhenCheck(ChessState state, int row, int col, Piece.ColorType color) {
        state.removeDot();
        // current piece that is selected
        Piece p = state.getPiece(row, col);
        // depending on what type of piece that is selected for movement
        // different locations may appear
        if (p.getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(p, state, color);
            // allow this piece to go to any position that is valid for a pawn
            // and that position must be in the xBlock and yBlock arraylist
            // since it indicates that location will block your king so it
            // is no longer in check
            for(int i = 0; i < pawn.getX().size(); i++) {
                for(int j = 0; j < xBlock.size(); j++) {
                    if (pawn.getX().get(i).equals(xBlock.get(j)) && pawn.getY().get(i).equals(yBlock.get(j))) {
                        state.setCircles(pawn.getX(), pawn.getY());
                    }
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(p, state, color);
            for(int i = 0; i < knight.getX().size(); i++) {
                for(int j = 0; j < xBlock.size(); j++) {
                    if (knight.getX().get(i).equals(xBlock.get(j)) && knight.getY().get(i).equals(yBlock.get(j))) {
                        state.setCircles(knight.getX(), knight.getY());
                    }
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(p, state, color);
            for(int i = 0; i < bishop.getX().size(); i++) {
                for(int j = 0; j < xBlock.size(); j++) {
                    if (bishop.getX().get(i).equals(xBlock.get(j)) && bishop.getY().get(i).equals(yBlock.get(j))) {
                        state.setCircles(bishop.getX(), bishop.getY());
                    }
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(p, state, color);
            for(int i = 0; i < rook.getX().size(); i++) {
                for(int j = 0; j < xBlock.size(); j++) {
                    if (rook.getX().get(i).equals(xBlock.get(j)) && rook.getY().get(i).equals(yBlock.get(j))) {
                        state.setCircles(rook.getX(), rook.getY());
                    }
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(p, state, color);
            for(int i = 0; i < queen.getX().size(); i++) {
                for(int j = 0; j < xBlock.size(); j++) {
                    if (queen.getX().get(i).equals(xBlock.get(j)) && queen.getY().get(i).equals(yBlock.get(j))) {
                        state.setCircles(queen.getX(), queen.getY());
                    }
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.KING) {
            // if the piece is a king then generate all of the valid
            // movements the king can make to get out of check
            checkMoveToCheckKing(state, color);
            King king = new King(p, state, color);
            state.setCircles(king.getX(), king.getY());
            state.setHighlight(row, col);
            // remove all of the locations that cause the king to be in check (xKing, yKing)
            for (int i = 0; i < xKing.size(); i++) {
                state.removeDot(xKing.get(i), yKing.get(i));
            }
        }
    }

    /**
     * Move the piece that was selected to the new position
     * that the player wants to move to
     *
     * @param state the current state of the game
     * @param row the row of the position the player is moving to
     * @param col the column of the position the player is moving to
     * @param color the color of the piece they selected previously
     *
     * @return tells weather the move was valid and happened
     */
    public boolean setMovement(ChessState state, int row, int col, Piece.ColorType color) {
        // if they selected a dot then move
        if (state.getDrawing(row, col) == 2) {


            //adds captured piece to captured pieces array t
            if(state.getPiece(row, col).getPieceType() != Piece.PieceType.EMPTY){
                state.addWhiteCapturedPiece(state.getPiece(row, col));
            }

            for(Piece p : state.getWhiteCapturedPieces()){
                Log.d("Testing", p.getPieceType().toString());
            }

            Piece tempPiece = state.getPiece(tempRow,tempCol);
            // change the location of the king to be at the new square if it is going to be moved
            if (tempPiece.getPieceType() == Piece.PieceType.KING) {
                if (tempPiece.getPieceColor() == Piece.ColorType.WHITE) {
                    state.setKingWhite(row, col);
                } else if (tempPiece.getPieceColor() == Piece.ColorType.BLACK) {
                    state.setKingBlack(row, col);
                }
            }
            // set the new position to be the piece they originally selected
//            state.setPiece(row, col, tempPiece);
            boolean isCapture = state.getPiece(row,col).getPieceType() != Piece.PieceType.EMPTY;
            ChessHumanPlayer chp = (ChessHumanPlayer) players[0];
            state.setPiece(row,col,checkPromotion(state.getPiece(tempRow,tempCol),col));
            //TODO put display moves log here
            chp.displayMovesLog(row,col,tempRow,state,isCapture);

            // change the piece at the selection to be an empty piece
            state.setPiece(tempRow, tempCol, state.emptyPiece);

            setCheck(false);

            // remove all highlights
            state.removeHighlight();

            // reset temp values so only selections may occur
            tempRow = -1;
            tempCol = -1;

            // remove all the circles after moving
            state.removeDot();

            // check if the enemy king is in check after moving
            if (checkForCheckMove(state, row, col, color)) {
                setCheck(true);
            } else {
                setCheck(false);
            }

            return true;
        } else {
            // if they didn't select a dot they don't move
            return false;
        }
    }

    public Piece checkPromotion(Piece piece, int col){
        if(piece.getPieceType() != Piece.PieceType.PAWN){return piece;}

        if(piece.getPieceColor() == Piece.ColorType.WHITE && col == 0){
            return new Piece(Piece.PieceType.QUEEN, Piece.ColorType.WHITE, 0, piece.getY());
        }else if(piece.getPieceColor() == Piece.ColorType.BLACK && col == 7) {
            return new Piece(Piece.PieceType.QUEEN, Piece.ColorType.BLACK, 7, piece.getY());
        }
        return piece;
    }

    /**
     * Indicates that a king is currently in check
     *
     * @param b the boolean value that says if a king is in check
     */
    public void setCheck(boolean b) {
        isCheck = b;
    }

    /**
     * Helper method for finding if a king is in check
     *
     * @return tells if a king is in check or not
     */
    public boolean getCheck() {
        return isCheck;
    }

    /**
     * Finds out if the most recent movement caused the opponents king to
     * be in check
     *
     * @param state the current state of the game
     * @param row the row of the piece they have moved
     * @param col the column of the piece they have moved
     * @param color the color of the piece they have moved
     *
     * @return indicates if a king was put into check
     */
    public boolean checkForCheckMove(ChessState state, int row, int col, Piece.ColorType color) {
        // remove all items in piecesCauseCheck so you can add new ones
        for (int i = 0; i < piecesCauseCheck.size(); i++) {
            piecesCauseCheck.remove(i);
        }
        // current piece that was moved
        Piece p = state.getPiece(row, col);
        // current king piece for black/white
        Piece king = null;
        if (color == Piece.ColorType.WHITE) {
            king = state.getKingBlack();
        } else if (color == Piece.ColorType.BLACK) {
            king = state.getKingWhite();
        }
        if (king == null) {
            return false;
        }
        // if the piece that was moved was a king then you cannot put the
        // enemy king in check
        if (p.getPieceType() == Piece.PieceType.KING) {
            return false;
        }
        // for each PieceType generate movements and identify if they put
        // the king in check. If they do then add it to the arraylist
        if (p.getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(p, state, color);
            for (int i = 0; i < pawn.getXAttack().size(); i++) {
                if (pawn.getXAttack().get(i) == king.getX() && pawn.getYAttack().get(i) == king.getY()) {
                    piecesCauseCheck.add(p);
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(p, state, color);
            state.setCircles(knight.getX(), knight.getY());
            for (int i = 0; i < knight.getX().size(); i++) {
                if (knight.getX().get(i) == king.getX() && knight.getY().get(i) == king.getY()) {
                    piecesCauseCheck.add(p);
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(p, state, color);
            state.setCircles(bishop.getX(), bishop.getY());
            for (int i = 0; i < bishop.getX().size(); i++) {
                if (bishop.getX().get(i) == king.getX() && bishop.getY().get(i) == king.getY()) {
                    piecesCauseCheck.add(p);
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(p, state, color);
            state.setCircles(rook.getX(), rook.getY());
            for (int i = 0; i < rook.getX().size(); i++) {
                if (rook.getX().get(i) == king.getX() && rook.getY().get(i) == king.getY()) {
                    piecesCauseCheck.add(p);
                }
            }
        } else if (p.getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(p, state, color);
            state.setCircles(queen.getX(), queen.getY());
            for (int i = 0; i < queen.getX().size(); i++) {
                if (queen.getX().get(i) == king.getX() && queen.getY().get(i) == king.getY()) {
                    piecesCauseCheck.add(p);
                }
            }
        }
        // if at least one piece is causing the king to be in check then highlight
        // the kings square indicating it is in check and return true
        if (piecesCauseCheck.size() > 0) {
            state.setHighlightCheck(king.getX(), king.getY());
            return true;
        }
        return false;
    }

    /**
     * Finds all of the locations the king can move to
     *
     * @param state the current state of the game
     * @param colorType the color of the king
     */
    public void checkMoveToCheckKing(ChessState state, Piece.ColorType colorType) {
        // if it is white's turn
        if (colorType == Piece.ColorType.WHITE) {
            // generate all of black's piece movements
            generateMovementEnemy(state, Piece.ColorType.BLACK);
            // generate all of the movements the king can make while taking into
            // account all positions that cause the king to be in check
            kingMovement(state, state.getKingWhite().getX(), state.getKingWhite().getY());
            state.removeDot();
        } else if (colorType == Piece.ColorType.BLACK) {
            // generate all of white's piece movements
            generateMovementEnemy(state, Piece.ColorType.WHITE);
            // find out all of the locations that cause the king to be in
            // check
            kingMovement(state, state.getKingBlack().getX(), state.getKingBlack().getY());
            state.removeDot();
        }
    }

    /**
     * Generates all of the movements of the enemy pieces
     *
     * @param state the current state of the game
     * @param colorType the color of the opponents pieces
     */
    public void generateMovementEnemy(ChessState state, Piece.ColorType colorType) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = state.getPiece(row, col);
                if (piece.getPieceColor() == colorType) {
                    if (piece.getPieceType() == Piece.PieceType.PAWN) {
                        Pawn pawn = new Pawn(piece, state, colorType);
                        state.setCircles(pawn.getXAttack(), pawn.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.KNIGHT) {
                        Knight knight = new Knight(piece, state, colorType);
                        state.setCircles(knight.getXAttack(), knight.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.BISHOP) {
                        Bishop bishop = new Bishop(piece, state, colorType);
                        state.setCircles(bishop.getXAttack(), bishop.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.ROOK) {
                        Rook rook = new Rook(piece, state, colorType);
                        state.setCircles(rook.getXAttack(), rook.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.QUEEN) {
                        Queen queen = new Queen(piece, state, colorType);
                        state.setCircles(queen.getXAttack(), queen.getYAttack());
                    } else if (piece.getPieceType() == Piece.PieceType.KING) {
                        King king = new King(piece, state, colorType);
                        state.setCircles(king.getXAttack(), king.getYAttack());
                    }
                }
            }
        }
    }

    /**
     * Generates all of the locations that would cause the
     * king to be in check and add them to the arraylists
     *
     * @param state the current state of the game
     * @param x the x position of the king
     * @param y the y position of the king
     */
    public void kingMovement(ChessState state, int x, int y) {
        // remove all of the values that are currently set
        // before setting the new values
        for(int i = 0; i < xKing.size(); i++) {
            xKing.remove(i);
            yKing.remove(i);
        }
        if (x > 0) {
            if (state.getDrawing(x - 1, y) == 2) {
                xKing.add(x - 1);
                yKing.add(y);
            }
        }
        if (x < 7) {
            if (state.getDrawing(x + 1, y) == 2) {
                xKing.add(x + 1);
                yKing.add(y);
            }
        }
        if (y > 0) {
            if (state.getDrawing(x, y - 1) == 2) {
                xKing.add(x);
                yKing.add(y - 1);
            }
        }
        if (y < 7) {
            if (state.getDrawing(x, y + 1) == 2) {
                xKing.add(x);
                yKing.add(y + 1);
            }
        }
        if (x > 0 && y > 0) {
            if (state.getDrawing(x - 1, y - 1) == 2) {
                xKing.add(x - 1);
                yKing.add(y - 1);
            }
        }
        if (x > 0 && y < 7) {
            if (state.getDrawing(x - 1, y + 1) == 2) {
                xKing.add(x - 1);
                yKing.add(y + 1);
            }
        }
        if (x < 7 && y > 0) {
            if (state.getDrawing(x + 1, y - 1) == 2) {
                xKing.add(x + 1);
                yKing.add(y - 1);
            }
        }
        if (x < 7 && y < 7) {
            if (state.getDrawing(x + 1, y + 1) == 2) {
                xKing.add(x + 1);
                yKing.add(y + 1);
            }
        }
    }
}
