package com.elma.braingames.puzzles.colormatch.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.puzzles.colormatch.manager.ColorMatchGameManager;
import com.elma.braingames.puzzles.colormatch.models.ColorCircle;

public class ColorMatchInput extends InputAdapter {

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final ColorMatchGameManager gameManager;

    private final Vector3 touch;
    private final Vector2 currentTouch;


    private final Array<Vector2> currentPath;

    private boolean touching;


    public ColorMatchInput(
        OrthographicCamera camera,
        Viewport viewport,
        ColorMatchGameManager gameManager
    ) {

        this.camera = camera;

        this.viewport = viewport;

        this.gameManager = gameManager;

        touch = new Vector3();

        currentTouch = new Vector2();

        currentPath = new Array<>();

        touching = false;
    }


    @Override
    public boolean touchDown(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {


        screenToWorld(
            screenX,
            screenY
        );



        ColorCircle circle =
            gameManager.getCircleAt(
                touch.x,
                touch.y
            );


        if (circle == null) {

            return false;
        }

        if (!gameManager.canStartLine(
            circle
        )) {

            return false;
        }


        gameManager.startLine(
            circle
        );
        currentPath.clear();



        currentPath.add(
            new Vector2(
                touch.x,
                touch.y
            )
        );


        gameManager.setActiveLinePoints(
            currentPath
        );


        currentTouch.set(
            touch.x,
            touch.y
        );


        touching = true;

        return true;
    }


    @Override
    public boolean touchDragged(
        int screenX,
        int screenY,
        int pointer
    ) {

        if (!touching) {

            return false;
        }

        screenToWorld(
            screenX,
            screenY
        );



        currentTouch.set(
            touch.x,
            touch.y
        );



        Vector2 lastPoint =
            currentPath.peek();


        float minimumDistance = 8f;


        if (
            lastPoint == null
                || lastPoint.dst2(
                touch.x,
                touch.y
            ) >= minimumDistance * minimumDistance
        ) {

            currentPath.add(
                new Vector2(
                    touch.x,
                    touch.y
                )
            );
        }


        return true;
    }


    @Override
    public boolean touchUp(
        int screenX,
        int screenY,
        int pointer,
        int button
    ) {

        if (!touching) {

            return false;
        }


        screenToWorld(
            screenX,
            screenY
        );


        currentTouch.set(
            touch.x,
            touch.y
        );


        Vector2 lastPoint =
            currentPath.peek();


        if (
            lastPoint == null
                || lastPoint.dst2(
                touch.x,
                touch.y
            ) > 1f
        ) {

            currentPath.add(
                new Vector2(
                    touch.x,
                    touch.y
                )
            );
        }



        ColorCircle targetCircle =
            gameManager.getCircleAt(
                touch.x,
                touch.y
            );

        gameManager.finishLine(
            targetCircle
        );

        touching = false;

        currentTouch.set(
            0f,
            0f
        );


        currentPath.clear();


        return true;
    }


    private void screenToWorld(
        int screenX,
        int screenY
    ) {

        touch.set(
            screenX,
            screenY,
            0
        );

        viewport.unproject(
            touch
        );
    }


    public Vector2 getCurrentTouch() {

        return currentTouch;
    }


    public Array<Vector2> getCurrentPath() {

        return currentPath;
    }


    public boolean isTouching() {

        return touching;
    }


    public void reset() {

        touching = false;

        currentTouch.set(
            0f,
            0f
        );

        currentPath.clear();

        gameManager.clearActiveLinePoints();
    }
}
