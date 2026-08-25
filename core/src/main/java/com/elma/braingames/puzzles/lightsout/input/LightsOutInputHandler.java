package com.elma.braingames.puzzles.lightsout.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.elma.braingames.puzzles.lightsout.layout.LightsOutLayout;
import com.elma.braingames.puzzles.lightsout.manager.LightsOutGameManager;
import com.elma.braingames.puzzles.lightsout.model.LightsOutBoard;

public class LightsOutInputHandler extends InputAdapter {

    private final OrthographicCamera camera;

    private final LightsOutLayout layout;

    private final LightsOutGameManager gameManager;

    private final Vector3 touch;


    public LightsOutInputHandler(
        OrthographicCamera camera,
        LightsOutLayout layout,
        LightsOutGameManager gameManager
    ) {

        this.camera =
            camera;

        this.layout =
            layout;

        this.gameManager =
            gameManager;

        touch =
            new Vector3();
    }


    @Override
    public boolean touchDown(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {

        touch.set(
            screenX,
            screenY,
            0
        );

        camera.unproject(
            touch
        );

        float boardX =
            layout.getBoardX();

        float boardY =
            layout.getBoardY();

        float boardSize =
            layout.getBoardSize();


        if (
            touch.x < boardX
                ||
                touch.x > boardX + boardSize
                ||
                touch.y < boardY
                ||
                touch.y > boardY + boardSize
        ) {

            return false;
        }

        float cellStep =
            layout.getCellSize()
                + layout.getCellGap();


        float localX =
            touch.x - boardX;

        float localY =
            touch.y - boardY;


        int col =
            (int)
                (localX / cellStep);

        int row =
            (int)
                (localY / cellStep);


        float cellX =
            col * cellStep;

        float cellY =
            row * cellStep;



        if (
            row < 0
                ||
                row >= LightsOutBoard.ROWS
                ||
                col < 0
                ||
                col >= LightsOutBoard.COLS
        ) {

            return false;
        }

        return gameManager.pressCell(
            row,
            col
        );
    }
}
