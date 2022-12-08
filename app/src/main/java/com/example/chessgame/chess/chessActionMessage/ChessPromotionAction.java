package com.example.chessgame.chess.chessActionMessage;

import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.chess.infoMessage.Piece;

public class ChessPromotionAction extends GameAction {


    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    private Piece promotionPiece;
    private int row;
    private int col;
    public static boolean isPromotion;

    public ChessPromotionAction(GamePlayer player,Piece promotionPiece,int row, int col) {
        super(player);
        this.promotionPiece = promotionPiece;
        this.row = row;
        this.col = col;
        isPromotion = false;
    }

    public int getRow(){return this.row;}
    public int getCol(){return this.col;}
    public Piece getPromotionPiece(){return this.promotionPiece;}
}
