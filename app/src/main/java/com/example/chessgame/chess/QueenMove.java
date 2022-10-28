package com.example.chessgame.chess;

import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;

public class QueenMove extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public QueenMove(GamePlayer player) {
        super(player);
    }
}
