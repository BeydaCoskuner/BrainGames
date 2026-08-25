package com.elma.braingames.render;

import com.elma.braingames.render.TextRenderer;
import com.elma.braingames.render.PathRenderer;
import com.elma.braingames.render.LevelNodeRenderer;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LevelMapRenderer {

    private final PathRenderer pathRenderer;
    private final LevelNodeRenderer levelNodeRenderer;
    private final LevelMap levelMap;
    private final TextRenderer textRenderer;
    private final LevelStarRenderer levelStarRenderer;
    private final BackgroundRenderer backgroundRenderer;

    public LevelMapRenderer(LevelMap levelMap) {
        this.levelMap = levelMap;
        pathRenderer = new PathRenderer(levelMap);
        levelNodeRenderer = new LevelNodeRenderer(levelMap);
        textRenderer = new TextRenderer(levelMap);
        levelStarRenderer = new LevelStarRenderer(levelMap);
        backgroundRenderer = new BackgroundRenderer();
    }

    public void render(OrthographicCamera camera) {

        backgroundRenderer.render(camera, levelMap.getMapWidth());
        pathRenderer.render(camera);
       levelNodeRenderer.render(camera);
       textRenderer.render(camera);
       levelStarRenderer.render(camera);

    }

    public void dispose() {
        backgroundRenderer.dispose();
        pathRenderer.dispose();
        levelNodeRenderer.dispose();
        textRenderer.dispose();
        levelStarRenderer.dispose();
    }
}
