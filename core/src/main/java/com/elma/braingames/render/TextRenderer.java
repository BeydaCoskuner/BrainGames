package com.elma.braingames.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elma.braingames.managers.FontManager;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

public class TextRenderer {

    private final SpriteBatch batch;

    private final LevelMap levelMap;

    private final GlyphLayout glyphLayout;

    public TextRenderer(LevelMap levelMap){

        this.levelMap = levelMap;

        batch = new SpriteBatch();
        glyphLayout = new GlyphLayout();

    }

    public void render(OrthographicCamera camera){

        batch.setProjectionMatrix(camera.combined);

        Gdx.app.log(
            "TEXT",
            "render"
        );
        batch.begin();

        FontManager.getDefaultFont().getData().setScale(6f);
        FontManager.getDefaultFont().setColor(Color.BLACK);

        for (LevelNode node : levelMap.getLevels()) {

            String levelText =
                String.valueOf(node.getId());

            glyphLayout.setText(
                FontManager.getDefaultFont(),
                levelText
            );

            float textX =
                node.getX() - glyphLayout.width / 2f;

            float textY =
                node.getY() + glyphLayout.height / 2f;

            FontManager.getDefaultFont().draw(
                batch,
                glyphLayout,
                textX,
                textY
            );
        }

        batch.end();

    }

    public void dispose(){

        batch.dispose();

    }

}
