package com.elma.braingames.puzzles.sliding.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elma.braingames.puzzles.sliding.layout.SlidingLayout;
import com.elma.braingames.puzzles.sliding.manager.SlidingGameManager;

public class SlidingInputHandler extends InputAdapter {

    private final Viewport viewport;
    private final SlidingGameManager gameManager;
    private final SlidingLayout layout;

    private final Vector3 touch =
        new Vector3();

    public SlidingInputHandler(
        Viewport viewport,
        SlidingGameManager gameManager,
        SlidingLayout layout
    ) {

        this.viewport = viewport;
        this.gameManager = gameManager;
        this.layout = layout;
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

        viewport.unproject(touch);

        float x = touch.x;
        float y = touch.y;

        float boardX =
            layout.getBoardX();

        float boardY =
            layout.getBoardY();

        float tileSize =
            layout.getTileSize();

        float boardSize =
            layout.getBoardSize();

        if (
            x < boardX ||
                x > boardX + boardSize ||
                y < boardY ||
                y > boardY + boardSize
        ) {

            return false;
        }

        int col =
            (int)((x - boardX) / tileSize);

        int visualRow =
            (int)((y - boardY) / tileSize);

        int row =
            2 - visualRow;

        if (
            row < 0 ||
                row >= 3 ||
                col < 0 ||
                col >= 3
        ) {

            return false;
        }

        return gameManager.move(
            row,
            col
        );
    }
}
