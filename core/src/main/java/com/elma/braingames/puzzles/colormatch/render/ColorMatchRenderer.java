package com.elma.braingames.puzzles.colormatch.render;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.elma.braingames.puzzles.colormatch.manager.ColorMatchGameManager;
import com.elma.braingames.puzzles.colormatch.models.ColorCircle;

public class ColorMatchRenderer {

    private final ShapeRenderer shapeRenderer;

    private final float lineWidth = 24f;


    public ColorMatchRenderer() {

        shapeRenderer =
            new ShapeRenderer();
    }


    public void render(
        OrthographicCamera camera,
        ColorMatchGameManager gameManager,
        Vector2 currentTouch
    ) {

        shapeRenderer.setProjectionMatrix(
            camera.combined
        );



        drawCompletedLines(
            gameManager.getCompletedLines()
        );



        if (
            gameManager.isDrawing()
                && gameManager.getActiveLinePoints() != null
        ) {

            drawActiveLine(
                gameManager.getActiveLinePoints(),
                gameManager.getActiveColor()
            );
        }


        drawCircles(
            gameManager.getCircles()
        );
    }


    private void drawCompletedLines(
        Array<ColorMatchGameManager.ColorLine> lines
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        for (
            ColorMatchGameManager.ColorLine line
            : lines
        ) {

            shapeRenderer.setColor(
                line.getColor()
            );


            Array<Vector2> points =
                line.getPoints();


            drawPoints(
                points
            );
        }


        shapeRenderer.end();
    }


    private void drawActiveLine(
        Array<Vector2> points,
        Color color
    ) {

        if (
            points == null
                || points.size < 2
        ) {

            return;
        }


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            color
        );


        drawPoints(
            points
        );


        shapeRenderer.end();
    }


    private void drawPoints(
        Array<Vector2> points
    ) {

        if (
            points == null
                || points.size < 2
        ) {

            return;
        }


        for (
            int i = 0;
            i < points.size - 1;
            i++
        ) {

            Vector2 start =
                points.get(i);

            Vector2 end =
                points.get(i + 1);


            drawThickLine(
                start,
                end,
                lineWidth
            );
        }
    }


    private void drawCircles(
        Array<ColorCircle> circles
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        for (
            ColorCircle circle
            : circles
        ) {

            shapeRenderer.setColor(
                circle.getColor()
            );


            shapeRenderer.circle(
                circle.getX(),
                circle.getY(),
                circle.getRadius()
            );
        }


        shapeRenderer.end();

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Line
        );


        shapeRenderer.setColor(
            Color.WHITE
        );


        for (
            ColorCircle circle
            : circles
        ) {

            shapeRenderer.circle(
                circle.getX(),
                circle.getY(),
                circle.getRadius()
            );
        }


        shapeRenderer.end();
    }


    private void drawThickLine(
        Vector2 start,
        Vector2 end,
        float width
    ) {

        float dx =
            end.x - start.x;

        float dy =
            end.y - start.y;


        float length =
            (float) Math.sqrt(
                dx * dx +
                    dy * dy
            );


        if (length == 0f) {

            shapeRenderer.circle(
                start.x,
                start.y,
                width / 2f
            );

            return;
        }


        float nx =
            -dy / length;

        float ny =
            dx / length;


        float halfWidth =
            width / 2f;


        float x1 =
            start.x +
                nx * halfWidth;

        float y1 =
            start.y +
                ny * halfWidth;


        float x2 =
            start.x -
                nx * halfWidth;

        float y2 =
            start.y -
                ny * halfWidth;


        float x3 =
            end.x -
                nx * halfWidth;

        float y3 =
            end.y -
                ny * halfWidth;


        float x4 =
            end.x +
                nx * halfWidth;

        float y4 =
            end.y +
                ny * halfWidth;


        shapeRenderer.triangle(
            x1,
            y1,
            x2,
            y2,
            x3,
            y3
        );


        shapeRenderer.triangle(
            x1,
            y1,
            x3,
            y3,
            x4,
            y4
        );


        shapeRenderer.circle(
            start.x,
            start.y,
            halfWidth
        );


        shapeRenderer.circle(
            end.x,
            end.y,
            halfWidth
        );
    }


    public void dispose() {

        shapeRenderer.dispose();
    }
}
