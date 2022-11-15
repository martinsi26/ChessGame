package com.example.chessgame.chess.players;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.chessgame.GameFramework.GameMainActivity;
import com.example.chessgame.GameFramework.infoMessage.GameInfo;
import com.example.chessgame.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.chessgame.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.chessgame.GameFramework.players.GameHumanPlayer;
import com.example.chessgame.GameFramework.utilities.MessageBox;
import com.example.chessgame.R;
import com.example.chessgame.chess.chessActionMessage.ChessMoveAction;
import com.example.chessgame.chess.chessActionMessage.ChessSelectAction;
import com.example.chessgame.chess.infoMessage.ChessState;
import com.example.chessgame.chess.infoMessage.Piece;
import com.example.chessgame.chess.views.BlackCaptureSurfaceView;
import com.example.chessgame.chess.infoMessage.Piece;
import com.example.chessgame.chess.views.ChessBoardSurfaceView;
import com.example.chessgame.chess.views.WhiteCaptureSurfaceView;

public class ChessHumanPlayer extends GameHumanPlayer implements View.OnTouchListener {

    //Tag for logging
    private static final String TAG = "ChessHumanPlayer";

    // the surface view
    private ChessBoardSurfaceView surfaceView;
    public TextView movesLog;
    private ChessBoardSurfaceView surfaceViewChessBoard;
    private Button resignButton;
    //private BlackCaptureSurfaceView surfaceViewBlackCapture;
    private WhiteCaptureSurfaceView surfaceViewWhiteCapture;

    // the ID for the layout to use
    private int layoutId;

    private ChessState state;
    private int numTurns;
    private boolean justStarted;
    private int x = 8;
    private int y = 8;

    /**
     * constructor
     *
     * @param name the name of the player
     */
    public ChessHumanPlayer(String name, int layoutId, ChessState state) {
        super(name);
        this.layoutId = layoutId;
        numTurns = 1;
        justStarted = true;
        this.state = state;
    }

    public void setState(ChessState state) {
        this.state = state;
    }

    @Override
    public void receiveInfo(GameInfo info) {
        if (surfaceViewChessBoard == null) {
            return;
        }
        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceViewChessBoard.flash(Color.RED, 50);
        } else if (!(info instanceof ChessState)) {
            // if we do not have a TTTState, ignore
            return;
        } else {
            surfaceViewChessBoard.setState((ChessState)info);
            surfaceViewChessBoard.invalidate();
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
        movesLog = myActivity.findViewById(R.id.movesLog);
        surfaceViewChessBoard = (ChessBoardSurfaceView) myActivity.findViewById(R.id.chessBoard);
        resignButton = myActivity.findViewById(R.id.surrenderButton);
        //surfaceViewWhiteCapture = (WhiteCaptureSurfaceView) myActivity.findViewById(R.id.whiteCaptures);
        //surfaceViewBlackCapture = (BlackCaptureSurfaceView) myActivity.findViewById(R.id.blackCaptures);
        surfaceViewChessBoard.setOnTouchListener(this);
        resignButton.setOnTouchListener(this);
    }

    public TextView getMovesLog(){return this.movesLog;}

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

        if(view.getId() == resignButton.getId()){
            MessageBox.popUpMessage("Game Over!\n You have resigned",myActivity);
            CountDownTimer cdt = new CountDownTimer(3000,10) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    myActivity.finishAffinity();
                }
            };
            cdt.start();
        }
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

                        // create the select action
                        if (state.getPiece(i, j).getPieceColor() == Piece.ColorType.WHITE && state.getWhoseMove() == 0) {
                            ChessSelectAction select = new ChessSelectAction(this, i, j);
                            game.sendAction(select);
                        } else if (state.getPiece(i, j).getPieceColor() == Piece.ColorType.BLACK && state.getWhoseMove() == 1) {
                            ChessSelectAction select = new ChessSelectAction(this, i, j);
                            game.sendAction(select);
                        } else if (state.getPiece(i, j).getPieceColor() != Piece.ColorType.WHITE && state.getWhoseMove() == 0) {
                            ChessMoveAction move = new ChessMoveAction(this, i, j);
                            game.sendAction(move);
                        } else if (state.getPiece(i, j).getPieceColor() != Piece.ColorType.BLACK && state.getWhoseMove() == 1) {
                            ChessMoveAction move = new ChessMoveAction(this, i, j);
                            game.sendAction(move);
                        }

                        surfaceViewChessBoard.invalidate();
                    }
                }
            }
        }

        // register that we have handled the event
        return true;
    }

    public void displayMovesLog(int currRow, int currCol,int tempRow, ChessState state,boolean isCapture){
        if(state == null)return;
        Piece.PieceType currPiece = state.getPiece(currRow,currCol).getPieceType();
        String toReturn = "";
        if(justStarted){
            movesLog.append("\n");
            justStarted = false;
        }
        boolean whitesTurn = state.getWhoseMove() == 0;
        if(whitesTurn) {
            toReturn += numTurns + ")";
        }
        if(currPiece == Piece.PieceType.KING){
            toReturn+="K";
        }else if(currPiece == Piece.PieceType.QUEEN){
            toReturn+="Q";
        }else if(currPiece == Piece.PieceType.BISHOP){
            toReturn += "B";
        }else if(currPiece == Piece.PieceType.KNIGHT){
            toReturn += "N";
        }else if(currPiece == Piece.PieceType.ROOK){
            toReturn += "R";
        }
        if(isCapture && currPiece == Piece.PieceType.PAWN){
            toReturn += determineRow(tempRow);
            toReturn += "x";
        }else if(isCapture){
            toReturn += "x";
        }
        toReturn += determineRow(currRow);
        toReturn += currCol + 1 + " ";
        if(!whitesTurn){
            numTurns++;
            toReturn+="\n";
        }
        movesLog.append(toReturn);

    }

    private char determineRow(int row){
        switch(row){
            case(0):
                return 'a';
            case(1):
                return 'b';
            case(2):
                return 'c';
            case(3):
                return'd';
            case(4):
                return 'e';
            case(5):
                return 'f';
            case(6):
                return 'g';
            case(7):
                return 'h';
        }
        return 'q';
    }
}
