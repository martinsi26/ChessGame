package com.example.chessgame.chess.chessActionMessage;

import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;

public class ChessMoveAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public ChessMoveAction(GamePlayer player) {
        super(player);
    }
}
