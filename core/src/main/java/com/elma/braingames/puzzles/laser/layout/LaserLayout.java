package com.elma.braingames.puzzles.laser.layout;

public class LaserLayout {

    private float boardX;
    private float boardY;

    private float boardSize;
    private float cellSize;

    private float borderSize;

    private float mirrorLength;
    private float mirrorThickness;

    private float sourceRadius;
    private float targetRadius;

    public void update(
        float width,
        float height
    ) {

        boardSize =
            Math.min(
                width,
                height
            ) * 0.82f;

        boardX =
            (width - boardSize) / 2f;

        boardY =
            (height - boardSize) / 2f;

        cellSize =
            boardSize / 8f;

        borderSize =
            Math.max(
                4f,
                cellSize * 0.06f
            );

        mirrorLength =
            cellSize * 0.70f;

        mirrorThickness =
            Math.max(
                4f,
                cellSize * 0.07f
            );

        sourceRadius =
            cellSize * 0.20f;

        targetRadius =
            cellSize * 0.25f;
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


    public float getBorderSize() {

        return borderSize;
    }


    public float getMirrorLength() {

        return mirrorLength;
    }


    public float getMirrorThickness() {

        return mirrorThickness;
    }


    public float getSourceRadius() {

        return sourceRadius;
    }


    public float getTargetRadius() {

        return targetRadius;
    }


    public float getCellX(
        int col
    ) {

        return boardX
            + col * cellSize;
    }


    public float getCellY(
        int row
    ) {

        return boardY
            + row * cellSize;
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


    public float getBoardRight() {

        return boardX + boardSize;
    }


    public float getBoardTop() {

        return boardY + boardSize;
    }
}
