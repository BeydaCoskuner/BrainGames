package com.elma.braingames.puzzles.laser.model;
public class LaserMirror {

    public enum Orientation {
        SLASH,       // /
        BACKSLASH,   // \
        VERTICAL,    // |
        HORIZONTAL   // —
    }

    private int row;
    private int col;

    private Orientation orientation;

    public LaserMirror(
        int row,
        int col,
        Orientation orientation
    ) {

        this.row = row;
        this.col = col;
        this.orientation = orientation;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Orientation getOrientation() {
        return orientation;
    }
    public void rotateClockwise() {

        switch (orientation) {

            case SLASH:

                orientation =
                    Orientation.HORIZONTAL;

                break;


            case HORIZONTAL:

                orientation =
                    Orientation.BACKSLASH;

                break;


            case BACKSLASH:

                orientation =
                    Orientation.VERTICAL;

                break;


            case VERTICAL:

                orientation =
                    Orientation.SLASH;

                break;
        }
    }

    public void setPosition(
        int row,
        int col
    ) {

        this.row = row;
        this.col = col;
    }
}
