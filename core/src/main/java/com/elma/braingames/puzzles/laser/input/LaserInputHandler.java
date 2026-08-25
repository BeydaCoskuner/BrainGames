package com.elma.braingames.puzzles.laser.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.laser.layout.LaserLayout;
import com.elma.braingames.puzzles.laser.manager.LaserGameManager;
import com.elma.braingames.puzzles.laser.model.LaserBoard;

public class LaserInputHandler
    extends InputAdapter {

    private final Viewport viewport;

    private final LaserGameManager gameManager;

    private final LaserLayout layout;

    private final Vector3 touch;


    public LaserInputHandler(
        Viewport viewport,
        LaserGameManager gameManager,
        LaserLayout layout
    ) {

        this.viewport =
            viewport;

        this.gameManager =
            gameManager;

        this.layout =
            layout;

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


        viewport.unproject(
            touch
        );


        float boardX =
            layout.getBoardX();

        float boardY =
            layout.getBoardY();

        float cellSize =
            layout.getCellSize();


        if (
            touch.x < boardX
                ||
                touch.x >
                    layout.getBoardRight()
                ||
                touch.y < boardY
                ||
                touch.y >
                    layout.getBoardTop()
        ) {

            return false;
        }


        int col =
            (int)
                ((touch.x - boardX)
                    / cellSize);


        int row =
            (int)
                ((touch.y - boardY)
                    / cellSize);


        if (
            row < 0
                ||
                row >= LaserBoard.ROWS
                ||
                col < 0
                ||
                col >= LaserBoard.COLS
        ) {

            return false;
        }


        return gameManager.rotateMirror(
            row,
            col
        );
    }
}
