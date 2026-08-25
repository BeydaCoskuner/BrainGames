package com.elma.braingames.puzzles.laser.manager;


import com.elma.braingames.puzzles.laser.model.LaserBoard;
import com.elma.braingames.puzzles.laser.model.LaserMirror;
import com.elma.braingames.puzzles.laser.model.LaserSource;
import com.elma.braingames.puzzles.laser.model.LaserTarget;

import java.util.ArrayList;
import java.util.List;

public class LaserGameManager {

    private final LaserBoard board;

    private final List<LaserPoint> laserPath;

    private float elapsedTime;

    private boolean completed;


    public LaserGameManager() {

        board =
            new LaserBoard();

        laserPath =
            new ArrayList<>();

        elapsedTime = 0f;

        completed = false;

        calculateLaserPath();
    }


    public void update(
        float delta
    ) {

        if (completed) {
            return;
        }

        elapsedTime += delta;

        calculateLaserPath();
    }


    private void calculateLaserPath() {

        laserPath.clear();


        LaserSource source =
            board.getSource();


        int row =
            source.getRow();

        int col =
            source.getCol();


        Direction direction =
            convertDirection(
                source.getDirection()
            );

        laserPath.add(
            new LaserPoint(
                row,
                col
            )
        );
        //max adım
        int maxSteps =
            LaserBoard.ROWS
                * LaserBoard.COLS
                * 4;


        for (
            int step = 0;
            step < maxSteps;
            step++
        ) {

            int nextRow =
                row + direction.row;

            int nextCol =
                col + direction.col;


            if (
                nextRow < 0
                    ||
                    nextRow >= LaserBoard.ROWS
                    ||
                    nextCol < 0
                    ||
                    nextCol >= LaserBoard.COLS
            ) {

                break;
            }


            row = nextRow;
            col = nextCol;


            laserPath.add(
                new LaserPoint(
                    row,
                    col
                )
            );

            if (
                isTarget(
                    row,
                    col
                )
            ) {

                completed = true;

                break;
            }

            LaserMirror mirror =
                getMirrorAt(
                    row,
                    col
                );


            if (mirror != null) {

                direction =
                    reflectLaser(
                        direction,
                        mirror
                    );
            }
        }
    }


    private Direction convertDirection(
        LaserSource.Direction direction
    ) {

        switch (direction) {

            case UP:
                return Direction.UP;

            case DOWN:
                return Direction.DOWN;

            case LEFT:
                return Direction.LEFT;

            case RIGHT:
                return Direction.RIGHT;

            default:
                return Direction.RIGHT;
        }
    }


    private Direction reflectLaser(
        Direction direction,
        LaserMirror mirror
    ) {

        switch (
            mirror.getOrientation()
        ) {
            // teerse
            case SLASH:

                switch (direction) {

                    case UP:
                        return Direction.RIGHT;

                    case RIGHT:
                        return Direction.UP;

                    case DOWN:
                        return Direction.LEFT;

                    case LEFT:
                        return Direction.DOWN;
                }

                break;



            case BACKSLASH:

                switch (direction) {

                    case UP:
                        return Direction.LEFT;

                    case LEFT:
                        return Direction.UP;

                    case DOWN:
                        return Direction.RIGHT;

                    case RIGHT:
                        return Direction.DOWN;
                }

                break;


            //dikey ayne
            case VERTICAL:

                if (
                    direction == Direction.LEFT
                ) {

                    return Direction.RIGHT;

                }

                if (
                    direction == Direction.RIGHT
                ) {

                    return Direction.LEFT;
                }

                return direction;

                //yatay
            case HORIZONTAL:

                if (
                    direction == Direction.UP
                ) {

                    return Direction.DOWN;

                }

                if (
                    direction == Direction.DOWN
                ) {

                    return Direction.UP;
                }

                return direction;
        }


        return direction;
    }


    private LaserMirror getMirrorAt(
        int row,
        int col
    ) {

        for (
            LaserMirror mirror
            : board.getMirrors()
        ) {

            if (
                mirror.getRow() == row
                    &&
                    mirror.getCol() == col
            ) {

                return mirror;
            }
        }

        return null;
    }


    private boolean isTarget(
        int row,
        int col
    ) {

        LaserTarget target =
            board.getTarget();


        return target.getRow() == row
            &&
            target.getCol() == col;
    }

    public boolean rotateMirror(
        int row,
        int col
    ) {

        if (completed) {
            return false;
        }

        LaserMirror mirror =
            getMirrorAt(
                row,
                col
            );

        if (mirror == null) {
            return false;
        }

        mirror.rotateClockwise();

        calculateLaserPath();

        return true;
    }

    public LaserBoard getBoard() {

        return board;
    }


    public List<LaserPoint> getLaserPath() {

        return laserPath;
    }


    public float getElapsedTime() {

        return elapsedTime;
    }


    public boolean isCompleted() {

        return completed;
    }


    public enum Direction {

        UP(1, 0),
        DOWN(-1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);


        private final int row;
        private final int col;


        Direction(
            int row,
            int col
        ) {

            this.row = row;
            this.col = col;
        }
    }


    public static class LaserPoint {

        private final int row;
        private final int col;


        public LaserPoint(
            int row,
            int col
        ) {

            this.row = row;
            this.col = col;
        }


        public int getRow() {

            return row;
        }


        public int getCol() {

            return col;
        }
    }
}
