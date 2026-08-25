package com.elma.braingames.puzzles.lightsout.manager;

import com.elma.braingames.puzzles.lightsout.model.LightsOutBoard;

public class LightsOutGameManager {

    private final LightsOutBoard board;

    private int moves;

    private boolean completed;


    public LightsOutGameManager() {

        board =
            new LightsOutBoard();

        moves = 0;

        completed = false;
    }


    public boolean pressCell(
        int row,
        int col
    ) {

        if (completed) {
            return false;
        }


        if (
            row < 0
                ||
                row >= LightsOutBoard.ROWS
                ||
                col < 0
                ||
                col >= LightsOutBoard.COLS
        ) {

            return false;
        }


        board.toggleCross(
            row,
            col
        );


        moves++;


        checkCompleted();


        return true;
    }


    private void checkCompleted() {

        if (
            board.areAllLightsOn()
        ) {

            completed = true;
        }
    }


    public int calculateStars() {

        if (moves <= 28) {

            return 3;
        }

        if (moves <= 35) {

            return 2;
        }

        if (moves <= 45) {

            return 1;
        }

        return 0;
    }


    public LightsOutBoard getBoard() {

        return board;
    }


    public int getMoves() {

        return moves;
    }


    public boolean isCompleted() {

        return completed;
    }
}
