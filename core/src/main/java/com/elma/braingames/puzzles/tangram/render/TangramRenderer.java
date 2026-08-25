package com.elma.braingames.puzzles.tangram.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.tangram.layout.TangramLayout;
import com.elma.braingames.puzzles.tangram.manager.TangramGameManager;
import com.elma.braingames.puzzles.tangram.model.TangramBoard;
import com.elma.braingames.puzzles.tangram.model.TangramPiece;


public class TangramRenderer {


    private final ShapeRenderer shapeRenderer;

    private final TangramGameManager gameManager;

    private final TangramLayout layout;

    private final Color outlineColor =
        new Color(
            0.08f,
            0.08f,
            0.10f,
            1f
        );

    private final Color targetColor =
        new Color(
            0.01f,
            0.01f,
            0.015f,
            1f
        );

    private final Color targetBackgroundColor =
        new Color(
            0.92f,
            0.92f,
            0.94f,
            1f
        );


    public TangramRenderer(
        TangramGameManager gameManager,
        TangramLayout layout
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

        shapeRenderer.setProjectionMatrix(
            camera.combined
        );


        drawTarget();

        drawPieces();
    }

    private void drawTarget() {

        float centerX =
            layout.getTargetCenterX();

        float centerY =
            layout.getTargetCenterY();

        float size =
            layout.getTargetSize();

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            targetBackgroundColor
        );

        shapeRenderer.rect(
            centerX - size * 0.62f,
            centerY - size * 0.48f,
            size * 1.24f,
            size * 0.96f
        );

        shapeRenderer.end();

        drawTargetPieces();
    }


    private void drawTargetPieces() {

        TangramBoard board =
            gameManager.getBoard();


        for (
            TangramPiece piece :
            board.getPieces()
        ) {

            drawTargetPiece(
                piece
            );
        }
    }


    private void drawTargetPiece(
        TangramPiece piece
    ) {

        float x =
            piece.getTargetX();

        float y =
            piece.getTargetY();

        float rotation =
            piece.getTargetRotation();


        float width =
            layout.getPieceWidth(
                piece.getType()
            );

        float height =
            layout.getPieceHeight(
                piece.getType()
            );


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            targetColor
        );


        switch (
            piece.getType()
        ) {

            case LARGE_TRIANGLE_1:
            case LARGE_TRIANGLE_2:

                drawTriangle(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case MEDIUM_TRIANGLE:

                drawTriangle(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case SMALL_TRIANGLE_1:
            case SMALL_TRIANGLE_2:

                drawTriangle(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case SQUARE:

                drawSquare(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case PARALLELOGRAM:

                drawParallelogram(
                    x,
                    y,
                    width,
                    height,
                    rotation
                );

                break;
        }


        shapeRenderer.end();
    }

    private void drawPieces() {

        TangramBoard board =
            gameManager.getBoard();


        for (
            TangramPiece piece :
            board.getPieces()
        ) {

            drawPiece(
                piece
            );
        }
    }


    private void drawPiece(
        TangramPiece piece
    ) {

        float x =
            piece.getX();

        float y =
            piece.getY();

        float rotation =
            piece.getRotation();


        float width =
            layout.getPieceWidth(
                piece.getType()
            );

        float height =
            layout.getPieceHeight(
                piece.getType()
            );


        Color pieceColor =
            piece.getColor();

        if (
            piece.isPlaced()
        ) {

            pieceColor =
                new Color(
                    pieceColor.r * 0.88f,
                    pieceColor.g * 0.88f,
                    pieceColor.b * 0.88f,
                    1f
                );
        }

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            pieceColor
        );


        switch (
            piece.getType()
        ) {

            case LARGE_TRIANGLE_1:
            case LARGE_TRIANGLE_2:

                drawTriangle(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case MEDIUM_TRIANGLE:

                drawTriangle(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case SMALL_TRIANGLE_1:
            case SMALL_TRIANGLE_2:

                drawTriangle(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case SQUARE:

                drawSquare(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case PARALLELOGRAM:

                drawParallelogram(
                    x,
                    y,
                    width,
                    height,
                    rotation
                );

                break;
        }


        shapeRenderer.end();

        drawPieceOutline(
            piece
        );
    }

    private void drawTriangle(
        float cx,
        float cy,
        float size,
        float rotation
    ) {

        float half =
            size / 2f;


        float[] points =
            new float[] {

                cx - half,
                cy - half,

                cx + half,
                cy - half,

                cx - half,
                cy + half
            };


        rotatePoints(
            points,
            cx,
            cy,
            rotation
        );


        shapeRenderer.triangle(
            points[0],
            points[1],

            points[2],
            points[3],

            points[4],
            points[5]
        );
    }

    private void drawSquare(
        float cx,
        float cy,
        float size,
        float rotation
    ) {

        float half =
            size / 2f;


        float[] points =
            new float[] {

                cx - half,
                cy - half,

                cx + half,
                cy - half,

                cx + half,
                cy + half,

                cx - half,
                cy + half
            };


        rotatePoints(
            points,
            cx,
            cy,
            rotation
        );


        shapeRenderer.triangle(
            points[0],
            points[1],

            points[2],
            points[3],

            points[4],
            points[5]
        );


        shapeRenderer.triangle(
            points[0],
            points[1],

            points[4],
            points[5],

            points[6],
            points[7]
        );
    }

    private void drawParallelogram(
        float cx,
        float cy,
        float width,
        float height,
        float rotation
    ) {

        float skew =
            width * 0.25f;


        float[] points =
            new float[] {

                cx - width / 2f + skew,
                cy - height / 2f,

                cx + width / 2f + skew,
                cy - height / 2f,

                cx + width / 2f - skew,
                cy + height / 2f,

                cx - width / 2f - skew,
                cy + height / 2f
            };


        rotatePoints(
            points,
            cx,
            cy,
            rotation
        );

        shapeRenderer.triangle(
            points[0],
            points[1],

            points[2],
            points[3],

            points[4],
            points[5]
        );


        shapeRenderer.triangle(
            points[0],
            points[1],

            points[4],
            points[5],

            points[6],
            points[7]
        );
    }

    private void rotatePoints(
        float[] points,
        float cx,
        float cy,
        float degrees
    ) {

        double radians =
            Math.toRadians(
                degrees
            );


        float cos =
            (float)
                Math.cos(
                    radians
                );

        float sin =
            (float)
                Math.sin(
                    radians
                );


        for (
            int i = 0;
            i < points.length;
            i += 2
        ) {

            float px =
                points[i];

            float py =
                points[i + 1];


            float dx =
                px - cx;

            float dy =
                py - cy;


            points[i] =
                cx
                    +
                    dx * cos
                    -
                    dy * sin;


            points[i + 1] =
                cy
                    +
                    dx * sin
                    +
                    dy * cos;
        }
    }

    private void drawPieceOutline(
        TangramPiece piece
    ) {

        float x =
            piece.getX();

        float y =
            piece.getY();


        float width =
            layout.getPieceWidth(
                piece.getType()
            );

        float height =
            layout.getPieceHeight(
                piece.getType()
            );


        float rotation =
            piece.getRotation();


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Line
        );

        shapeRenderer.setColor(
            outlineColor
        );


        switch (
            piece.getType()
        ) {

            case LARGE_TRIANGLE_1:
            case LARGE_TRIANGLE_2:
            case MEDIUM_TRIANGLE:
            case SMALL_TRIANGLE_1:
            case SMALL_TRIANGLE_2:

                drawTriangleOutline(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case SQUARE:

                drawSquareOutline(
                    x,
                    y,
                    width,
                    rotation
                );

                break;


            case PARALLELOGRAM:

                drawParallelogramOutline(
                    x,
                    y,
                    width,
                    height,
                    rotation
                );

                break;
        }


        shapeRenderer.end();
    }

    private void drawTriangleOutline(
        float cx,
        float cy,
        float size,
        float rotation
    ) {

        float half =
            size / 2f;


        float[] points =
            new float[] {

                cx - half,
                cy - half,

                cx + half,
                cy - half,

                cx - half,
                cy + half
            };


        rotatePoints(
            points,
            cx,
            cy,
            rotation
        );


        shapeRenderer.line(
            points[0],
            points[1],

            points[2],
            points[3]
        );


        shapeRenderer.line(
            points[2],
            points[3],

            points[4],
            points[5]
        );


        shapeRenderer.line(
            points[4],
            points[5],

            points[0],
            points[1]
        );
    }

    private void drawSquareOutline(
        float cx,
        float cy,
        float size,
        float rotation
    ) {

        float half =
            size / 2f;


        float[] points =
            new float[] {

                cx - half,
                cy - half,

                cx + half,
                cy - half,

                cx + half,
                cy + half,

                cx - half,
                cy + half
            };


        rotatePoints(
            points,
            cx,
            cy,
            rotation
        );


        drawClosedPolygon(
            points
        );
    }

    private void drawParallelogramOutline(
        float cx,
        float cy,
        float width,
        float height,
        float rotation
    ) {

        float skew =
            width * 0.25f;


        float[] points =
            new float[] {

                cx - width / 2f + skew,
                cy - height / 2f,

                cx + width / 2f + skew,
                cy - height / 2f,

                cx + width / 2f - skew,
                cy + height / 2f,

                cx - width / 2f - skew,
                cy + height / 2f
            };


        rotatePoints(
            points,
            cx,
            cy,
            rotation
        );


        drawClosedPolygon(
            points
        );
    }

    private void drawClosedPolygon(
        float[] points
    ) {

        int count =
            points.length / 2;


        for (
            int i = 0;
            i < count;
            i++
        ) {

            int next =
                (i + 1) % count;


            shapeRenderer.line(
                points[i * 2],
                points[i * 2 + 1],

                points[next * 2],
                points[next * 2 + 1]
            );
        }
    }

    public void dispose() {

        if (
            shapeRenderer != null
        ) {

            shapeRenderer.dispose();
        }
    }
}
