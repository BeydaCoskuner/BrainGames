package com.elma.braingames.puzzles.maze.model;

public class MazeBall {

    private float x;
    private float y;

    private final float radius;

    public MazeBall(
        float x,
        float y,
        float radius
    ) {

        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public void setPosition(
        float x,
        float y
    ) {

        this.x = x;
        this.y = y;
    }
}
