package com.elma.braingames.puzzles.keyrescue.layout;

import com.elma.braingames.puzzles.keyrescue.model.KeyRescueBoard;

public class KeyRescueLayout {

    private float boardX;
    private float boardY;

    private float boardSize;

    private float cellSize;

    private float borderSize;

    private float exitWidth;

    private float exitHeight;


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
            (height - boardSize) / 2f
                - height * 0.08f;

        //6x6 tahta
        cellSize =
            boardSize
                / KeyRescueBoard.COLS;

        borderSize =
            Math.max(
                5f,
                cellSize * 0.10f
            );


        //çıkış boyutu
        exitWidth =
            borderSize * 1.5f;


        exitHeight =
            cellSize * 2f;
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


    public float getExitWidth() {

        return exitWidth;
    }


    public float getExitHeight() {

        return exitHeight;
    }


    public float getBoardRight() {

        return boardX + boardSize;
    }


    public float getBoardTop() {

        return boardY + boardSize;
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
}
