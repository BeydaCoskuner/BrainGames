package com.elma.braingames.puzzles.maze.layout;

public class MazeLayout {

    private float mazeX;
    private float mazeY;

    private float mazeSize;

    public void update(
        float width,
        float height
    ) {

        mazeSize =
            Math.min(width, height) * 0.85f;

        mazeX =
            (width - mazeSize) / 2f;

        mazeY =
            (height - mazeSize) / 2f;
    }

    public float getMazeX() {

        return mazeX;
    }

    public float getMazeY() {

        return mazeY;
    }

    public float getMazeSize() {

        return mazeSize;
    }
}
