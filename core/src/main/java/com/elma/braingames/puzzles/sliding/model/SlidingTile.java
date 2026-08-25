package com.elma.braingames.puzzles.sliding.model;

public class SlidingTile {

    private final int value;

    private int row;
    private int col;

    public SlidingTile(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isEmpty() {
        return value == 0;
    }
}
