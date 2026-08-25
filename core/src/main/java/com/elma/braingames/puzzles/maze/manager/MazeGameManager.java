package com.elma.braingames.puzzles.maze.manager;

public class MazeGameManager {

    public static final int ROWS = 10;
    public static final int COLS = 10;

    /*
     * # = duvar
     * . = geçilebilir alan
     * S = başlangıç
     * E = çıkış
     */
    private final char[][] maze = {

        {'#','#','#','#','#','#','#','#','#','#'},
        {'S','.','.','.','#','.','.','.','.','#'},
        {'#','#','#','.','#','.','#','#','.','#'},
        {'#','.','.','.','.','.','#','.','.','#'},
        {'#','.','#','#','#','#','#','.','#','#'},
        {'#','.','.','.','.','.','.','.','.','#'},
        {'#','#','#','#','#','.','#','#','.','#'},
        {'#','.','.','.','.','.','#','.','.','#'},
        {'#','.','#','#','#','.','.','.','.','E'},
        {'#','#','#','#','#','#','#','#','#','#'}
    };

    private float ballX;
    private float ballY;

    private float exitX;
    private float exitY;

    private boolean completed;

    private float elapsedTime;

    private static final float BALL_RADIUS = 0.25f;


    public MazeGameManager() {

        completed = false;

        elapsedTime = 0f;

        findPositions();
    }


    private void findPositions() {

        for (int row = 0; row < ROWS; row++) {

            for (int col = 0; col < COLS; col++) {

                if (maze[row][col] == 'S') {

                    ballX = col + 0.5f;
                    ballY = row + 0.5f;
                }

                if (maze[row][col] == 'E') {

                    exitX = col + 0.5f;
                    exitY = row + 0.5f;
                }
            }
        }
    }


    public void update(float delta) {

        if (completed) {
            return;
        }

        elapsedTime += delta;

        checkExit();
    }


    public boolean moveBall(
        float newX,
        float newY
    ) {

        if (completed) {
            return false;
        }

        float dx = newX - ballX;
        float dy = newY - ballY;

        float distance =
            (float) Math.sqrt(
                dx * dx + dy * dy
            );

        float stepSize = 0.05f;

        int steps =
            Math.max(
                1,
                (int) Math.ceil(
                    distance / stepSize
                )
            );

        float stepX =
            dx / steps;

        float stepY =
            dy / steps;

        float currentX = ballX;
        float currentY = ballY;

        for (int i = 0; i < steps; i++) {

            float nextX =
                currentX + stepX;

            float nextY =
                currentY + stepY;

            if (!isInside(nextX, nextY)) {
                break;
            }

            if (!isWalkable(nextX, nextY)) {
                break;
            }

            currentX = nextX;
            currentY = nextY;
        }

        ballX = currentX;
        ballY = currentY;

        checkExit();

        return true;
    }


    private boolean isInside(
        float x,
        float y
    ) {

        return x >= 0
            && x < COLS
            && y >= 0
            && y < ROWS;
    }


    private boolean isWalkable(
        float x,
        float y
    ) {

        float radius = BALL_RADIUS;


        float left = x - radius;
        float right = x + radius;

        float bottom = y - radius;
        float top = y + radius;


        return isPointWalkable(left, bottom)
            && isPointWalkable(left, top)
            && isPointWalkable(right, bottom)
            && isPointWalkable(right, top);
    }
    private boolean isPointWalkable(
        float x,
        float y
    ) {

        int col = (int) x;
        int row = (int) y;

        if (
            row < 0 ||
                row >= ROWS ||
                col < 0 ||
                col >= COLS
        ) {

            return false;
        }

        return maze[row][col] != '#';
    }


    private void checkExit() {

        int ballCol = (int) ballX;
        int ballRow = (int) ballY;

        if (
            ballCol == (int) exitX &&
                ballRow == (int) exitY
        ) {

            completed = true;
        }
    }




    public char getCell(
        int row,
        int col
    ) {

        return maze[row][col];
    }


    public float getBallX() {

        return ballX;
    }


    public float getBallY() {

        return ballY;
    }


    public float getBallRadius() {

        return 0.30f;
    }


    public float getExitX() {

        return exitX;
    }


    public float getExitY() {

        return exitY;
    }


    public float getElapsedTime() {

        return elapsedTime;
    }


    public boolean isCompleted() {

        return completed;
    }


}
