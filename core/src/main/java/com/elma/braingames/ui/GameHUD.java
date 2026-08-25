package com.elma.braingames.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameHUD {

    private int currentStars;

    private Texture backTexture;

    private Texture restartTexture;

    private Texture fullStarTexture;
    private Texture emptyStarTexture;

    private int maxStars = 3;

    private int currentMoves;

    private int maxMoves;

    private float remainingTime;

    private boolean gameFinished;

    private boolean useTimer;


    private final ShapeRenderer shapeRenderer;

    private final SpriteBatch batch;

    private final BitmapFont font;

    private final HUDLayout layout;


    public GameHUD() {

        currentStars = 3;

        currentMoves = 0;

        maxMoves = 0;

        remainingTime = 0;

        gameFinished = false;

        useTimer = true;


        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        font = new BitmapFont();

        layout = new HUDLayout();

        backTexture = new Texture("buttons/back.png");

        restartTexture = new Texture("buttons/restart.png");

        fullStarTexture = new Texture("ui/stars/star_full.png");

        emptyStarTexture = new Texture("ui/stars/star_empty.png");
    }


    public void update(float delta) {

    }


    public void render(OrthographicCamera camera) {

        float width = camera.viewportWidth;

        float height = camera.viewportHeight;

        layout.update(width, height);


        shapeRenderer.setProjectionMatrix(
            camera.combined
        );

        batch.setProjectionMatrix(
            camera.combined
        );


        drawPanels();

        drawButtons();

        drawTexts(camera);
    }


    /*private void drawPanels(
        OrthographicCamera camera
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            Color.DARK_GRAY
        );


        // GERİ

        shapeRenderer.rect(
            layout.backButton.x,
            layout.backButton.y,
            layout.backButton.width,
            layout.backButton.height
        );


        // YILDIZLAR

        shapeRenderer.rect(
            layout.starsPanel.x,
            layout.starsPanel.y,
            layout.starsPanel.width,
            layout.starsPanel.height
        );


        // MOVES / TIMER

        shapeRenderer.rect(
            layout.movesPanel.x,
            layout.movesPanel.y,
            layout.movesPanel.width,
            layout.movesPanel.height
        );


        // RESTART

        shapeRenderer.rect(
            layout.restartButton.x,
            layout.restartButton.y,
            layout.restartButton.width,
            layout.restartButton.height
        );


        shapeRenderer.end();
    }*/
    private void drawPanels() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            Color.WHITE
        );

        // Yıldız paneli
        shapeRenderer.rect(
            layout.starsPanel.x,
            layout.starsPanel.y,
            layout.starsPanel.width,
            layout.starsPanel.height
        );

        // Moves / Timer paneli
        shapeRenderer.rect(
            layout.movesPanel.x,
            layout.movesPanel.y,
            layout.movesPanel.width,
            layout.movesPanel.height
        );

        shapeRenderer.end();
    }
    private void drawButtons() {

        batch.begin();

        batch.draw(
            backTexture,
            layout.backButton.x,
            layout.backButton.y,
            layout.backButton.width,
            layout.backButton.height
        );

        batch.draw(
            restartTexture,
            layout.restartButton.x,
            layout.restartButton.y,
            layout.restartButton.width,
            layout.restartButton.height
        );

        batch.end();
    }


    private void drawTexts(
        OrthographicCamera camera
    ) {

        float width = camera.viewportWidth;

        float height = camera.viewportHeight;



        float fontScale =
            Math.min(width, height) / 500f;

        font.getData().setScale(
            fontScale
        );

        font.setColor(Color.BLACK);

        batch.begin();


/*
        String starsText =
            getStarsText();


        font.draw(
            batch,

            starsText,

            layout.starsPanel.x
                + layout.starsPanel.width * 0.35f,

            layout.starsPanel.y
                + layout.starsPanel.height * 0.68f
        );*/
        drawStars();

        String counterText;


        if (useTimer) {

            counterText =
                String.format(
                    java.util.Locale.US,
                    "%.0f",
                    remainingTime
                );

        } else {

            counterText =
                "moves: " + currentMoves;
        }


        font.draw(
            batch,

            counterText,

            layout.movesPanel.x
                + layout.movesPanel.width * 0.25f,

            layout.movesPanel.y
                + layout.movesPanel.height * 0.68f
        );




        batch.end();
    }


    /*private String getStarsText() {

        switch (currentStars) {

            case 3:
                return "★★★";

            case 2:
                return "★★☆";

            case 1:
                return "★☆☆";

            default:
                return "☆☆☆";
        }
    }*/

    private void drawStars() {

        float panelX = layout.starsPanel.x;
        float panelY = layout.starsPanel.y;

        float panelWidth = layout.starsPanel.width;
        float panelHeight = layout.starsPanel.height;

        float starSize = panelHeight * 0.65f;

        float gap = starSize * 0.15f;

        float totalWidth =
            starSize * 3f + gap * 2f;

        float startX =
            panelX + (panelWidth - totalWidth) / 2f;

        float starY =
            panelY + (panelHeight - starSize) / 2f;

        for (int i = 0; i < 3; i++) {

            Texture texture;

            if (i < currentStars) {
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

    public void setStars(int stars) {

        currentStars = stars;
    }


    public void setMoves(int moves) {

        currentMoves = moves;
    }


    public void setMaxMoves(int maxMoves) {

        this.maxMoves = maxMoves;
    }


    public void setRemainingTime(float time) {

        remainingTime = time;
    }


    public void setUseTimer(boolean useTimer) {

        this.useTimer = useTimer;
    }


    public void setGameFinished(
        boolean finished
    ) {

        gameFinished = finished;
    }


    public boolean isGameFinished() {

        return gameFinished;
    }

    public boolean isBackPressed(
        float x,
        float y
    ) {

        return layout.backButton.contains(
            x,
            y
        );
    }


    public boolean isRestartPressed(
        float x,
        float y
    ) {

        return layout.restartButton.contains(
            x,
            y
        );
    }


    public void dispose() {

        shapeRenderer.dispose();

        batch.dispose();

        font.dispose();

        if (backTexture != null) {
            backTexture.dispose();
        }

        if (restartTexture != null) {
            restartTexture.dispose();
        }
        fullStarTexture.dispose();

        emptyStarTexture.dispose();
    }
}
