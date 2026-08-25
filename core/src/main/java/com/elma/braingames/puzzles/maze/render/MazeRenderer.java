package com.elma.braingames.puzzles.maze.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.maze.manager.MazeGameManager;

public class MazeRenderer {

    private final ShapeRenderer shapeRenderer;

    private final MazeGameManager gameManager;

    private float boardX;
    private float boardY;
    private float cellSize;

    private final SpriteBatch batch;
    private final Texture wallTexture;


    public MazeRenderer(
        MazeGameManager gameManager
    ) {

        this.gameManager = gameManager;

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        wallTexture =
            new Texture("ui/maze/wall3.png");
    }


    public void render(
        OrthographicCamera camera
    ) {

        float width =
            camera.viewportWidth;

        float height =
            camera.viewportHeight;

        float boardSize =
            Math.min(width, height) * 0.85f;

        cellSize =
            boardSize /
                MazeGameManager.COLS;

        boardX =
            (width - boardSize) / 2f;

        boardY =
            (height - boardSize) / 2f;


        shapeRenderer.setProjectionMatrix(
            camera.combined
        );


        drawMaze();

        drawBall();
    }


    /*private void drawMaze() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Line
        );

        shapeRenderer.setColor(
            Color.BLACK
        );


        for (
            int row = 0;
            row < MazeGameManager.ROWS;
            row++
        ) {

            for (
                int col = 0;
                col < MazeGameManager.COLS;
                col++
            ) {

                if (
                    gameManager.getCell(row, col)
                        == '#'
                ) {

                    float x =
                        boardX +
                            col * cellSize;

                    float y =
                        boardY +
                            row * cellSize;


                    // Hücrenin üst duvarı
                    shapeRenderer.line(
                        x,
                        y + cellSize,
                        x + cellSize,
                        y + cellSize
                    );


                    // Sol duvar
                    shapeRenderer.line(
                        x,
                        y,
                        x,
                        y + cellSize
                    );


                    // Sağ duvar
                    shapeRenderer.line(
                        x + cellSize,
                        y,
                        x + cellSize,
                        y + cellSize
                    );


                    // Alt duvar
                    shapeRenderer.line(
                        x,
                        y,
                        x + cellSize,
                        y
                    );
                }
            }
        }

        shapeRenderer.end();
    }*/
    private void drawMaze() {

        batch.begin();

        for (
            int row = 0;
            row < MazeGameManager.ROWS;
            row++
        ) {

            for (
                int col = 0;
                col < MazeGameManager.COLS;
                col++
            ) {

                if (
                    gameManager.getCell(row, col)
                        == '#'
                ) {

                    float x =
                        boardX +
                            col * cellSize;

                    float y =
                        boardY +
                            row * cellSize;

                    batch.draw(
                        wallTexture,
                        x,
                        y,
                        cellSize,
                        cellSize
                    );
                }
            }
        }

        batch.end();
    }


    private void drawBall() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            Color.CYAN
        );


        float ballX =
            boardX +
                gameManager.getBallX() * cellSize;

        float ballY =
            boardY +
                gameManager.getBallY() * cellSize;

        shapeRenderer.circle(
            ballX,
            ballY,
            cellSize *
                gameManager.getBallRadius()
        );


        shapeRenderer.end();
    }


    public float getBoardX() {

        return boardX;
    }


    public float getBoardY() {

        return boardY;
    }


    public float getCellSize() {

        return cellSize;
    }


    public void dispose() {

        shapeRenderer.dispose();
    }
}
