package com.elma.braingames.models;

public class Path {

    private final LevelNode start;

    private final LevelNode end;
    private final float controlX;
    private final float controlY;

    public Path(LevelNode start, LevelNode end, float controlX, float controlY) {

        this.start = start;

        this.end = end;

        this.controlX = controlX;

        this.controlY = controlY;
    }

    public LevelNode getStart() {

        return start;

    }

    public LevelNode getEnd() {

        return end;

    }
    public float getControlX() {
        return controlX;
    }

    public float getControlY() {
        return controlY;
    }


}
