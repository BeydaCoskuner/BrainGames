package com.elma.braingames.puzzles.shake.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShakeRenderer {

    private final SpriteBatch batch;

    private final Texture cloudsTexture;

    private final BitmapFont font;

    private final GlyphLayout glyphLayout;

    public ShakeRenderer() {

        batch = new SpriteBatch();

        cloudsTexture =
            new Texture("clouds/clouds.png");

        font = new BitmapFont();

        glyphLayout =
            new GlyphLayout();
    }

    public void render(
        OrthographicCamera camera
    ) {

        float width =
            camera.viewportWidth;

        float height =
            camera.viewportHeight;

        batch.setProjectionMatrix(
            camera.combined
        );

        batch.begin();

        //bulut resmi
        batch.draw(
            cloudsTexture,
            0,
            0,
            width,
            height
        );

        drawInstruction(
            width,
            height
        );

        batch.end();
    }

    private void drawInstruction(
        float width,
        float height
    ) {

        String text =
            "See the clouds?\n"
                + "Give your phone a shake.";

        float fontScale =
            Math.min(width, height) / 280f;

        font.getData().setScale(
            fontScale
        );

        font.setColor(
            Color.BLACK
        );

        glyphLayout.setText(
            font,
            text
        );

        float x =
            (width - glyphLayout.width) / 2f;

        float y =
            height * 0.55f;

        font.draw(
            batch,
            glyphLayout,
            x,
            y
        );
    }

    public void dispose() {

        batch.dispose();

        cloudsTexture.dispose();

        font.dispose();
    }
}
