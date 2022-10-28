package com.example.chessgame.chess;

import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;

public class PawnMove extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PawnMove(GamePlayer player) {
        super(player);
    }
}
