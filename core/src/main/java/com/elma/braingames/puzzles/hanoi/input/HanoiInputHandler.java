package com.elma.braingames.puzzles.hanoi.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.hanoi.layout.HanoiLayout;
import com.elma.braingames.puzzles.hanoi.manager.HanoiGameManager;

public class HanoiInputHandler extends InputAdapter {

    private final Viewport viewport;

    private final HanoiGameManager gameManager;

    private final HanoiLayout layout;

    private final Vector3 touch =
        new Vector3();


    public HanoiInputHandler(
        Viewport viewport,
        HanoiGameManager gameManager,
        HanoiLayout layout
    ) {

        this.viewport =
            viewport;

        this.gameManager =
            gameManager;

        this.layout =
            layout;
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


        int rod =
            findTouchedRod(
                touch.x,
                touch.y
            );


        if (rod == -1) {

            return false;
        }


        gameManager.selectRod(
            rod
        );


        return true;
    }


    private int findTouchedRod(
        float x,
        float y
    ) {

        float touchRadius =
            layout.getBoardWidth()
                * 0.10f;


        for (
            int i = 0;
            i < HanoiGameManager.ROD_COUNT;
            i++
        ) {

            float rodX =
                layout.getRodX(i);


            if (
                Math.abs(x - rodX)
                    <= touchRadius
            ) {

                return i;
            }
        }


        return -1;
    }
}
