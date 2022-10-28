package com.example.chessgame.chess.players;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.infoMessage.GameInfo;
import com.example.chessgame.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgame.GameFramework.players.GameHumanPlayer;
import com.example.chessgame.R;
import com.example.chessgame.chess.chessActionMessage.ChessDrawAction;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.views.ChessBoardSurfaceView;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    //Tag for logging
    private static final String TAG = "ChessHumanPlayer";

    // the surface view
    private ChessBoardSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    private ChessState state;

    private int x;
    private int y;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    public void setState(ChessState state) {
        this.state = state;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (surfaceView == null) {
            return;
        }
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        } else if (!(info instanceof ChessState)) {
            // if we do not have a TTTState, ignore
            return;
        } else {
            surfaceView.setState((ChessState)info);
            surfaceView.invalidate();
        }
    }

    /**
     * sets the current player as the activity's GUI
     */
    @Override
    public void setAsGui(GameMainActivity activity) {
        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (ChessBoardSurfaceView) myActivity.findViewById(R.id.chessBoard);
        surfaceView.setOnTouchListener(this);
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("Chess: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch (which we'll detect on
     * the "up" movement" onto a tic-tac-tie square
     *
     * @param motionEvent
     * 		the motion event that was detected
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // ignore if not an "down" event
        if (motionEvent.getAction() != MotionEvent.ACTION_DOWN) {
            return true;
        }

        // loop through all of the locations on the board and compare
        // the location pressed to the pixels on the screen to find
        // the exact location of the click according to the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (motionEvent.getX() > 20 + (i * 115) && motionEvent.getX() < 175 + (i * 115)) {
                    if (motionEvent.getY() > 20 + (j * 115) && motionEvent.getY() < 175 + (j * 115)) {

                        // create the highlight action
                        ChessDrawAction drawAction = new ChessDrawAction(this,x,y);
                        game.sendAction(drawAction);
                        surfaceView.invalidate();

                        // create the move action
                        ChessMoveAction action = new ChessMoveAction(this, i, j, x, y);
                        game.sendAction(action);
                        surfaceView.invalidate();

                        // position of the selected piece
                        x = i;
                        y = j;
                    }
                }
            }
        }

        // register that we have handled the event
        return true;
    }
}
