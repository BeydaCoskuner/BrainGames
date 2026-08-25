package com.elma.braingames.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.Path;

public class PathRenderer {

    private final LevelMap levelMap;
    private final ShapeRenderer shapeRenderer;

    public PathRenderer(LevelMap levelMap) {

        this.levelMap = levelMap;
        this.shapeRenderer = new ShapeRenderer();

    }

    public void render(OrthographicCamera camera) {

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        shapeRenderer.setColor(Color.WHITE);

        for (Path path : levelMap.getPaths()) {

            drawBezier(path);

        }

        shapeRenderer.end();

    }

    private void drawBezier(Path path) {
        float x0 = path.getStart().getX();
        float y0 = path.getStart().getY();

        float x1 = path.getControlX();
        float y1 = path.getControlY();

        float x2 = path.getEnd().getX();
        float y2 = path.getEnd().getY();

        float previousX = x0;
        float previousY = y0;

        for (float t = 0; t <= 1; t += 0.02f) {
            float x =
                (1 - t) * (1 - t) * x0
                    + 2 * (1 - t) * t * x1
                    + t * t * x2;

            float y =
                (1 - t) * (1 - t) * y0
                    + 2 * (1 - t) * t * y1
                    + t * t * y2;

            shapeRenderer.line(
                previousX,
                previousY,
                x,
                y
            );

            previousX = x;
            previousY = y;
        }
    }

    public void dispose() {

        shapeRenderer.dispose();

    }

}
