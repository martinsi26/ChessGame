package com.example.chessgame.chess;

import com.example.chessgame.GameFramework.actionMessage.GameAction;
import com.example.chessgame.GameFramework.players.GamePlayer;

public class BishopMove extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public BishopMove(GamePlayer player) {
        super(player);
    }
}
