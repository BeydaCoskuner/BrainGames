package com.elma.braingames.puzzles.shake.manager;

import com.badlogic.gdx.Gdx;

public class ShakeGameManager {

    private boolean completed;

    private float shakeThreshold = 5f;

    public ShakeGameManager() {

        completed = false;

    }

    public void update(float delta) {

        if (completed) {
            return;
        }

        float x = Gdx.input.getAccelerometerX();
        float y = Gdx.input.getAccelerometerY();
        float z = Gdx.input.getAccelerometerZ();

        float acceleration =
            (float) Math.sqrt(
                x * x +
                    y * y +
                    z * z
            );
        Gdx.app.log(
            "SHAKE_SENSOR",
            "x=" + x +
                " y=" + y +
                " z=" + z +
                " acceleration=" + acceleration
        );


        if (Math.abs(acceleration - 9.8f) > shakeThreshold) {

            completed = true;

            Gdx.app.log(
                "SHAKE",
                "Phone shake detected!"
            );
        }
    }

    public boolean isCompleted() {

        return completed;
    }
}
