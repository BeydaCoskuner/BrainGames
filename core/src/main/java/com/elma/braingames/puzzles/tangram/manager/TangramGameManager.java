package com.elma.braingames.puzzles.tangram.manager;

import com.elma.braingames.puzzles.tangram.model.TangramBoard;
import com.elma.braingames.puzzles.tangram.model.TangramPiece;


public class TangramGameManager {


    public enum GameState {

        PLAYING,

        COMPLETED
    }


    private static final float POSITION_TOLERANCE = 35f;

    private static final float ROTATION_TOLERANCE = 22.5f;


    private final TangramBoard board;

    private GameState state;


    private int moves;

    private float elapsedTime;


    public TangramGameManager() {

        board =
            new TangramBoard();

        state =
            GameState.PLAYING;

        moves = 0;

        elapsedTime = 0f;
    }

    public void update(
        float delta
    ) {

        if (
            state != GameState.PLAYING
        ) {

            return;
        }


        elapsedTime += delta;
    }

    public TangramBoard getBoard() {

        return board;
    }

    public GameState getState() {

        return state;
    }


    public boolean isPlaying() {

        return state ==
            GameState.PLAYING;
    }


    public boolean isCompleted() {

        return state ==
            GameState.COMPLETED;
    }

    public float getElapsedTime() {

        return elapsedTime;
    }

    public int getMoves() {

        return moves;
    }

    public void movePiece(
        int pieceId,
        float x,
        float y
    ) {

        if (
            state !=
                GameState.PLAYING
        ) {

            return;
        }


        TangramPiece piece =
            board.getPiece(
                pieceId
            );


        if (
            piece == null ||
                piece.isPlaced()
        ) {

            return;
        }


        piece.setPosition(
            x,
            y
        );
    }


    public void rotatePiece(
        int pieceId
    ) {

        if (
            state !=
                GameState.PLAYING
        ) {

            return;
        }


        TangramPiece piece =
            board.getPiece(
                pieceId
            );


        if (
            piece == null ||
                piece.isPlaced()
        ) {

            return;
        }


        piece.rotate45();


        moves++;


        checkPiecePlacement(
            piece
        );
    }

    public void releasePiece(
        int pieceId
    ) {

        if (
            state !=
                GameState.PLAYING
        ) {

            return;
        }


        TangramPiece piece =
            board.getPiece(
                pieceId
            );


        if (
            piece == null ||
                piece.isPlaced()
        ) {

            return;
        }


        moves++;


        checkPiecePlacement(
            piece
        );
    }

    private void checkPiecePlacement(
        TangramPiece piece
    ) {

        float dx =
            piece.getX()
                -
                piece.getTargetX();


        float dy =
            piece.getY()
                -
                piece.getTargetY();


        float distance =
            (float)
                Math.sqrt(
                    dx * dx +
                        dy * dy
                );


        float rotationDifference =
            Math.abs(
                piece.getRotation()
                    -
                    piece.getTargetRotation()
            );


        if (
            rotationDifference >
                180f
        ) {

            rotationDifference =
                360f -
                    rotationDifference;
        }


        if (
            distance <=
                POSITION_TOLERANCE
                &&
                rotationDifference <=
                    ROTATION_TOLERANCE
        ) {

            piece.setPosition(
                piece.getTargetX(),
                piece.getTargetY()
            );


            piece.setRotation(
                piece.getTargetRotation()
            );


            piece.setPlaced(
                true
            );


            checkCompleted();
        }
    }

    private void checkCompleted() {

        if (
            board.areAllPiecesPlaced()
        ) {

            state =
                GameState.COMPLETED;
        }
    }

    public int calculateStars() {

        if (
            elapsedTime <= 35f
        ) {

            return 3;
        }


        if (
            elapsedTime <= 45f
        ) {

            return 2;
        }


        if (
            elapsedTime <= 60f
        ) {

            return 1;
        }


        return 0;
    }
}
