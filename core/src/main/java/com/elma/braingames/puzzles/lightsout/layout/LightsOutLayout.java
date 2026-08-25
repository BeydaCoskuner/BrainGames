package com.elma.braingames.puzzles.lightsout.layout;

import com.elma.braingames.puzzles.lightsout.model.LightsOutBoard;

public class LightsOutLayout {

    private float boardX;
    private float boardY;

    private float boardSize;

    private float cellSize;

    private float cellGap;


    public void update(
        float width,
        float height
    ) {

        float availableHeight =
            height * 0.72f;


        boardSize =
            Math.min(
                width * 0.86f,
                availableHeight
            );


        cellGap =
            boardSize * 0.018f;


        cellSize =
            (
                boardSize
                    - cellGap
                    * (LightsOutBoard.COLS - 1)
            )
                / LightsOutBoard.COLS;


        boardX =
            (width - boardSize) / 2f;


        boardY =
            height * 0.30f;
    }


    public float getBoardX() {

        return boardX;
    }


    public float getBoardY() {

        return boardY;
    }


    public float getBoardSize() {

        return boardSize;
    }


    public float getCellSize() {

        return cellSize;
    }


    public float getCellGap() {

        return cellGap;
    }


    public float getCellX(
        int col
    ) {

        return boardX
            + col
            * (
            cellSize
                + cellGap
        );
    }


    public float getCellY(
        int row
    ) {

        return boardY
            + row
            * (
            cellSize
                + cellGap
        );
    }


    public float getCellCenterX(
        int col
    ) {

        return getCellX(col)
            + cellSize / 2f;
    }


    public float getCellCenterY(
        int row
    ) {

        return getCellY(row)
            + cellSize / 2f;
    }
}
