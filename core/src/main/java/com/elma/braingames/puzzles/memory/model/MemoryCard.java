package com.elma.braingames.puzzles.memory.model;

public class MemoryCard {

    private final int pairId;

    private boolean flipped;

    private boolean matched;

    private float x;

    private float y;

    private float width;

    private float height;

    private float animationScale = 1f;

    private boolean bouncing;

    private float bounceTime;
    private float flipProgress = 0f;

    private boolean flipping = false;

    private boolean flipForward = true;

    public MemoryCard(int pairId) {

        this.pairId = pairId;

    }

    public int getPairId() {

        return pairId;

    }

    public boolean isFlipped() {

        return flipped;

    }

    public void setFlipped(boolean flipped) {

        this.flipped = flipped;

    }

    public boolean isMatched() {

        return matched;

    }

    public void setMatched(boolean matched) {

        this.matched = matched;

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }


    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
    public float getFlipProgress() {
        return flipProgress;
    }

    public void setFlipProgress(float flipProgress) {
        this.flipProgress = flipProgress;
    }

    public boolean isFlipping() {
        return flipping;
    }

    public void setFlipping(boolean flipping) {
        this.flipping = flipping;
    }

    public boolean isFlipForward() {
        return flipForward;
    }

    public void setFlipForward(boolean flipForward) {
        this.flipForward = flipForward;
    }
    public float getAnimationScale() {
        return animationScale;
    }

    public void setAnimationScale(float animationScale) {
        this.animationScale = animationScale;
    }

    public boolean isBouncing() {
        return bouncing;
    }

    public void setBouncing(boolean bouncing) {
        this.bouncing = bouncing;
    }

    public float getBounceTime() {
        return bounceTime;
    }

    public void setBounceTime(float bounceTime) {
        this.bounceTime = bounceTime;
    }

}
