package com.elma.braingames.puzzles.hanoi.layout;

public class HanoiLayout {

    private float boardWidth;
    private float boardHeight;

    private float rodY;

    private float rodHeight;

    private float rodWidth;

    private float rod1X;
    private float rod2X;
    private float rod3X;

    private float diskHeight;

    private float baseY;


    public void update(
        float width,
        float height
    ) {

        boardWidth = width;
        boardHeight = height;


        rodHeight =
            height * 0.30f;


        baseY =
            height * 0.35f;

        rodY =
            baseY;

        rodWidth =
            Math.max(
                7f,
                width * 0.015f
            );

        rod1X =
            width * 0.25f;

        rod2X =
            width * 0.50f;

        rod3X =
            width * 0.75f;


        diskHeight =
            Math.min(
                width,
                height
            ) * 0.055f;
    }


    public float getRod1X() {

        return rod1X;
    }


    public float getRod2X() {

        return rod2X;
    }


    public float getRod3X() {

        return rod3X;
    }


    public float getRodY() {

        return rodY;
    }


    public float getRodHeight() {

        return rodHeight;
    }


    public float getRodWidth() {

        return rodWidth;
    }


    public float getDiskHeight() {

        return diskHeight;
    }


    public float getBaseY() {

        return baseY;
    }


    public float getBoardWidth() {

        return boardWidth;
    }


    public float getBoardHeight() {

        return boardHeight;
    }


    public float getRodX(int rodIndex) {

        switch (rodIndex) {

            case 0:
                return rod1X;

            case 1:
                return rod2X;

            case 2:
                return rod3X;

            default:
                return rod1X;
        }
    }
}
