package com.elma.braingames.puzzles.numbersequence.model;

public class NumberSequenceCell {

    private final int index;

    private final int row;

    private final int col;

    private final int number;

    private boolean completed;

    private boolean wrongFlash;


    public NumberSequenceCell(
        int index,
        int row,
        int col,
        int number
    ) {

        this.index = index;

        this.row = row;

        this.col = col;

        this.number = number;

        completed = false;

        wrongFlash = false;
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


    public int getNumber() {

        return number;
    }


    public boolean isCompleted() {

        return completed;
    }


    public void setCompleted(
        boolean completed
    ) {

        this.completed = completed;
    }


    public boolean isWrongFlash() {

        return wrongFlash;
    }


    public void setWrongFlash(
        boolean wrongFlash
    ) {

        this.wrongFlash = wrongFlash;
    }
}
