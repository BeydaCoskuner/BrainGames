package com.elma.braingames.puzzles.maze.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.maze.layout.MazeLayout;
import com.elma.braingames.puzzles.maze.manager.MazeGameManager;

public class MazeInputHandler extends InputAdapter {

    private final Viewport viewport;

    private final MazeGameManager gameManager;

    private final MazeLayout layout;

    private final Vector3 touch =
        new Vector3();


    public MazeInputHandler(
        Viewport viewport,
        MazeGameManager gameManager,
        MazeLayout layout
    ) {

        this.viewport = viewport;
        this.gameManager = gameManager;
        this.layout = layout;
    }


    @Override
    public boolean touchDragged(
        int screenX,
        int screenY,
        int pointer
    ) {

        touch.set(
            screenX,
            screenY,
            0
        );

        viewport.unproject(touch);


        float mazeX =
            layout.getMazeX();

        float mazeY =
            layout.getMazeY();

        float mazeSize =
            layout.getMazeSize();


        if (
            touch.x < mazeX ||
                touch.x > mazeX + mazeSize ||
                touch.y < mazeY ||
                touch.y > mazeY + mazeSize
        ) {

            return false;
        }


        float cellSize =
            mazeSize /
                MazeGameManager.COLS;


        float x =
            (touch.x - mazeX)
                / cellSize;


        float y =
            (touch.y - mazeY)
                / cellSize;


        gameManager.moveBall(
            x,
            y
        );


        return true;
    }
}
