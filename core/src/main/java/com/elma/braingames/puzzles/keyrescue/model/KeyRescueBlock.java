package com.elma.braingames.puzzles.keyrescue.model;

public class KeyRescueBlock {

    public enum Orientation {

        HORIZONTAL,
        VERTICAL
    }


    private final int id;

    private int row;
    private int col;

    private final int length;

    private final Orientation orientation;

    private final boolean keyBlock;


    public KeyRescueBlock(
        int id,
        int row,
        int col,
        int length,
        Orientation orientation,
        boolean keyBlock
    ) {

        this.id = id;

        this.row = row;

        this.col = col;

        this.length = length;

        this.orientation = orientation;

        this.keyBlock = keyBlock;
    }


    public int getId() {

        return id;
    }


    public int getRow() {

        return row;
    }


    public int getCol() {

        return col;
    }


    public int getLength() {

        return length;
    }


    public Orientation getOrientation() {

        return orientation;
    }


    public boolean isKeyBlock() {

        return keyBlock;
    }


    public void setPosition(
        int row,
        int col
    ) {

        this.row = row;

        this.col = col;
    }


    public boolean isHorizontal() {

        return orientation
            == Orientation.HORIZONTAL;
    }


    public boolean isVertical() {

        return orientation
            == Orientation.VERTICAL;
    }

    public boolean occupies(
        int targetRow,
        int targetCol
    ) {

        if (isHorizontal()) {

            return targetRow == row
                &&
                targetCol >= col
                &&
                targetCol < col + length;
        }


        return targetCol == col
            &&
            targetRow >= row
            &&
            targetRow < row + length;
    }
}
