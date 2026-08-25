package com.elma.braingames.puzzles.numbersequence.manager;

import com.elma.braingames.puzzles.numbersequence.model.NumberSequenceBoard;
import com.elma.braingames.puzzles.numbersequence.model.NumberSequenceCell;

public class NumberSequenceGameManager {

    public enum GameState {

        PLAYING,

        COMPLETED
    }


    private static final int LAST_NUMBER = 30;

    private static final float WRONG_FLASH_DURATION = 0.25f;


    private final NumberSequenceBoard board;


    private GameState state;

    private int nextNumber;

    private float elapsedTime;

    private NumberSequenceCell wrongCell;

    private float wrongFlashTimer;


    public NumberSequenceGameManager() {

        board =
            new NumberSequenceBoard();


        state =
            GameState.PLAYING;


        nextNumber = 1;


        elapsedTime = 0f;


        wrongCell = null;

        wrongFlashTimer = 0f;
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

        updateWrongFlash(delta);
    }

    public boolean pressCell(
        int row,
        int col
    ) {

        if (
            state != GameState.PLAYING
        ) {

            return false;
        }


        NumberSequenceCell cell =
            board.getCell(
                row,
                col
            );


        if (cell == null) {

            return false;
        }

        if (
            cell.getNumber()
                == nextNumber
        ) {

            cell.setCompleted(true);

            nextNumber++;

            if (
                nextNumber
                    > LAST_NUMBER
            ) {

                completeGame();
            }


            return true;
        }

        showWrongFeedback(cell);

        return true;
    }

    private void showWrongFeedback(
        NumberSequenceCell cell
    ) {

        if (wrongCell != null) {

            wrongCell.setWrongFlash(false);
        }


        wrongCell = cell;

        wrongCell.setWrongFlash(true);

        wrongFlashTimer = 0f;
    }


    private void updateWrongFlash(
        float delta
    ) {

        if (wrongCell == null) {

            return;
        }


        wrongFlashTimer += delta;


        if (
            wrongFlashTimer
                >= WRONG_FLASH_DURATION
        ) {

            wrongCell.setWrongFlash(false);

            wrongCell = null;

            wrongFlashTimer = 0f;
        }
    }

    private void completeGame() {

        state =
            GameState.COMPLETED;

        for (
            NumberSequenceCell cell :
            board.getCells()
        ) {

            cell.setWrongFlash(false);
        }
    }

    public int calculateStars() {

        if (
            elapsedTime <= 30f
        ) {

            return 3;
        }

        if (
            elapsedTime <= 35f
        ) {

            return 2;
        }

        if (
            elapsedTime <= 45f
        ) {

            return 1;
        }

        return 0;
    }

    public NumberSequenceBoard getBoard() {

        return board;
    }


    public GameState getState() {

        return state;
    }


    public int getNextNumber() {

        return nextNumber;
    }


    public float getElapsedTime() {

        return elapsedTime;
    }


    public boolean isCompleted() {

        return
            state
                == GameState.COMPLETED;
    }


    public boolean isPlaying() {

        return
            state
                == GameState.PLAYING;
    }

    public float getMoves() {

        return elapsedTime;
    }
}
