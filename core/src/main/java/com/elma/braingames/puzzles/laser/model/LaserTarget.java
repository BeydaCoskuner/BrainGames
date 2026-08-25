package com.elma.braingames.puzzles.laser.model;

public class LaserTarget {

    private final int row;
    private final int col;

    public LaserTarget(
        int row,
        int col
    ) {

        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
