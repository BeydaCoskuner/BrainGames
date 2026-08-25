package com.elma.braingames.models;

public class LevelNode {

    private final int id;

    private final float x;

    private final float y;

    private boolean unlocked;

    private int stars;

    private boolean selected;
    private float animationScale = 1f;

    public LevelNode(int id,
                     float x,
                     float y,
                     boolean unlocked,
                     int stars) {

        this.id = id;
        this.x = x;
        this.y = y;
        this.unlocked = unlocked;
        this.stars = stars;
        this.selected = false;
    }

    public int getId() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public float getAnimationScale() {
        return animationScale;
    }

    public void setAnimationScale(float animationScale) {
        this.animationScale = animationScale;
    }

}
