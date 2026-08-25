package com.elma.braingames.puzzles.colormatch.models;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ColorCircle {

    private final Color color;

    private final Vector2 position;

    private final float radius;

    private final boolean leftSide;

    private boolean matched;

    public ColorCircle(
        Color color,
        float x,
        float y,
        float radius,
        boolean leftSide
    ) {

        this.color = new Color(color);

        this.position = new Vector2(x, y);

        this.radius = radius;

        this.leftSide = leftSide;

        this.matched = false;
    }

    public Color getColor() {

        return color;
    }

    public Vector2 getPosition() {

        return position;
    }

    public float getX() {

        return position.x;
    }

    public float getY() {

        return position.y;
    }

    public float getRadius() {

        return radius;
    }

    public boolean isLeftSide() {

        return leftSide;
    }

    public boolean isMatched() {

        return matched;
    }

    public void setMatched(boolean matched) {

        this.matched = matched;
    }

    public boolean contains(
        float x,
        float y
    ) {

        float dx = x - position.x;
        float dy = y - position.y;

        return dx * dx + dy * dy
            <= radius * radius;
    }

    public boolean contains(Vector2 point) {

        return contains(
            point.x,
            point.y
        );
    }

    public void reset() {

        matched = false;
    }
}
