package com.elma.braingames.puzzles.colorsequence.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.colorsequence.layout.ColorSequenceLayout;
import com.elma.braingames.puzzles.colorsequence.manager.ColorSequenceGameManager;
import com.elma.braingames.puzzles.colorsequence.model.ColorSequenceBoard;

public class ColorSequenceInputHandler
    extends InputAdapter {


    private final Viewport viewport;

    private final ColorSequenceLayout layout;

    private final ColorSequenceGameManager gameManager;

    private final Vector3 touch;


    public ColorSequenceInputHandler(
        Viewport viewport,
        ColorSequenceLayout layout,
        ColorSequenceGameManager gameManager
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
            !gameManager.isPlayerTurn()
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


        for (
            int i = 0;
            i < ColorSequenceBoard.BUTTON_COUNT;
            i++
        ) {

            int row =
                gameManager
                    .getBoard()
                    .getButton(i)
                    .getRow();


            int col =
                gameManager
                    .getBoard()
                    .getButton(i)
                    .getCol();


            float x =
                layout.getButtonX(col);

            float y =
                layout.getButtonY(row);

            float width =
                layout.getButtonWidth();

            float height =
                layout.getButtonHeight();


            if (
                touch.x >= x
                    &&
                    touch.x <= x + width
                    &&
                    touch.y >= y
                    &&
                    touch.y <= y + height
            ) {

                return gameManager.pressButton(i);
            }
        }


        return false;
    }
}
