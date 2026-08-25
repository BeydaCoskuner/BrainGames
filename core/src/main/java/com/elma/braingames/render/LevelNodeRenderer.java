package com.elma.braingames.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elma.braingames.constants.LevelConstants;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

public class LevelNodeRenderer {

    private final LevelMap levelMap;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;

    private final Texture fullStarTexture;
    private final Texture emptyStarTexture;

    public LevelNodeRenderer(LevelMap levelMap) {

        this.levelMap = levelMap;

        this.shapeRenderer =
            new ShapeRenderer();

        this.batch =
            new SpriteBatch();

        this.fullStarTexture =
            new Texture("ui/stars/star_full.png");

        this.emptyStarTexture =
            new Texture("ui/stars/star_empty.png");
    }

    public void render(OrthographicCamera camera) {

        shapeRenderer.setProjectionMatrix(camera.combined);

        Gdx.app.log(
            "NODE",
            "count=" + levelMap.getLevels().size()
        );
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for(LevelNode node : levelMap.getLevels()) {

            if (node.isSelected()) {

                shapeRenderer.setColor(Color.SKY);

            } else if (node.isUnlocked()) {

                shapeRenderer.setColor(Color.CYAN);

            } else {

                shapeRenderer.setColor(Color.DARK_GRAY);

            }

            float radius =
                LevelConstants.NODE_RADIUS *
                    node.getAnimationScale();

            shapeRenderer.circle(
                node.getX(),
                node.getY(),
                radius
            );
            /*if (node.getId() == 2) {
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.circle(node.getX(), node.getY(), 8);
            }*/
        }

        shapeRenderer.end();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        for (LevelNode node : levelMap.getLevels()) {

            drawStars(node);
        }

        batch.end();

    }
    private void drawStars(LevelNode node) {

        int stars = node.getStars();

        if (stars <= 0) {
            return;
        }

        float starSize = 120f;
        float gap = 8f;

        float totalWidth =
            starSize * 3f + gap * 2f;

        float startX =
            node.getX() - totalWidth / 2f;

        float starY =
            node.getY()
                - LevelConstants.NODE_RADIUS
                - starSize;

        for (int i = 0; i < 3; i++) {

            Texture texture;

            if (i < stars) {

                texture =
                    fullStarTexture;

            } else {

                texture =
                    emptyStarTexture;
            }

            batch.draw(
                texture,
                startX + i * (starSize + gap),
                starY,
                starSize,
                starSize
            );
        }
    }

    public void dispose() {

        shapeRenderer.dispose();

        batch.dispose();

        fullStarTexture.dispose();

        emptyStarTexture.dispose();
    }

}
