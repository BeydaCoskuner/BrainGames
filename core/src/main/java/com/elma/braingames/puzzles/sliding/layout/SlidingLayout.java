package com.elma.braingames.puzzles.sliding.layout;

public class SlidingLayout {

    private float boardX;
    private float boardY;

    private float tileSize;
    private float gap;

    public void update(float width, float height) {

        // Tahta ekranın ortasında olacak
        float boardSize =
            Math.min(width, height) * 0.70f;

        gap = boardSize * 0.02f;

        tileSize =
            (boardSize - gap * 2f) / 3f;

        boardX =
            (width - boardSize) / 2f;

        boardY =
            (height - boardSize) / 2f;
    }

    public float getTileX(int col) {

        return boardX +
            col * (tileSize + gap);
    }

    public float getTileY(int row) {

        return boardY +
            (2 - row) * (tileSize + gap);
    }

    public float getTileSize() {

        return tileSize;
    }

    public float getBoardX() {

        return boardX;
    }

    public float getBoardY() {

        return boardY;
    }

    public float getBoardSize() {

        return tileSize * 3f +
            gap * 2f;
    }
}
