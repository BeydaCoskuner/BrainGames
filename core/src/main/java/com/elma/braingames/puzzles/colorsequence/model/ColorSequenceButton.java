package com.elma.braingames.puzzles.colorsequence.model;

import com.badlogic.gdx.graphics.Color;

public class ColorSequenceButton {

    private final int index;
    private final int row;
    private final int col;
    private final Color color;

    private boolean active;

    public ColorSequenceButton(
        int index,
        int row,
        int col,
        Color color
    ) {

        this.index = index;
        this.row = row;
        this.col = col;
        this.color = color;
        this.active = false;
    }

    public int getIndex() {

        return index;
    }

    public int getRow() {

        return row;
    }

    public int getCol() {

        return col;
    }

    public Color getColor() {

        return color;
    }

    public boolean isActive() {

        return active;
    }

    public void setActive(
        boolean active
    ) {

        this.active = active;
    }
}
