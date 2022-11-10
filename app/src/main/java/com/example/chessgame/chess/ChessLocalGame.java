package com.example.chessgame.chess;

import android.widget.TextView;

import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;
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

public class ChessLocalGame extends LocalGame {

    //Tag for logging
    private static final String TAG = "ChessLocalGame";

    // the number of moves that have been played so far, used to
    // determine whether the game is over
    protected int moveCount;

    private int tempRow;
    private int tempCol;

    private boolean isCheck = false;

    private ArrayList<Integer> xKing = new ArrayList<>();
    private ArrayList<Integer> yKing = new ArrayList<>();

    private ArrayList<Piece> piecesCauseCheck = new ArrayList<>();

    private ArrayList<Integer> xBlock = new ArrayList<>();
    private ArrayList<Integer> yBlock = new ArrayList<>();

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

            ///////////////////////////////////////////////////
            // generate all possible moves of piece selected
            // remove all moves that cause check
            // then find remaining movements to display to user
            ///////////////////////////////////////////////////

            if(state.getPiece(row, col).getPieceColor() == Piece.ColorType.WHITE) {
                findMovement(state, row, col, Piece.ColorType.WHITE);
            } else if (state.getPiece(row, col).getPieceColor() == Piece.ColorType.BLACK) {
                findMovement(state, row, col, Piece.ColorType.BLACK);
            }

            // return true to skip changing turns
            return true;
        } else if(action instanceof ChessMoveAction) {
            ChessMoveAction move = (ChessMoveAction) action;
            int row = move.getRow();
            int col = move.getCol();
            // if they have no selected a piece movement shouldn't occur
            if (tempRow == -1 || tempCol == -1) {
                return false;
            }
            if(state.getPiece(tempRow, tempCol).getPieceColor() == Piece.ColorType.WHITE) {
                if (!setMovement(state, row, col, Piece.ColorType.WHITE)) {
                    state.removeHighlight();
                    state.removeDot();
                    return false;
                }
            } else if (state.getPiece(tempRow, tempCol).getPieceColor() == Piece.ColorType.BLACK) {
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

            // bump the move count
            moveCount++;

            // return true, indicating the it was a legal move
            return true;
        }
        // return true, indicating the it was a legal move
        return false;
    }

    public void findMovement(ChessState state, int row, int col, Piece.ColorType color) {
        if (!getCheck()) {
            if (state.getPiece(row, col).getPieceType() == Piece.PieceType.PAWN) {
                Pawn pawn = new Pawn(state.getPiece(row, col), state, color);
                state.setCircles(pawn.getX(), pawn.getY());
            } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KNIGHT) {
                Knight knight = new Knight(state.getPiece(row, col), state, color);
                state.setCircles(knight.getX(), knight.getY());
            } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(state.getPiece(row, col), state, color);
                state.setCircles(bishop.getX(), bishop.getY());
            } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.ROOK) {
                Rook rook = new Rook(state.getPiece(row, col), state, color);
                state.setCircles(rook.getX(), rook.getY());
            } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.QUEEN) {
                Queen queen = new Queen(state.getPiece(row, col), state, color);
                state.setCircles(queen.getX(), queen.getY());
            } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KING) {
                checkMoveToCheckKing(state, color);
                King king = new King(state.getPiece(row, col), state, color);
                state.setCircles(king.getX(), king.getY());
                for (int i = 0; i < xKing.size(); i++) {
                    state.removeDot(xKing.get(i), yKing.get(i));
                }
            }
        } else if (getCheck()) {
            // will need to create a method that allows for only legal

            // create a method that locates the positions that can block check
            findCheckBlock(color, state);
            // moves for being in check
            findMovementsWhenCheck(state, row, col, color);

        }
    }

    public void findCheckBlock(Piece.ColorType color, ChessState state) {
        for(int i = 0; i < piecesCauseCheck.size(); i++) {
            xBlock.add(piecesCauseCheck.get(i).getX());
            yBlock.add(piecesCauseCheck.get(i).getY());
            if (piecesCauseCheck.get(i).getPieceType() == Piece.PieceType.KNIGHT) {
                break;
            } else if (piecesCauseCheck.get(i).getPieceType() == Piece.PieceType.BISHOP) {
                Bishop bishop = new Bishop(piecesCauseCheck.get(i), state, color);
                for(int j = 0; j < bishop.getX().size(); j++) {
                    if(!bishop.getX().get(j).equals(xKing.get(j))) {
                        xBlock.add(bishop.getX().get(j));
                    }
                }
            } else if (piecesCauseCheck.get(i).getPieceType() == Piece.PieceType.ROOK) {
                Rook rook = new Rook(piecesCauseCheck.get(i), state, color);
            } else if (piecesCauseCheck.get(i).getPieceType() == Piece.PieceType.QUEEN) {
                Queen queen = new Queen(piecesCauseCheck.get(i), state, color);
            } else if (piecesCauseCheck.get(i).getPieceType() == Piece.PieceType.KING) {
                King king = new King(piecesCauseCheck.get(i), state, color);
            }
        }
    }

    public void findMovementsWhenCheck(ChessState state, int row, int col, Piece.ColorType color) {
        if (state.getPiece(row, col).getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(state.getPiece(row, col), state, color);
            state.setCircles(pawn.getX(), pawn.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(state.getPiece(row, col), state, color);
            state.setCircles(knight.getX(), knight.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(state.getPiece(row, col), state, color);
            state.setCircles(bishop.getX(), bishop.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(state.getPiece(row, col), state, color);
            state.setCircles(rook.getX(), rook.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(state.getPiece(row, col), state, color);
            state.setCircles(queen.getX(), queen.getY());
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KING) {
            checkMoveToCheckKing(state, color);
            King king = new King(state.getPiece(row, col), state, color);
            state.setCircles(king.getX(), king.getY());
            state.setHighlight(row, col);
            for (int i = 0; i < xKing.size(); i++) {
                state.removeDot(xKing.get(i), yKing.get(i));
            }
        }
    }

    public boolean setMovement(ChessState state, int row, int col, Piece.ColorType color) {
        // if they selected a dot then move
        if (state.getDrawing(row, col) == 2) {

            // change the location of the king to be at the new square if it is going to be moved
            if (state.getPiece(tempRow, tempCol).getPieceType() == Piece.PieceType.KING) {
                if (state.getPiece(tempRow, tempCol).getPieceColor() == Piece.ColorType.WHITE) {
                    state.setKingWhite(row, col);
                } else if (state.getPiece(tempRow, tempCol).getPieceColor() == Piece.ColorType.BLACK) {
                    state.setKingBlack(row, col);
                }
            }

            // set the new position to be the piece they originally selected
            state.setPiece(row, col, state.getPiece(tempRow, tempCol));

            // change the piece at the selection to be an empty piece
            state.setPiece(tempRow, tempCol, state.emptyPiece);

            // remove all highlights
            state.removeHighlight();

            // reset temp values so only selections may occur
            tempRow = -1;
            tempCol = -1;

            // remove all the circles after moving
            state.removeDot();

            // check if the king is in check now
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

    public void setCheck(boolean b) {
        isCheck = b;
    }

    public boolean getCheck() {
        return isCheck;
    }

    public boolean checkForCheckMove(ChessState state, int row, int col, Piece.ColorType color) {
        if (state.getPiece(row, col).getPieceType() == Piece.PieceType.PAWN) {
            Pawn pawn = new Pawn(state.getPiece(row, col), state, color);
            if (color == Piece.ColorType.BLACK) {
                for (int i = 0; i < pawn.getXAttack().size(); i++) {
                    if (pawn.getXAttack().get(i) == state.getKingWhite().getX() && pawn.getYAttack().get(i) == state.getKingWhite().getY()) {
                        state.setHighlightCheck(state.getKingWhite().getX(), state.getKingWhite().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            } else if (color == Piece.ColorType.WHITE) {
                for (int i = 0; i < pawn.getXAttack().size(); i++) {
                    if (pawn.getXAttack().get(i) == state.getKingBlack().getX() && pawn.getYAttack().get(i) == state.getKingBlack().getY()) {
                        state.setHighlightCheck(state.getKingBlack().getX(), state.getKingBlack().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            }
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KNIGHT) {
            Knight knight = new Knight(state.getPiece(row, col), state, color);
            state.setCircles(knight.getX(), knight.getY());
            if (color == Piece.ColorType.BLACK) {
                for (int i = 0; i < knight.getX().size(); i++) {
                    if (knight.getX().get(i) == state.getKingWhite().getX() && knight.getY().get(i) == state.getKingWhite().getY()) {
                        state.setHighlightCheck(state.getKingWhite().getX(), state.getKingWhite().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            } else if (color == Piece.ColorType.WHITE) {
                for (int i = 0; i < knight.getX().size(); i++) {
                    if (knight.getX().get(i) == state.getKingBlack().getX() && knight.getY().get(i) == state.getKingBlack().getY()) {
                        state.setHighlightCheck(state.getKingBlack().getX(), state.getKingBlack().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            }
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.BISHOP) {
            Bishop bishop = new Bishop(state.getPiece(row, col), state, color);
            state.setCircles(bishop.getX(), bishop.getY());
            if (color == Piece.ColorType.BLACK) {
                for (int i = 0; i < bishop.getX().size(); i++) {
                    if (bishop.getX().get(i) == state.getKingWhite().getX() && bishop.getY().get(i) == state.getKingWhite().getY()) {
                        state.setHighlightCheck(state.getKingWhite().getX(), state.getKingWhite().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            } else if (color == Piece.ColorType.WHITE) {
                for (int i = 0; i < bishop.getX().size(); i++) {
                    if (bishop.getX().get(i) == state.getKingBlack().getX() && bishop.getY().get(i) == state.getKingBlack().getY()) {
                        state.setHighlightCheck(state.getKingBlack().getX(), state.getKingBlack().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            }
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.ROOK) {
            Rook rook = new Rook(state.getPiece(row, col), state, color);
            state.setCircles(rook.getX(), rook.getY());
            if (color == Piece.ColorType.BLACK) {
                for (int i = 0; i < rook.getX().size(); i++) {
                    if (rook.getX().get(i) == state.getKingWhite().getX() && rook.getY().get(i) == state.getKingWhite().getY()) {
                        state.setHighlightCheck(state.getKingWhite().getX(), state.getKingWhite().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            } else if (color == Piece.ColorType.WHITE) {
                for (int i = 0; i < rook.getX().size(); i++) {
                    if (rook.getX().get(i) == state.getKingBlack().getX() && rook.getY().get(i) == state.getKingBlack().getY()) {
                        state.setHighlightCheck(state.getKingBlack().getX(), state.getKingBlack().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            }
        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.QUEEN) {
            Queen queen = new Queen(state.getPiece(row, col), state, color);
            state.setCircles(queen.getX(), queen.getY());
            if (color == Piece.ColorType.BLACK) {
                for (int i = 0; i < queen.getX().size(); i++) {
                    if (queen.getX().get(i) == state.getKingWhite().getX() && queen.getY().get(i) == state.getKingWhite().getY()) {
                        state.setHighlightCheck(state.getKingWhite().getX(), state.getKingWhite().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            } else if (color == Piece.ColorType.WHITE) {
                for (int i = 0; i < queen.getX().size(); i++) {
                    if (queen.getX().get(i) == state.getKingBlack().getX() && queen.getY().get(i) == state.getKingBlack().getY()) {
                        state.setHighlightCheck(state.getKingBlack().getX(), state.getKingBlack().getY());
                        piecesCauseCheck.add(state.getPiece(row, col));
                    }
                }
            }
        }
        if (piecesCauseCheck.size() > 0) {
            return true;
        }
        return false;
    }

    public void checkMoveToCheckKing(ChessState state, Piece.ColorType colorType) {
        // if it is white's turn
        if (colorType == Piece.ColorType.WHITE) {
            // generate all of black's piece movements
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (state.getPiece(row, col).getPieceColor() == Piece.ColorType.BLACK) {
                        if (state.getPiece(row, col).getPieceType() == Piece.PieceType.PAWN) {
                            Pawn pawn = new Pawn(state.getPiece(row, col), state, Piece.ColorType.BLACK);
                            state.setCircles(pawn.getXAttack(), pawn.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KNIGHT) {
                            Knight knight = new Knight(state.getPiece(row, col), state, Piece.ColorType.BLACK);
                            state.setCircles(knight.getXAttack(), knight.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.BISHOP) {
                            Bishop bishop = new Bishop(state.getPiece(row, col), state, Piece.ColorType.BLACK);
                            state.setCircles(bishop.getXAttack(), bishop.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.ROOK) {
                            Rook rook = new Rook(state.getPiece(row, col), state, Piece.ColorType.BLACK);
                            state.setCircles(rook.getXAttack(), rook.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.QUEEN) {
                            Queen queen = new Queen(state.getPiece(row, col), state, Piece.ColorType.BLACK);
                            state.setCircles(queen.getXAttack(), queen.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KING) {
                            King king = new King(state.getPiece(row, col), state, Piece.ColorType.BLACK);
                            state.setCircles(king.getXAttack(), king.getYAttack());
                        }
                    }
                }
            }
            whiteKingMovement(state);
            state.removeDot();
        } else if (colorType == Piece.ColorType.BLACK) {
            // generate all of black's piece movements
            /*for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (state.getPiece(row, col).getPieceColor() == Piece.ColorType.WHITE) {
                        if (state.getPiece(row, col).getPieceType() == Piece.PieceType.PAWN) {
                            Pawn pawn = new Pawn(state.getPiece(row, col), state, Piece.ColorType.WHITE);
                            state.setCircles(pawn.getXAttack(), pawn.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KNIGHT) {
                            Knight knight = new Knight(state.getPiece(row, col), state, Piece.ColorType.WHITE);
                            state.setCircles(knight.getXAttack(), knight.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.BISHOP) {
                            Bishop bishop = new Bishop(state.getPiece(row, col), state, Piece.ColorType.WHITE);
                            state.setCircles(bishop.getXAttack(), bishop.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.ROOK) {
                            Rook rook = new Rook(state.getPiece(row, col), state, Piece.ColorType.WHITE);
                            state.setCircles(rook.getXAttack(), rook.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.QUEEN) {
                            Queen queen = new Queen(state.getPiece(row, col), state, Piece.ColorType.WHITE);
                            state.setCircles(queen.getXAttack(), queen.getYAttack());
                        } else if (state.getPiece(row, col).getPieceType() == Piece.PieceType.KING) {
                            King king = new King(state.getPiece(row, col), state, Piece.ColorType.WHITE);
                            state.setCircles(king.getXAttack(), king.getYAttack());
                        }
                    }
                }
            }
            blackKingMovement(state);
            state.removeDot();*/
        }
    }

    public void whiteKingMovement(ChessState state) {
        int x = state.getKingWhite().getX();
        int y = state.getKingWhite().getY();
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

    public void blackKingMovement(ChessState state) {
        int x = state.getKingBlack().getX();
        int y = state.getKingBlack().getY();
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
