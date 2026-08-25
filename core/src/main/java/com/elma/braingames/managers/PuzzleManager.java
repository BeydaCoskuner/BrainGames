package com.elma.braingames.managers;

import com.elma.braingames.models.PuzzleData;
import com.elma.braingames.ui.GameHUD;

public class
PuzzleManager {

    private final PuzzleData puzzleData;

    private final GameHUD hud;

    private int moves;

    private float remainingTime;

    private boolean finished;

    private boolean failed;

    public PuzzleManager(
        PuzzleData puzzleData,
        GameHUD hud
    ) {

        this.puzzleData = puzzleData;
        this.hud = hud;

        remainingTime = puzzleData.getTimeLimit();

        moves = 0;

        hud.setRemainingTime(remainingTime);

        hud.setMaxMoves(puzzleData.getMoveLimit());

    }

    public void update(float delta) {

        if (finished || failed) {
            return;
        }

        if (puzzleData.getTimeLimit() > 0) {

            remainingTime -= delta;

            if (remainingTime <= 0) {

                remainingTime = 0;

                failed = true;

            }

            hud.setRemainingTime(remainingTime);

        }

        hud.setMoves(moves);

    }

    public void addMove() {

        moves++;

    }

    public int getMoves() {

        return moves;

    }

    public float getRemainingTime() {

        return remainingTime;

    }

    public boolean isFinished() {

        return finished;

    }

    public boolean isFailed() {

        return failed;

    }

    public void finishLevel() {

        /*if (moves <= puzzleData.getThreeStarTarget()) {
            stars = 3;
        } else if (moves <= puzzleData.getTwoStarTarget()) {
            stars = 2;
        } else if (moves <= puzzleData.getOneStarTarget()) {
            stars = 1;
        } else {
            stars = 0;
        }*/

        finished = true;

    }

    public void restart() {

        finished = false;

        failed = false;

        moves = 0;

        remainingTime = puzzleData.getTimeLimit();

    }

}
