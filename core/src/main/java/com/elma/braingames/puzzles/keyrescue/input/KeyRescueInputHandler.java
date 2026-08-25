package com.elma.braingames.puzzles.keyrescue.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.keyrescue.layout.KeyRescueLayout;
import com.elma.braingames.puzzles.keyrescue.manager.KeyRescueGameManager;


public class KeyRescueInputHandler
    extends InputAdapter {


    private final Viewport viewport;

    private final KeyRescueGameManager gameManager;

    private final KeyRescueLayout layout;


    private final Vector3 touch =
        new Vector3();


    private boolean dragging;


    public KeyRescueInputHandler(
        Viewport viewport,
        KeyRescueGameManager gameManager,
        KeyRescueLayout layout
    ) {

        this.viewport = viewport;

        this.gameManager = gameManager;

        this.layout = layout;

        dragging = false;
    }


    @Override
    public boolean touchDown(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {

        if (dragging) {
            return false;
        }


        touch.set(
            screenX,
            screenY,
            0
        );


        viewport.unproject(touch);


        int row =
            getRowFromTouch(
                touch.y
            );


        int col =
            getColFromTouch(
                touch.x
            );


        if (
            row < 0 ||
                col < 0
        ) {

            return false;
        }


        boolean selected =
            gameManager.selectBlock(
                row,
                col
            );


        if (selected) {

            dragging = true;

            return true;
        }


        return false;
    }


    @Override
    public boolean touchDragged(
        int screenX,
        int screenY,
        int pointer
    ) {

        if (!dragging) {
            return false;
        }


        touch.set(
            screenX,
            screenY,
            0
        );


        viewport.unproject(touch);


        int row =
            getRowFromTouch(
                touch.y
            );


        int col =
            getColFromTouch(
                touch.x
            );


        if (
            row < 0 ||
                col < 0
        ) {

            return true;
        }


        gameManager.moveSelectedBlock(
            row,
            col
        );


        return true;
    }


    @Override
    public boolean touchUp(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {

        if (!dragging) {
            return false;
        }


        gameManager.releaseBlock();


        dragging = false;


        return true;
    }


    private int getColFromTouch(
        float x
    ) {

        float boardX =
            layout.getBoardX();

        float cellSize =
            layout.getCellSize();


        if (
            x < boardX ||
                x >=
                    boardX
                        + layout.getBoardSize()
        ) {

            return -1;
        }


        return (int)
            ((x - boardX)
                / cellSize);
    }


    private int getRowFromTouch(
        float y
    ) {

        float boardY =
            layout.getBoardY();

        float cellSize =
            layout.getCellSize();


        if (
            y < boardY ||
                y >=
                    boardY
                        + layout.getBoardSize()
        ) {

            return -1;
        }


        return (int)
            ((y - boardY)
                / cellSize);
    }
}
