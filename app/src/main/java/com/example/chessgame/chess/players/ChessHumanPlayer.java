package com.example.chessgame.chess.players;

import android.view.MotionEvent;
import android.view.View;

import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.infoMessage.GameInfo;
import com.example.chessgame.GameFramework.players.GameHumanPlayer;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {
    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name) {
        super(name);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    @Override
    public void setAsGui(GameMainActivity activity) {

    }
}
