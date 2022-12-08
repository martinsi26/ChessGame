package com.example.chessgame.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.LocalGame;
import com.example.chessgame.GameFramework.gameConfiguration.GameConfig;
import com.example.chessgame.GameFramework.gameConfiguration.GamePlayerType;
import com.example.chessgame.GameFramework.infoMessage.GameState;
import com.example.chessgame.GameFramework.players.GamePlayer;
import com.example.chessgame.GameFramework.utilities.Saving;
import com.example.chessgame.R;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.players.ChessComputerPlayer;
import com.example.chessgame.chess.players.ChessComputerPlayer2;
import com.example.chessgame.chess.players.ChessHumanPlayer;

import java.util.ArrayList;

public class ChessMainActivity extends GameMainActivity {

    //Tag for logging
    private static final String TAG = "ChessMainActivity";
    public static final int PORT_NUMBER = 5213;

    /**
     * a chess game is for two players. The default is human vs. computer
     */
    @Override
    public GameConfig createDefaultConfig() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // yellow-on-blue GUI
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new ChessHumanPlayer(name, R.layout.activity_main, (ChessState) getGameState());
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new ChessComputerPlayer(name);
            }
        });

        // smarter computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new ChessComputerPlayer2(name);
            }
        });

        // Create a game configuration class for Chess
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Chess", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); // human player GUI
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // remote player GUI

        //done!
        return defaultConfig;

    }//createDefaultConfig

    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     * @param gameState
     * 				the gameState for this game or null for a new game
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     */
    @Override
    public LocalGame createLocalGame(GameState gameState) {
        if(gameState == null)
            return new ChessLocalGame();
        return new ChessLocalGame((ChessState) gameState);
    }

    /**
     * saveGame, adds this games prepend to the filename
     *
     * @param gameName
     * 				Desired save name
     * @return String representation of the save
     */
    @Override
    public GameState saveGame(String gameName) {
        return super.saveGame(getGameString(gameName));
    }

    /**
     * loadGame, adds this games prepend to the desire file to open and creates the game specific state
     * @param gameName
     * 				The file to open
     * @return The loaded GameState
     */
    @Override
    public GameState loadGame(String gameName){
        String appName = getGameString(gameName);
        super.loadGame(appName);
        return (GameState) new ChessState((ChessState) Saving.readFromFile(appName, this.getApplicationContext()));
    }
}