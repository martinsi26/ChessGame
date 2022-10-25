package com.example.chessgame.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgame.GameFramework.infoMessage.GameState;
import com.example.chessgame.R;

public class ChessMainActivity extends GameMainActivity {

    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        return null;
    }
}