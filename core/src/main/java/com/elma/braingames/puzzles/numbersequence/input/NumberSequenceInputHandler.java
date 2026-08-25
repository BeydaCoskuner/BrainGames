package com.elma.braingames.puzzles.numbersequence.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.numbersequence.layout.NumberSequenceLayout;
import com.elma.braingames.puzzles.numbersequence.manager.NumberSequenceGameManager;
import com.elma.braingames.puzzles.numbersequence.model.NumberSequenceBoard;

public class NumberSequenceInputHandler
    extends InputAdapter {


    private final Viewport viewport;

    private final NumberSequenceLayout layout;

    private final NumberSequenceGameManager gameManager;

    private final Vector3 touch;


    public NumberSequenceInputHandler(
        Viewport viewport,
        NumberSequenceLayout layout,
        NumberSequenceGameManager gameManager
    ) {

        this.viewport =
            viewport;

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

        if (
            !gameManager.isPlaying()
        ) {

            return false;
        }


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

        float boardWidth =
            layout.getBoardWidth();

        float boardHeight =
            layout.getBoardHeight();

        if (
            touch.x < boardX ||
                touch.x > boardX + boardWidth ||
                touch.y < boardY ||
                touch.y > boardY + boardHeight
        ) {

            return false;
        }


        float stepX =
            layout.getCellWidth()
                +
                layout.getCellGap();


        float stepY =
            layout.getCellHeight()
                +
                layout.getCellGap();


        int col =
            (int)
                (
                    (touch.x - boardX)
                        /
                        stepX
                );

        int visualRow =
            (int)
                (
                    (touch.y - boardY)
                        /
                        stepY
                );


        int row =
            NumberSequenceBoard.ROWS
                - 1
                - visualRow;


        if (
            row < 0 ||
                row >= NumberSequenceBoard.ROWS ||
                col < 0 ||
                col >= NumberSequenceBoard.COLS
        ) {

            return false;
        }


        /*
         * Hücrenin gerçek sınırında mı?
         *
         * Gap bölgesine basıldıysa hiçbir şey yapma.
         */

        float cellX =
            layout.getCellX(col);

        float cellY =
            layout.getCellY(row);


        if (
            touch.x < cellX ||
                touch.x > cellX + layout.getCellWidth() ||
                touch.y < cellY ||
                touch.y > cellY + layout.getCellHeight()
        ) {

            return false;
        }


        return gameManager.pressCell(
            row,
            col
        );
    }
}
