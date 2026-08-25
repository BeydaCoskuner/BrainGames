package com.elma.braingames.puzzles.numbersequence.layout;

import com.elma.braingames.puzzles.numbersequence.model.NumberSequenceBoard;

public class NumberSequenceLayout {

    private float boardX;
    private float boardY;

    private float boardWidth;
    private float boardHeight;

    private float cellWidth;
    private float cellHeight;

    private float cellGap;


    private float nextMoveX;
    private float nextMoveY;


    public void update(
        float width,
        float height
    ) {

        float availableWidth =
            width * 0.90f;

        float availableHeight =
            height * 0.67f;


        float cellSize =
            Math.min(
                availableWidth /
                    NumberSequenceBoard.COLS,

                availableHeight /
                    NumberSequenceBoard.ROWS
            );


        cellGap =
            cellSize * 0.10f;


        cellWidth =
            cellSize;

        cellHeight =
            cellSize;


        boardWidth =
            NumberSequenceBoard.COLS
                * cellWidth
                +
                (NumberSequenceBoard.COLS - 1)
                    * cellGap;


        boardHeight =
            NumberSequenceBoard.ROWS
                * cellHeight
                +
                (NumberSequenceBoard.ROWS - 1)
                    * cellGap;


        boardX =
            (width - boardWidth) / 2f;



        boardY =
            height * 0.20f;

        nextMoveX =
            width / 2f;

        nextMoveY =
            boardY
                + boardHeight
                + height * 0.055f;
    }


    public float getBoardX() {

        return boardX;
    }


    public float getBoardY() {

        return boardY;
    }


    public float getBoardWidth() {

        return boardWidth;
    }


    public float getBoardHeight() {

        return boardHeight;
    }


    public float getCellWidth() {

        return cellWidth;
    }


    public float getCellHeight() {

        return cellHeight;
    }


    public float getCellGap() {

        return cellGap;
    }


    public float getCellX(
        int col
    ) {

        return
            boardX
                +
                col
                    * (
                    cellWidth
                        +
                        cellGap
                );
    }


    public float getCellY(
        int row
    ) {

        return
            boardY
                +
                (
                    NumberSequenceBoard.ROWS
                        - 1
                        - row
                )
                    * (
                    cellHeight
                        +
                        cellGap
                );
    }


    public float getCellCenterX(
        int col
    ) {

        return
            getCellX(col)
                +
                cellWidth / 2f;
    }


    public float getCellCenterY(
        int row
    ) {

        return
            getCellY(row)
                +
                cellHeight / 2f;
    }


    public float getNextMoveX() {

        return nextMoveX;
    }


    public float getNextMoveY() {

        return nextMoveY;
    }
}
