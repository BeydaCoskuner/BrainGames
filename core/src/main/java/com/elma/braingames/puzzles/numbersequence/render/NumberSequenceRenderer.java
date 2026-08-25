package com.elma.braingames.puzzles.numbersequence.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.numbersequence.layout.NumberSequenceLayout;
import com.elma.braingames.puzzles.numbersequence.manager.NumberSequenceGameManager;
import com.elma.braingames.puzzles.numbersequence.model.NumberSequenceBoard;
import com.elma.braingames.puzzles.numbersequence.model.NumberSequenceCell;

public class NumberSequenceRenderer {

    private final ShapeRenderer shapeRenderer;

    private final SpriteBatch batch;

    private final BitmapFont font;

    private final GlyphLayout glyphLayout;

    private final Color BOARD_COLOR =
        new Color(
            1f,
            1f,
            1f,
            1f
        );

    private final Color BUTTON_COLOR =
        new Color(
            0.05f,
            0.78f,
            0.82f,
            1f
        );

    private final Color COMPLETED_COLOR =
        new Color(
            0.18f,
            0.82f,
            0.38f,
            1f
        );

    private final Color WRONG_COLOR =
        new Color(
            0.95f,
            0.15f,
            0.12f,
            1f
        );

    private final Color TEXT_COLOR =
        new Color(
            0.015f,
            0.015f,
            0.02f,
            1f
        );


    public NumberSequenceRenderer() {

        shapeRenderer =
            new ShapeRenderer();

        batch =
            new SpriteBatch();

        font =
            new BitmapFont();

        font.getData().setScale(2.4f);

        glyphLayout =
            new GlyphLayout();
    }


    public void render(
        OrthographicCamera camera,
        NumberSequenceGameManager gameManager,
        NumberSequenceLayout layout
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

        batch.setProjectionMatrix(
            camera.combined
        );


        drawBoard(layout);

        drawCells(
            gameManager,
            layout
        );

        drawNextMove(
            gameManager,
            layout
        );
    }


    private void drawBoard(
        NumberSequenceLayout layout
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            BOARD_COLOR
        );


        shapeRenderer.rect(
            layout.getBoardX() - 12f,
            layout.getBoardY() - 12f,
            layout.getBoardWidth() + 24f,
            layout.getBoardHeight() + 24f
        );


        shapeRenderer.end();
    }


    private void drawCells(
        NumberSequenceGameManager gameManager,
        NumberSequenceLayout layout
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        NumberSequenceBoard board =
            gameManager.getBoard();


        for (
            NumberSequenceCell cell :
            board.getCells()
        ) {

            float x =
                layout.getCellX(
                    cell.getCol()
                );

            float y =
                layout.getCellY(
                    cell.getRow()
                );

            float width =
                layout.getCellWidth();

            float height =
                layout.getCellHeight();


            if (
                cell.isWrongFlash()
            ) {

                shapeRenderer.setColor(
                    WRONG_COLOR
                );

            } else if (
                cell.isCompleted()
            ) {

                shapeRenderer.setColor(
                    COMPLETED_COLOR
                );

            } else {

                shapeRenderer.setColor(
                    BUTTON_COLOR
                );
            }


            drawRoundedRectangle(
                x,
                y,
                width,
                height,
                width * 0.16f
            );
        }


        shapeRenderer.end();

        batch.begin();


        for (
            NumberSequenceCell cell :
            board.getCells()
        ) {

            float x =
                layout.getCellCenterX(
                    cell.getCol()
                );

            float y =
                layout.getCellCenterY(
                    cell.getRow()
                );


            String text =
                String.valueOf(
                    cell.getNumber()
                );


            glyphLayout.setText(
                font,
                text
            );


            font.setColor(
                TEXT_COLOR
            );


            font.draw(
                batch,
                text,
                x - glyphLayout.width / 2f,
                y + glyphLayout.height / 2f
            );
        }


        batch.end();
    }


    private void drawNextMove(
        NumberSequenceGameManager gameManager,
        NumberSequenceLayout layout
    ) {

        batch.begin();


        String text;


        if (
            gameManager.isCompleted()
        ) {

            text =
                "COMPLETE";

        } else {

            text =
                "NEXT MOVE: "
                    +
                    gameManager.getNextNumber();
        }


        glyphLayout.setText(
            font,
            text
        );


        font.setColor(
            Color.BLACK
        );


        font.draw(
            batch,
            text,
            layout.getNextMoveX()
                - glyphLayout.width / 2f,

            layout.getNextMoveY()
        );


        batch.end();
    }


    private void drawRoundedRectangle(
        float x,
        float y,
        float width,
        float height,
        float radius
    ) {

        shapeRenderer.rect(
            x + radius,
            y,
            width - radius * 2f,
            height
        );

        shapeRenderer.rect(
            x,
            y + radius,
            width,
            height - radius * 2f
        );


        shapeRenderer.circle(
            x + radius,
            y + radius,
            radius
        );


        shapeRenderer.circle(
            x + width - radius,
            y + radius,
            radius
        );


        shapeRenderer.circle(
            x + radius,
            y + height - radius,
            radius
        );


        shapeRenderer.circle(
            x + width - radius,
            y + height - radius,
            radius
        );
    }


    public void dispose() {

        shapeRenderer.dispose();

        batch.dispose();

        font.dispose();
    }
}
