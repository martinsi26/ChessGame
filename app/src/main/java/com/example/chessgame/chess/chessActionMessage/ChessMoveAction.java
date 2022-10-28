package com.example.chessgame.chess.chessActionMessage;

import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;

public class ChessMoveAction extends GameAction {

    // instance variables: the selected row and column
    private int newRow;
    private int newCol;

    private int currentRow;
    private int currentCol;

    /**
     * Constructor for ChessMoveAction
     *
     //@param source the player making the move
     * @param newCol the row of the square selected
     * @param newRow the column of the square selected
     * @param currentRow the row of the new position
     * @param currentCol the col of the new position
     */
    public ChessMoveAction(GamePlayer player, int newRow, int newCol, int currentRow, int currentCol) {
        // invoke superclass constructor to set the player
        super(player);

        // set the row and column as passed to us
        this.newRow = newRow;
        this.newCol = newCol;

        this.currentRow = currentRow;
        this.currentCol = currentCol;
    }

    /**
     * get the object's row
     *
     * @return the row selected
     */
    public int getNewRow() { return newRow; }

    /**
     * get the object's column
     *
     * @return the column selected
     */
    public int getNewCol() { return newCol; }

    /**
     * get the object's column
     *
     * @return the column selected
     */
    public int getCurrentRow() { return currentRow; }

    /**
     * get the object's row
     *
     * @return the row selected
     */
    public int getCurrentCol() { return currentCol; }
}
