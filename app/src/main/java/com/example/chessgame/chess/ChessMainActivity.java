package com.example.chessgame.chess;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgame.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgame.GameFramework.infoMessage.GameState;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.GameFramework.utilities.Logger;
import com.example.chessgame.GameFramework.utilities.Saving;
import com.example.chessgame.R;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.players.ChessComputerPlayer;
import com.example.chessgame.chess.players.ChessHumanPlayer;


import java.util.ArrayList;

public class ChessMainActivity extends GameMainActivity {
    private static final String TAG = "ChessMainActivity";
    public static final int PORT_NUMBER = 5213;

    @Override
    public GameConfig createDefaultConfig() {
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        //Local Human Player
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new ChessHumanPlayer(name);
            }
        });

        //computer player(dumb)
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new ChessComputerPlayer(name);
            }
        });

        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Chess", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0);
        defaultConfig.addPlayer("Computer", 3);

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI

        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null)
            return new ChessLocalGame();
        return new ChessLocalGame((ChessState) gameState); //copy constructor?
    }

    @Override
    public GameState saveGame(String gameName) {
        return super.saveGame(getGameString(gameName));
    }

    @Override
    public GameState loadGame(String gameName){
        String appName = getGameString(gameName);
        super.loadGame(appName);
        Logger.log(TAG, "Loading: " + gameName);
        return (GameState) new ChessState((ChessState) Saving.readFromFile(appName, this.getApplicationContext()));
    }

}