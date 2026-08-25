package com.elma.braingames.puzzles.laser.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.puzzles.laser.layout.LaserLayout;
import com.elma.braingames.puzzles.laser.manager.LaserGameManager;
import com.elma.braingames.puzzles.laser.model.LaserBoard;
import com.elma.braingames.puzzles.laser.model.LaserMirror;
import com.elma.braingames.puzzles.laser.model.LaserSource;
import com.elma.braingames.puzzles.laser.model.LaserTarget;

public class LaserRenderer {

    private final ShapeRenderer shapeRenderer;

    private final LaserGameManager gameManager;

    private final LaserLayout layout;


    public LaserRenderer(
        LaserGameManager gameManager,
        LaserLayout layout
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


        drawBoard();

        drawLaser();

        drawMirrors();

        drawSource();

        drawTarget();
    }


    private void drawBoard() {

        float boardX = layout.getBoardX();
        float boardY = layout.getBoardY();
        float boardSize = layout.getBoardSize();
        float cellSize = layout.getCellSize();

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(Color.BLACK);

        shapeRenderer.rect(
            boardX,
            boardY,
            boardSize,
            boardSize
        );

        shapeRenderer.end();

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Line
        );

        shapeRenderer.setColor(Color.WHITE);

        for (
            int row = 0;
            row <= LaserBoard.ROWS;
            row++
        ) {

            float y =
                boardY
                    + row * cellSize;

            shapeRenderer.line(
                boardX,
                y,
                boardX + boardSize,
                y
            );
        }


        for (
            int col = 0;
            col <= LaserBoard.COLS;
            col++
        ) {

            float x =
                boardX
                    + col * cellSize;

            shapeRenderer.line(
                x,
                boardY,
                x,
                boardY + boardSize
            );
        }

        shapeRenderer.end();
    }


    private void drawMirrors() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        for (
            LaserMirror mirror
            : gameManager
            .getBoard()
            .getMirrors()
        ) {

            drawMirror(
                mirror
            );
        }


        shapeRenderer.end();
    }


    private void drawMirror(
        LaserMirror mirror
    ) {

        float centerX =
            layout.getCellCenterX(
                mirror.getCol()
            );

        float centerY =
            layout.getCellCenterY(
                mirror.getRow()
            );


        float length =
            layout.getMirrorLength();


        float thickness =
            layout.getMirrorThickness();


        shapeRenderer.setColor(
            Color.CYAN
        );


        switch (
            mirror.getOrientation()
        ) {

            case SLASH:

                shapeRenderer.rectLine(
                    centerX - length / 2f,
                    centerY - length / 2f,

                    centerX + length / 2f,
                    centerY + length / 2f,

                    thickness
                );

                break;


            case BACKSLASH:

                shapeRenderer.rectLine(
                    centerX - length / 2f,
                    centerY + length / 2f,

                    centerX + length / 2f,
                    centerY - length / 2f,

                    thickness
                );

                break;


            case VERTICAL:

                shapeRenderer.rectLine(
                    centerX,
                    centerY - length / 2f,

                    centerX,
                    centerY + length / 2f,

                    thickness
                );

                break;


            case HORIZONTAL:

                shapeRenderer.rectLine(
                    centerX - length / 2f,
                    centerY,

                    centerX + length / 2f,
                    centerY,

                    thickness
                );

                break;
        }
    }


    private void drawLaser() {

        if (
            gameManager
                .getLaserPath()
                .size() < 2
        ) {
            return;
        }


        float cellSize =
            layout.getCellSize();


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            new Color(
                1.0f,
                0.0f,
                0.0f,
                0.18f
            )
        );

        drawLaserLines(
            cellSize * 0.24f
        );

        shapeRenderer.setColor(
            new Color(
                1.0f,
                0.0f,
                0.0f,
                0.40f
            )
        );

        drawLaserLines(
            cellSize * 0.13f
        );

        shapeRenderer.setColor(
            new Color(
                1.0f,
                0.05f,
                0.05f,
                1.0f
            )
        );

        drawLaserLines(
            cellSize * 0.065f
        );

        shapeRenderer.setColor(
            Color.WHITE
        );

        drawLaserLines(
            cellSize * 0.022f
        );


        shapeRenderer.end();
    }


    private void drawLaserLines(
        float thickness
    ) {

        float cellSize =
            layout.getCellSize();


        for (
            int i = 0;
            i <
                gameManager
                    .getLaserPath()
                    .size() - 1;
            i++
        ) {

            LaserGameManager.LaserPoint point1 =
                gameManager
                    .getLaserPath()
                    .get(i);


            LaserGameManager.LaserPoint point2 =
                gameManager
                    .getLaserPath()
                    .get(i + 1);


            float x1 =
                layout.getCellCenterX(
                    point1.getCol()
                );

            float y1 =
                layout.getCellCenterY(
                    point1.getRow()
                );


            float x2 =
                layout.getCellCenterX(
                    point2.getCol()
                );

            float y2 =
                layout.getCellCenterY(
                    point2.getRow()
                );


            shapeRenderer.rectLine(
                x1,
                y1,
                x2,
                y2,
                thickness
            );
        }
    }


    private void drawSource() {

        LaserSource source =
            gameManager
                .getBoard()
                .getSource();


        float x =
            layout.getCellCenterX(
                source.getCol()
            );

        float y =
            layout.getCellCenterY(
                source.getRow()
            );


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            Color.RED
        );


        shapeRenderer.circle(
            x,
            y,
            layout.getSourceRadius()
        );


        shapeRenderer.setColor(
            Color.WHITE
        );


        shapeRenderer.circle(
            x,
            y,
            layout.getSourceRadius()
                * 0.45f
        );


        shapeRenderer.end();
    }


    private void drawTarget() {

        LaserTarget target =
            gameManager
                .getBoard()
                .getTarget();


        float x =
            layout.getCellCenterX(
                target.getCol()
            );

        float y =
            layout.getCellCenterY(
                target.getRow()
            );


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            Color.GREEN
        );


        shapeRenderer.circle(
            x,
            y,
            layout.getTargetRadius()
        );


        shapeRenderer.setColor(
            Color.WHITE
        );


        shapeRenderer.circle(
            x,
            y,
            layout.getTargetRadius()
                * 0.40f
        );


        shapeRenderer.end();
    }


    public void dispose() {

        shapeRenderer.dispose();
    }
}
