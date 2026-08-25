package com.elma.braingames.puzzles.tangram.model;

import com.badlogic.gdx.graphics.Color;

public class TangramPiece {

    public enum PieceType {

        LARGE_TRIANGLE_1,
        LARGE_TRIANGLE_2,

        MEDIUM_TRIANGLE,

        SMALL_TRIANGLE_1,
        SMALL_TRIANGLE_2,

        SQUARE,

        PARALLELOGRAM
    }


    private final int id;

    private final PieceType type;

    private final Color color;


    private float x;
    private float y;

    private float rotation;


    private float targetX;
    private float targetY;

    private float targetRotation;


    private boolean placed;


    public TangramPiece(
        int id,
        PieceType type,
        Color color
    ) {

        this.id = id;

        this.type = type;

        this.color = color;

        x = 0f;
        y = 0f;

        rotation = 0f;

        targetX = 0f;
        targetY = 0f;

        targetRotation = 0f;

        placed = false;
    }


    public int getId() {

        return id;
    }


    public PieceType getType() {

        return type;
    }


    public Color getColor() {

        return color;
    }


    public float getX() {

        return x;
    }


    public float getY() {

        return y;
    }


    public void setPosition(
        float x,
        float y
    ) {

        this.x = x;
        this.y = y;
    }


    public float getRotation() {

        return rotation;
    }


    public void setRotation(
        float rotation
    ) {

        this.rotation = rotation;

        normalizeRotation();
    }


    public void rotate45() {

        rotation += 45f;

        normalizeRotation();
    }


    private void normalizeRotation() {

        while (rotation >= 360f) {

            rotation -= 360f;
        }

        while (rotation < 0f) {

            rotation += 360f;
        }
    }


    public float getTargetX() {

        return targetX;
    }


    public float getTargetY() {

        return targetY;
    }


    public float getTargetRotation() {

        return targetRotation;
    }


    public void setTarget(
        float x,
        float y,
        float rotation
    ) {

        targetX = x;

        targetY = y;

        targetRotation = rotation;
    }


    public boolean isPlaced() {

        return placed;
    }


    public void setPlaced(
        boolean placed
    ) {

        this.placed = placed;
    }
}
