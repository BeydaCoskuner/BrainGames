package com.elma.braingames.puzzles.keyrescue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;

import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.keyrescue.input.KeyRescueInputHandler;
import com.elma.braingames.puzzles.keyrescue.layout.KeyRescueLayout;
import com.elma.braingames.puzzles.keyrescue.manager.KeyRescueGameManager;
import com.elma.braingames.puzzles.keyrescue.render.KeyRescueRenderer;

import com.elma.braingames.puzzles.laser.LaserPuzzleScreen;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;


public class KeyRescuePuzzleScreen
    extends BasePuzzleScreen {


    private KeyRescueGameManager gameManager;

    private KeyRescueRenderer renderer;

    private KeyRescueLayout layout;

    private KeyRescueInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public KeyRescuePuzzleScreen(
        BrainGames game
    ) {

        super(game);
    }


    @Override
    public void show() {

        super.show();


        worldViewport =
            new ScreenViewport(
                worldCamera
            );

        worldViewport.apply();


        worldCamera.position.set(
            worldViewport.getWorldWidth() / 2f,
            worldViewport.getWorldHeight() / 2f,
            0
        );

        worldCamera.update();


        gameManager =
            new KeyRescueGameManager();


        layout =
            new KeyRescueLayout();


        layout.update(
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );


        renderer =
            new KeyRescueRenderer(
                gameManager,
                layout
            );


        inputHandler =
            new KeyRescueInputHandler(
                worldViewport,
                gameManager,
                layout
            );


        victoryOverlay =
            new VictoryOverlay();


        InputMultiplexer multiplexer =
            new InputMultiplexer();



        multiplexer.addProcessor(
            new VictoryOverlayInput(
                game,
                uiViewport,
                victoryOverlay,

                // Restart
                () -> game.setScreen(
                    new KeyRescuePuzzleScreen(game)
                ),

                // Next
                () -> game.setScreen(
                    new LaserPuzzleScreen(game)
                )
            )
        );


        multiplexer.addProcessor(
            new InputAdapter() {

                @Override
                public boolean touchDown(
                    int screenX,
                    int screenY,
                    int pointer,
                    int button
                ) {

                    if (
                        victoryOverlay.isVisible()
                    ) {

                        return false;
                    }


                    Vector3 touch =
                        new Vector3(
                            screenX,
                            screenY,
                            0
                        );


                    uiViewport.unproject(
                        touch
                    );


                    if (
                        hud.isBackPressed(
                            touch.x,
                            touch.y
                        )
                    ) {

                        game.setScreen(
                            new LevelSelectScreen(game)
                        );

                        return true;
                    }


                    if (
                        hud.isRestartPressed(
                            touch.x,
                            touch.y
                        )
                    ) {

                        game.setScreen(
                            new KeyRescuePuzzleScreen(game)
                        );

                        return true;
                    }


                    return false;
                }
            }
        );

        multiplexer.addProcessor(
            inputHandler
        );


        Gdx.input.setInputProcessor(
            multiplexer
        );


        hud.setUseTimer(true);

        hud.setMoves(0);

        hud.setStars(3);
    }


    @Override
    public void render(
        float delta
    ) {

        super.render(delta);


        worldViewport.apply();


        renderer.render(
            worldCamera
        );


        uiViewport.apply();


        victoryOverlay.render(
            uiCamera
        );
    }


    @Override
    protected void update(
        float delta
    ) {

        gameManager.update(delta);

        hud.setRemainingTime(
            gameManager.getElapsedTime()
        );

        hud.setMoves(
            gameManager.getMoves()
        );


        hud.setStars(
            calculateStars(
                gameManager.getElapsedTime()
            )
        );


        if (
            gameManager.isCompleted()
                &&
                !hud.isGameFinished()
        ) {

            hud.setGameFinished(true);

            finishGame();
        }
    }


    private int calculateStars(
        float seconds
    ) {

        if (seconds <= 10f) {

            return 3;

        } else if (seconds <= 20f) {

            return 2;

        } else if (seconds <= 30f) {

            return 1;

        } else {

            return 0;
        }
    }


    private void finishGame() {

        float seconds =
            gameManager.getElapsedTime();


        int stars =
            calculateStars(seconds);


        hud.setStars(stars);


        victoryOverlay.show(
            stars,
            gameManager.getMoves()
        );


        SaveManager.saveLevelResult(
            6,
            stars
        );
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


        worldViewport.update(
            width,
            height,
            true
        );


        layout.update(
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );
    }


    @Override
    public void dispose() {

        super.dispose();


        if (
            renderer != null
        ) {

            renderer.dispose();
        }


        if (
            victoryOverlay != null
        ) {

            victoryOverlay.dispose();
        }
    }
}
