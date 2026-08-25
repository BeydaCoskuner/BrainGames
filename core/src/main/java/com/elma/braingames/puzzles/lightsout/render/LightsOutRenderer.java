package com.elma.braingames.puzzles.lightsout.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.lightsout.layout.LightsOutLayout;
import com.elma.braingames.puzzles.lightsout.manager.LightsOutGameManager;
import com.elma.braingames.puzzles.lightsout.model.LightsOutBoard;
import com.elma.braingames.puzzles.lightsout.model.LightsOutCell;


public class LightsOutRenderer {

    private final ShapeRenderer shapeRenderer;

    private final LightsOutGameManager gameManager;

    private final LightsOutLayout layout;


    public LightsOutRenderer(
        LightsOutGameManager gameManager,
        LightsOutLayout layout
    ) {

        this.gameManager =
            gameManager;

        this.layout =
            layout;

        shapeRenderer =
            new ShapeRenderer();
    }


    public void render(
        OrthographicCamera camera
    ) {

        float width =
            camera.viewportWidth;

        float height =
            camera.viewportHeight;


        layout.update(
            width,
            height
        );


        shapeRenderer.setProjectionMatrix(
            camera.combined
        );


        drawBoardBackground();

        drawCells();
    }


    private void drawBoardBackground() {

        float boardX =
            layout.getBoardX();

        float boardY =
            layout.getBoardY();

        float boardSize =
            layout.getBoardSize();


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            new Color(
                0.025f,
                0.025f,
                0.03f,
                1f
            )
        );


        shapeRenderer.rect(
            boardX,
            boardY,
            boardSize,
            boardSize
        );


        shapeRenderer.end();
    }


    private void drawCells() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        for (
            int row = 0;
            row < LightsOutBoard.ROWS;
            row++
        ) {

            for (
                int col = 0;
                col < LightsOutBoard.COLS;
                col++
            ) {

                LightsOutCell cell =
                    gameManager
                        .getBoard()
                        .getCell(
                            row,
                            col
                        );


                if (cell.isOn()) {

                    drawOnCell(
                        row,
                        col
                    );

                } else {

                    drawOffCell(
                        row,
                        col
                    );
                }
            }
        }


        shapeRenderer.end();
    }


    private void drawOffCell(
        int row,
        int col
    ) {

        float x =
            layout.getCellX(col);

        float y =
            layout.getCellY(row);

        float size =
            layout.getCellSize();


        float radius =
            size * 0.08f;

        shapeRenderer.setColor(
            new Color(
                0.28f,
                0.30f,
                0.32f,
                1f
            )
        );


        drawRoundedCell(
            x,
            y,
            size,
            radius
        );
    }


    private void drawOnCell(
        int row,
        int col
    ) {

        float x =
            layout.getCellX(col);

        float y =
            layout.getCellY(row);

        float size =
            layout.getCellSize();


        float radius =
            size * 0.08f;

        shapeRenderer.setColor(
            new Color(
                1f,
                0.35f,
                0.02f,
                0.12f
            )
        );


        drawRoundedCell(
            x - size * 0.025f,
            y - size * 0.025f,
            size * 1.05f,
            radius * 1.2f
        );


        shapeRenderer.setColor(
            new Color(
                1f,
                0.38f,
                0.025f,
                1f
            )
        );


        drawRoundedCell(
            x,
            y,
            size,
            radius
        );

        shapeRenderer.setColor(
            new Color(
                1f,
                0.62f,
                0.18f,
                0.30f
            )
        );


        drawRoundedCell(
            x + size * 0.08f,
            y + size * 0.08f,
            size * 0.84f,
            radius * 0.75f
        );
    }


    private void drawRoundedCell(
        float x,
        float y,
        float size,
        float radius
    ) {

        shapeRenderer.rect(
            x + radius,
            y,
            size - radius * 2f,
            size
        );


        shapeRenderer.rect(
            x,
            y + radius,
            size,
            size - radius * 2f
        );

        shapeRenderer.circle(
            x + radius,
            y + radius,
            radius
        );


        shapeRenderer.circle(
            x + size - radius,
            y + radius,
            radius
        );


        shapeRenderer.circle(
            x + radius,
            y + size - radius,
            radius
        );


        shapeRenderer.circle(
            x + size - radius,
            y + size - radius,
            radius
        );
    }


    public void dispose() {

        shapeRenderer.dispose();
    }
}
