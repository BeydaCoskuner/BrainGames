package com.elma.braingames.puzzles.maze.model;

public class MazeCell {

    private boolean topWall;
    private boolean rightWall;
    private boolean bottomWall;
    private boolean leftWall;

    private boolean visited;

    public MazeCell() {

        topWall = true;
        rightWall = true;
        bottomWall = true;
        leftWall = true;

        visited = false;
    }

    public boolean hasTopWall() {
        return topWall;
    }

    public boolean hasRightWall() {
        return rightWall;
    }

    public boolean hasBottomWall() {
        return bottomWall;
    }

    public boolean hasLeftWall() {
        return leftWall;
    }

    public void setTopWall(boolean value) {
        topWall = value;
    }

    public void setRightWall(boolean value) {
        rightWall = value;
    }

    public void setBottomWall(boolean value) {
        bottomWall = value;
    }

    public void setLeftWall(boolean value) {
        leftWall = value;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}
