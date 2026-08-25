package com.elma.braingames.models;

import com.elma.braingames.enums.PuzzleType;

public class PuzzleData {

    private final int levelId;

    private final PuzzleType puzzleType;

    private final int moveLimit;

    private final float timeLimit;

    private final int threeStarTarget;

    private final int twoStarTarget;

    private final int oneStarTarget;

    public PuzzleData(
        int levelId,
        PuzzleType puzzleType,
        int moveLimit,
        float timeLimit,
        int threeStarTarget,
        int twoStarTarget,
        int oneStarTarget
    ) {

        this.levelId = levelId;
        this.puzzleType = puzzleType;
        this.moveLimit = moveLimit;
        this.timeLimit = timeLimit;
        this.threeStarTarget = threeStarTarget;
        this.twoStarTarget = twoStarTarget;
        this.oneStarTarget = oneStarTarget;

    }

    public int getLevelId() {

        return levelId;

    }

    public PuzzleType getPuzzleType() {

        return puzzleType;

    }

    public int getMoveLimit() {

        return moveLimit;

    }

    public float getTimeLimit() {

        return timeLimit;

    }

    public int getThreeStarTarget() {

        return threeStarTarget;

    }

    public int getTwoStarTarget() {

        return twoStarTarget;

    }
    public int getOneStarTarget() {

        return oneStarTarget;

    }

}
