package com.elma.braingames.render;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

public class LevelStarRenderer {

    private final LevelMap levelMap;

    private final SpriteBatch batch;

    private final Texture fullStarTexture;
    private final Texture emptyStarTexture;

    public LevelStarRenderer(LevelMap levelMap) {

        this.levelMap = levelMap;

        batch = new SpriteBatch();

        fullStarTexture =
            new Texture("ui/stars/star_full.png");

        emptyStarTexture =
            new Texture("ui/stars/star_empty.png");
    }

    public void render(OrthographicCamera camera) {

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for (LevelNode node : levelMap.getLevels()) {

            //drawStars(node);
        }

        batch.end();
    }

    private void drawStars(LevelNode node) {

        float starSize = 25f;
        float gap = 5f;

        float totalWidth =
            starSize * 3f + gap * 2f;

        float startX =
            node.getX() - totalWidth / 2f;

        float startY =
            node.getY() - 70f;

        for (int i = 0; i < 3; i++) {

            Texture texture;

            if (i < node.getStars()) {
                texture = fullStarTexture;
            } else {
                texture = emptyStarTexture;
            }

            batch.draw(
                texture,
                startX + i * (starSize + gap),
                startY,
                starSize,
                starSize
            );
        }
    }

    public void dispose() {

        batch.dispose();

        fullStarTexture.dispose();
        emptyStarTexture.dispose();
    }
}
