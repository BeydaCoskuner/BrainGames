package com.elma.braingames.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.elma.braingames.utils.GameConfig;


public class BackgroundRenderer {

    private final Texture texture;

    private final SpriteBatch batch;


    private final float tileHeight;
    private final float tileWidth;



    public BackgroundRenderer() {

        texture =
            new Texture(
                Gdx.files.internal(
                    "backgrounds/space_background.jpg"
                )
            );

        batch =
            new SpriteBatch();

        float aspectRatio =
            texture.getWidth()
                /
                (float) texture.getHeight();

        tileHeight =4000;

        tileWidth =
            tileHeight * aspectRatio;
    }


    public void render(
        OrthographicCamera camera,
        float mapWidth
    ) {

        batch.setProjectionMatrix(
            camera.combined
        );

        batch.begin();

        //yan yana resimler
        for (
            float x = 0f;
            x < mapWidth;
            x += tileWidth
        ) {

            batch.draw(
                texture,

                x,
                0f,

                tileWidth,
                tileHeight
            );
        }


        batch.end();
    }


    public void dispose() {

        texture.dispose();

        batch.dispose();
    }
}
