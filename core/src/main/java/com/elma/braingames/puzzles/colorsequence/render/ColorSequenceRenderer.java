package com.elma.braingames.puzzles.colorsequence.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.colorsequence.layout.ColorSequenceLayout;
import com.elma.braingames.puzzles.colorsequence.manager.ColorSequenceGameManager;
import com.elma.braingames.puzzles.colorsequence.model.ColorSequenceBoard;
import com.elma.braingames.puzzles.colorsequence.model.ColorSequenceButton;


public class ColorSequenceRenderer {


    private final ShapeRenderer shapeRenderer;

    private final ColorSequenceLayout layout;


    public ColorSequenceRenderer(
        ColorSequenceLayout layout
    ) {

        this.layout =
            layout;

        shapeRenderer =
            new ShapeRenderer();
    }


    public void render(
        OrthographicCamera camera,
        ColorSequenceGameManager gameManager
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

        drawButtons(
            gameManager
        );
    }


    private void drawBoardBackground() {

        float x =
            layout.getBoardX();

        float y =
            layout.getBoardY();

        float width =
            layout.getBoardWidth();

        float height =
            layout.getBoardHeight();


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            new Color(
                1f,
                1f,
                1f,
                1f
            )
        );


        shapeRenderer.rect(
            x,
            y,
            width,
            height
        );


        shapeRenderer.end();
    }


    private void drawButtons(
        ColorSequenceGameManager gameManager
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        ColorSequenceBoard board =
            gameManager.getBoard();


        for (
            int i = 0;
            i < ColorSequenceBoard.BUTTON_COUNT;
            i++
        ) {

            ColorSequenceButton button =
                board.getButton(i);


            int row =
                button.getRow();

            int col =
                button.getCol();


            float x =
                layout.getButtonX(col);

            float y =
                layout.getButtonY(row);

            float width =
                layout.getButtonWidth();

            float height =
                layout.getButtonHeight();


            if (
                button.isActive()
            ) {

                drawActiveButton(
                    button,
                    x,
                    y,
                    width,
                    height
                );

            } else {

                drawInactiveButton(
                    button,
                    x,
                    y,
                    width,
                    height
                );
            }
        }


        shapeRenderer.end();
    }


    private void drawInactiveButton(
        ColorSequenceButton button,
        float x,
        float y,
        float width,
        float height
    ) {

        Color baseColor =
            button.getColor();


        Color darkColor =
            new Color(
                baseColor.r * 0.70f,
                baseColor.g * 0.70f,
                baseColor.b * 0.70f,
                1f
            );


        shapeRenderer.setColor(
            darkColor
        );


        float radius =
            width * 0.14f;


        drawRoundedRectangle(
            x,
            y,
            width,
            height,
            radius
        );
    }


    private void drawActiveButton(
        ColorSequenceButton button,
        float x,
        float y,
        float width,
        float height
    ) {

        Color baseColor =
            button.getColor();


        float radius =
            width * 0.14f;

        shapeRenderer.setColor(
            new Color(
                baseColor.r,
                baseColor.g,
                baseColor.b,
                0.22f
            )
        );


        drawRoundedRectangle(
            x - width * 0.035f,
            y - height * 0.035f,
            width * 1.07f,
            height * 1.07f,
            radius * 1.15f
        );

        shapeRenderer.setColor(
            baseColor
        );


        drawRoundedRectangle(
            x,
            y,
            width,
            height,
            radius
        );

        Color highlight =
            new Color(
                1f,
                1f,
                1f,
                0.12f
            );


        shapeRenderer.setColor(
            highlight
        );


        drawRoundedRectangle(
            x + width * 0.08f,
            y + height * 0.08f,
            width * 0.84f,
            height * 0.84f,
            radius * 0.75f
        );
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
    }
}
