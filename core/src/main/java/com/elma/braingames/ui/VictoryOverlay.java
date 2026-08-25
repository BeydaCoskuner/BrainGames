package com.elma.braingames.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class VictoryOverlay {

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private final Rectangle restartButton = new Rectangle();

    private final Rectangle nextButton = new Rectangle();

    private final Rectangle mapButton = new Rectangle();

    private boolean visible;

    private int stars;

    private int moves;

    private Texture fullStarTexture;

    private Texture emptyStarTexture;

    private Texture restartButtonTexture;

    private Texture nextButtonTexture;

    private Texture mapButtonTexture;

    public VictoryOverlay() {

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(3f);

        fullStarTexture =
            new Texture("ui/stars/star_full.png");

        emptyStarTexture =
            new Texture("ui/stars/star_empty.png");
        restartButtonTexture =
            new Texture("buttons/restart.png");

        nextButtonTexture =
            new Texture("buttons/next_level.png");

        mapButtonTexture =
            new Texture("buttons/map.png");
    }

    public void show(int stars,int moves){

        this.stars = stars;
        this.moves = moves;

        visible = true;

    }

    public boolean isVisible(){

        return visible;

    }

    public void hide(){

        visible = false;

    }
    public void render(OrthographicCamera camera){

        if(!visible){
            return;
        }

        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);

        float width = camera.viewportWidth;
        float height = camera.viewportHeight;

        float panelWidth = 950;
        float panelHeight = 600;

        float x = (width-panelWidth)/2f;
        float y = (height-panelHeight)/2f;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.
            setColor(0,0,0,0.75f);

        shapeRenderer.rect(
            0,
            0,
            width,
            height
        );
        restartButton.set(
            x + 40,
            y - 90,
            320,
            350
        );

        nextButton.set(
            x + 370,
            y - 40,
            250,
            250
        );

        mapButton.set(
            x + 630,
            y - 20,
            220,
            220
        );

        shapeRenderer.setColor(Color.WHITE);

        shapeRenderer.rect(
            x,
            y,
            panelWidth,
            panelHeight
        );

        /*shapeRenderer.setColor(Color.GOLD);

        shapeRenderer.rect(
            restartButton.x,
            restartButton.y,
            restartButton.width,
            restartButton.height
        );

        shapeRenderer.rect(
            nextButton.x,
            nextButton.y,
            nextButton.width,
            nextButton.height
        );

        shapeRenderer.rect(
            mapButton.x,
            mapButton.y,
            mapButton.width,
            mapButton.height
        );*/

        shapeRenderer.end();

        batch.begin();


        font.setColor(Color.GOLD);

        font.draw(batch,
            "LEVEL COMPLETE",
            x+270,
            y+430);

        font.draw(batch,
            "Moves : "+moves,
            x+380,
            y+320);

        drawStars(
            x,
            y,
            panelWidth,
            panelHeight
        );
        drawButtons();
        /*font.draw(
            batch,
            "Restart",
            restartButton.x + 30,
            restartButton.y + 60
        );

        font.draw(
            batch,
            "Next",
            nextButton.x + 55,
            nextButton.y + 60
        );

        font.draw(
            batch,
            "Map",
            mapButton.x + 65,
            mapButton.y + 60
        );*/

        batch.end();

    }
    private void drawStars(
        float panelX,
        float panelY,
        float panelWidth,
        float panelHeight
    ) {

        float starSize = 100f;
        float gap = 25f;

        float totalWidth =
            starSize * 3f + gap * 2f;

        float startX =
            panelX + (panelWidth - totalWidth) / 2f;

        float starY =
            panelY + 130f;

        for (int i = 0; i < 3; i++) {

            Texture texture;

            if (i < stars) {
                texture = fullStarTexture;
            } else {
                texture = emptyStarTexture;
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
    private void drawButtons() {

        batch.draw(
            restartButtonTexture,
            restartButton.x,
            restartButton.y,
            restartButton.width,
            restartButton.height
        );

        batch.draw(
            nextButtonTexture,
            nextButton.x,
            nextButton.y,
            nextButton.width,
            nextButton.height
        );

        batch.draw(
            mapButtonTexture,
            mapButton.x,
            mapButton.y,
            mapButton.width,
            mapButton.height
        );
    }

    public boolean isRestartPressed(float x, float y) {

        return restartButton.contains(x, y);

    }
    public boolean isNextPressed(float x, float y) {

        return nextButton.contains(x, y);

    }
    public boolean isMapPressed(float x, float y) {

        return mapButton.contains(x, y);

    }





    public void dispose(){

        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();

        if (fullStarTexture != null) {
            fullStarTexture.dispose();
        }

        if (emptyStarTexture != null) {
            emptyStarTexture.dispose();
        }
        if (restartButtonTexture != null) {
            restartButtonTexture.dispose();
        }

        if (nextButtonTexture != null) {
            nextButtonTexture.dispose();
        }

        if (mapButtonTexture != null) {
            mapButtonTexture.dispose();
        }
    }

}
