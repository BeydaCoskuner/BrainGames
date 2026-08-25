package com.elma.braingames.puzzles.colormatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.puzzles.colormatch.input.ColorMatchInput;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.colormatch.render.ColorMatchRenderer;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;
import com.elma.braingames.utils.GameConfig;

import com.elma.braingames.puzzles.colormatch.manager.ColorMatchGameManager;


public class ColorMatchPuzzleScreen
    extends BasePuzzleScreen {


    private final ColorMatchRenderer renderer;


    private final ColorMatchGameManager gameManager;



    private  ColorMatchInput gameInput;

    private final VictoryOverlay victoryOverlay;


    private boolean cloudScreen;

    private Texture cloudsTexture;

    private SpriteBatch cloudBatch;

    private float gameTime;

    private boolean hideHud = false;

    private boolean timerRunning;

    private static final float SHAKE_THRESHOLD = 8.0f;

    private static final float SHAKE_COOLDOWN = 0.8f;

    private float shakeCooldown;

    private float lastAcceleration;

    private boolean shakeInitialized;


    private boolean gameInputActive;


    private final Vector3 uiTouch;

    public ColorMatchPuzzleScreen(
        BrainGames game
    ) {

        super(game);


        gameManager =
            new ColorMatchGameManager();


        renderer =
            new ColorMatchRenderer();



        victoryOverlay =
            new VictoryOverlay();


        uiTouch =
            new Vector3();


        cloudScreen =
            true;


        gameTime =
            0f;


        timerRunning =
            false;


        shakeCooldown =
            0f;


        lastAcceleration =
            0f;

        shakeInitialized =
            false;


        gameInputActive =
            false;
    }


    @Override
    public void show() {

        super.show();


        gameInput =
            new ColorMatchInput(
                worldCamera,
                worldViewport,
                gameManager
            );


        cloudsTexture =
            new Texture(
                "clouds/clouds.png"
            );

        cloudBatch =
            new SpriteBatch();


        gameManager.createCircles(
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT
        );


        cloudScreen = true;

        timerRunning = false;

        gameTime = 0f;

        shakeCooldown = 0f;

        lastAcceleration = 0f;

        shakeInitialized = false;

        gameInputActive = false;


        hud.setStars(3);

        hud.setRemainingTime(0f);

        hud.setUseTimer(true);

        hud.setGameFinished(false);


        victoryOverlay.hide();


        setCloudInput();
        Gdx.app.log(
            "COLORMATCH",
            "SHOW -> cloudScreen = " + cloudScreen
        );
    }


    @Override
    protected void update(
        float delta
    ) {

        if (victoryOverlay.isVisible()) {
            return;
        }


        if (cloudScreen) {

            updateShake(delta);

            return;
        }


        if (timerRunning) {

            gameTime += delta;

            hud.setRemainingTime(
                gameTime
            );
        }


        if (gameManager.isGameFailed()) {

            restartFromCloud();

            return;
        }


        if (gameManager.isGameCompleted()) {

            finishGame();

            return;
        }
    }
    @Override
    public void render(
        float delta
    ) {
        Gdx.app.log(
            "COLORMATCH",
            "RENDER -> cloudScreen = " + cloudScreen
        );
        super.render(delta);

        if (victoryOverlay.isVisible()) {

            worldViewport.apply();

            worldCamera.update();

            renderer.render(
                worldCamera,
                gameManager,
                null
            );


            uiViewport.apply();

            uiCamera.update();

            victoryOverlay.render(
                uiCamera
            );

            return;
        }

        if (cloudScreen) {

            drawCloudScreen();

            return;
        }

        drawGame();
    }

    private void drawGame() {

        worldViewport.apply();

        worldCamera.update();

        Vector2 currentTouch =
            gameInput.getCurrentTouch();

        renderer.render(
            worldCamera,
            gameManager,
            currentTouch
        );
    }

    private void drawCloudScreen() {
        Gdx.app.log(
            "COLORMATCH",
            "DRAW CLOUD"
        );

        if (cloudsTexture == null) {
            return;
        }

        if (cloudBatch == null) {
            return;
        }


        worldViewport.apply();

        worldCamera.update();


        cloudBatch.setProjectionMatrix(
            worldCamera.combined
        );


        cloudBatch.begin();


        cloudBatch.draw(
            cloudsTexture,
            0,
            0,
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT
        );


        cloudBatch.end();
    }

    private void updateShake(
        float delta
    ) {

        if (shakeCooldown > 0f) {

            shakeCooldown -= delta;

            return;
        }


        if (
            !Gdx.input.isPeripheralAvailable(
                com.badlogic.gdx.Input.Peripheral.Accelerometer
            )
        ) {

            return;
        }


        float x =
            Gdx.input.getAccelerometerX();

        float y =
            Gdx.input.getAccelerometerY();

        float z =
            Gdx.input.getAccelerometerZ();


        float acceleration =
            (float) Math.sqrt(
                x * x +
                    y * y +
                    z * z
            );


        if (!shakeInitialized) {

            lastAcceleration =
                acceleration;

            shakeInitialized =
                true;

            return;
        }


        float difference =
            Math.abs(
                acceleration -
                    lastAcceleration
            );


        lastAcceleration =
            acceleration;


        if (
            difference >
                SHAKE_THRESHOLD
        ) {

            startGame();

            shakeCooldown =
                SHAKE_COOLDOWN;
        }
    }


    private void startGame() {

        cloudScreen =
            false;


        timerRunning =
            true;


        gameTime =
            0f;


        shakeCooldown =
            SHAKE_COOLDOWN;

        shakeInitialized =
            false;


        gameManager.resetGame();


        gameInput.reset();


        hud.setStars(3);

        hud.setRemainingTime(0f);

        hud.setGameFinished(false);


        gameInputActive =
            true;


        setGameInput();
    }


    private void setGameInput() {

        Gdx.input.setInputProcessor(
            new InputAdapter() {


                @Override
                public boolean touchDown(
                    int screenX,
                    int screenY,
                    int pointer,
                    int button
                ) {

                    uiTouch.set(
                        screenX,
                        screenY,
                        0
                    );


                    uiViewport.unproject(
                        uiTouch
                    );


                    if (
                        hud.isBackPressed(
                            uiTouch.x,
                            uiTouch.y
                        )
                    ) {

                        game.setScreen(
                            new LevelSelectScreen(
                                game
                            )
                        );


                        return true;
                    }
                    if (
                        hud.isRestartPressed(
                            uiTouch.x,
                            uiTouch.y
                        )
                    ) {

                        restartFromCloud();


                        return true;
                    }


                    return gameInput.touchDown(
                        screenX,
                        screenY,
                        pointer,
                        button
                    );
                }


                @Override
                public boolean touchDragged(
                    int screenX,
                    int screenY,
                    int pointer
                ) {

                    return gameInput.touchDragged(
                        screenX,
                        screenY,
                        pointer
                    );
                }


                @Override
                public boolean touchUp(
                    int screenX,
                    int screenY,
                    int pointer,
                    int button
                ) {

                    return gameInput.touchUp(
                        screenX,
                        screenY,
                        pointer,
                        button
                    );
                }
            }
        );
    }

    private void setCloudInput() {

        gameInputActive =
            false;


        Gdx.input.setInputProcessor(
            new InputAdapter() {

                @Override
                public boolean touchDown(
                    int screenX,
                    int screenY,
                    int pointer,
                    int button
                ) {

                    return true;
                }
            }
        );
    }


    private void finishGame() {

        if (
            victoryOverlay.isVisible()
        ) {

            return;
        }


        timerRunning =
            false;


        gameInputActive =
            false;

        int stars;


        if (
            gameTime <= 10f
        ) {

            stars = 3;

        } else if (
            gameTime <= 15f
        ) {

            stars = 2;

        } else if (
            gameTime <= 20f
        ) {

            stars = 1;

        } else {

            stars = 0;
        }


        hud.setStars(
            stars
        );


        hud.setGameFinished(
            true
        );


        victoryOverlay.show(
            stars,
            0
        );


        Gdx.input.setInputProcessor(
            new VictoryOverlayInput(

                game,

                uiViewport,

                victoryOverlay,

                this::restartFromCloud,

                this::goToNextLevel
            )
        );
    }



    private void restartFromCloud() {

        victoryOverlay.hide();


        cloudScreen =
            true;


        timerRunning =
            false;


        gameTime =
            0f;


        gameManager.resetGame();


        gameInput.reset();


        hud.setStars(3);

        hud.setRemainingTime(0f);

        hud.setGameFinished(false);


        gameInputActive =
            false;


        setCloudInput();
    }



    private void goToNextLevel() {

    }

    @Override
    public void resize(
        int width,
        int height
    ) {

        super.resize(
            width,
            height
        );
    }

    @Override
    public void hide() {

        Gdx.input.setInputProcessor(
            null
        );
    }


    @Override
    public void dispose() {

        super.dispose();


        renderer.dispose();


        victoryOverlay.dispose();


        gameInput.reset();


        if (
            cloudsTexture != null
        ) {

            cloudsTexture.dispose();
        }


        if (
            cloudBatch != null
        ) {

            cloudBatch.dispose();
        }
    }
}
