package com.elma.braingames.puzzles.lightsout.model;

public class LightsOutCell {

    private final int row;
    private final int col;

    private boolean on;


    public LightsOutCell(
        int row,
        int col,
        boolean on
    ) {

        this.row = row;
        this.col = col;
        this.on = on;
    }


    public int getRow() {

        return row;
    }


    public int getCol() {

        return col;
    }


    public boolean isOn() {

        return on;
    }


    public void toggle() {

        on = !on;
    }


    public void setOn(
        boolean on
    ) {

        this.on = on;
    }
}
