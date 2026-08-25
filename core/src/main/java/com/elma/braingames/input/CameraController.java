package com.elma.braingames.input;

import com.elma.braingames.models.LevelMap;
import com.elma.braingames.utils.GameConfig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraController {

    private final OrthographicCamera camera;

    private float lastX;

    private boolean dragging;

    private final LevelMap levelMap;

    public CameraController(OrthographicCamera camera,
                            LevelMap levelMap) {

        this.camera = camera;
        this.levelMap = levelMap;

    }

    public void update() {

        if (Gdx.input.isTouched()) {

            float currentX = Gdx.input.getX();

            if (!dragging) {

                dragging = true;
                lastX = currentX;

            }

            float delta = currentX - lastX;

            camera.position.x -= delta;

            float minX = GameConfig.WORLD_WIDTH / 2f;

            float maxX = levelMap.getMapWidth()
                + GameConfig.CAMERA_MARGIN;

            camera.position.x = Math.max(minX,
                Math.min(camera.position.x, maxX));

            lastX = currentX;

        } else {

            dragging = false;

        }

        camera.update();

    }

}
