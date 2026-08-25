package com.elma.braingames.puzzles.keyrescue.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.keyrescue.layout.KeyRescueLayout;
import com.elma.braingames.puzzles.keyrescue.manager.KeyRescueGameManager;
import com.elma.braingames.puzzles.keyrescue.model.KeyRescueBlock;

public class KeyRescueRenderer {

    private final ShapeRenderer shapeRenderer;

    private final KeyRescueGameManager gameManager;
    private final KeyRescueLayout layout;


    public KeyRescueRenderer(
        KeyRescueGameManager gameManager,
        KeyRescueLayout layout
    ) {

        this.gameManager = gameManager;
        this.layout = layout;

        shapeRenderer = new ShapeRenderer();
    }


    public void render(
        OrthographicCamera camera
    ) {

        shapeRenderer.setProjectionMatrix(
            camera.combined
        );

        drawBoard();

        drawExit();

        drawBlocks();
    }


    private void drawBoard() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            new Color(
                0.42f,
                0.25f,
                0.12f,
                1f
            )
        );

        shapeRenderer.rect(
            layout.getBoardX(),
            layout.getBoardY(),
            layout.getBoardSize(),
            layout.getBoardSize()
        );

        shapeRenderer.end();


        drawGrid();
    }


    private void drawGrid() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Line
        );

        shapeRenderer.setColor(
            new Color(
                0.18f,
                0.10f,
                0.05f,
                1f
            )
        );

        float cellSize =
            layout.getCellSize();

        float boardX =
            layout.getBoardX();

        float boardY =
            layout.getBoardY();

        for (
            int i = 0;
            i <= 6;
            i++
        ) {

            float x =
                boardX
                    + i * cellSize;

            shapeRenderer.line(
                x,
                boardY,
                x,
                boardY
                    + layout.getBoardSize()
            );
        }


        for (
            int i = 0;
            i <= 6;
            i++
        ) {

            float y =
                boardY
                    + i * cellSize;

            shapeRenderer.line(
                boardX,
                y,
                boardX
                    + layout.getBoardSize(),
                y
            );
        }

        shapeRenderer.end();
    }


    private void drawExit() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            new Color(
                0.85f,
                0.75f,
                0.30f,
                1f
            )
        );

        float exitX =
            layout.getBoardRight();

        float exitY =
            layout.getBoardY()
                + layout.getCellSize() * 2f;

        shapeRenderer.rect(
            exitX,
            exitY,
            layout.getExitWidth(),
            layout.getExitHeight()
        );

        shapeRenderer.end();
    }


    private void drawBlocks() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        for (
            KeyRescueBlock block
            : gameManager
            .getBoard()
            .getBlocks()
        ) {

            drawBlock(block);
        }

        shapeRenderer.end();
    }


    private void drawBlock(
        KeyRescueBlock block
    ) {

        float cellSize =
            layout.getCellSize();

        float x =
            layout.getCellX(
                block.getCol()
            );

        float y =
            layout.getCellY(
                block.getRow()
            );

        float width;
        float height;


        if (
            block.isHorizontal()
        ) {

            width =
                cellSize
                    * block.getLength();

            height =
                cellSize
                    * 0.82f;

            y +=
                cellSize * 0.09f;

        } else {

            width =
                cellSize * 0.82f;

            height =
                cellSize
                    * block.getLength();

            x +=
                cellSize * 0.09f;
        }


        if (
            block.isKeyBlock()
        ) {

            shapeRenderer.setColor(
                new Color(
                    Color.CYAN
                )
            );

        } else if (
            block.getId() % 2 == 0
        ) {

            shapeRenderer.setColor(
                Color.BLACK
            );

        } else {

            shapeRenderer.setColor(
                Color.GOLD
            );
        }


        shapeRenderer.rect(
            x,
            y,
            width,
            height
        );
    }


    public void dispose() {

        shapeRenderer.dispose();
    }
}
