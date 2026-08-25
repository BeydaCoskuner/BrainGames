package com.elma.braingames.puzzles.laser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;

import com.elma.braingames.puzzles.BasePuzzleScreen;

import com.elma.braingames.puzzles.laser.input.LaserInputHandler;
import com.elma.braingames.puzzles.laser.layout.LaserLayout;
import com.elma.braingames.puzzles.laser.manager.LaserGameManager;
import com.elma.braingames.puzzles.laser.render.LaserRenderer;

import com.elma.braingames.puzzles.lightsout.LightsOutPuzzleScreen;
import com.elma.braingames.puzzles.lightsout.model.LightsOutBoard;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;


public class LaserPuzzleScreen
    extends BasePuzzleScreen {


    private LaserGameManager gameManager;

    private LaserRenderer renderer;

    private LaserLayout layout;

    private LaserInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public LaserPuzzleScreen(
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
            new LaserGameManager();


        layout =
            new LaserLayout();


        layout.update(
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );


        renderer =
            new LaserRenderer(
                gameManager,
                layout
            );


        inputHandler =
            new LaserInputHandler(
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


                // RESTART
                () -> game.setScreen(
                    new LaserPuzzleScreen(game)
                ),


                // NEXT
                () -> game.setScreen(
                    new LightsOutPuzzleScreen(game)
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

                    //geri

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

                    //restart

                    if (
                        hud.isRestartPressed(
                            touch.x,
                            touch.y
                        )
                    ) {

                        game.setScreen(
                            new LaserPuzzleScreen(game)
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

        hud.setRemainingTime(
            0f
        );

        hud.setMoves(
            0
        );

        hud.setStars(
            3
        );
    }


    @Override
    protected void update(
        float delta
    ) {

        gameManager.update(
            delta
        );

        float elapsedTime =
            gameManager.getElapsedTime();


        hud.setRemainingTime(
            elapsedTime
        );

        int stars =
            calculateStars(
                elapsedTime
            );


        hud.setStars(
            stars
        );

        if (
            gameManager.isCompleted()
                &&
                !hud.isGameFinished()
        ) {

            hud.setGameFinished(
                true
            );


            finishGame();
        }
    }


    private int calculateStars(
        float seconds
    ) {

        if (
            seconds <= 20f
        ) {

            return 3;

        } else if (
            seconds <= 30f
        ) {

            return 2;

        } else if (
            seconds <= 40f
        ) {

            return 1;

        } else {

            return 0;
        }
    }


    private void finishGame() {

        float elapsedTime =
            gameManager.getElapsedTime();


        int stars =
            calculateStars(
                elapsedTime
            );


        hud.setStars(
            stars
        );


        hud.setRemainingTime(
            elapsedTime
        );


        victoryOverlay.show(
            stars,
            0
        );

        SaveManager.saveLevelResult(
            7,
            stars
        );
    }


    @Override
    public void render(
        float delta
    ) {

        super.render(
            delta
        );

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
    public void pause() {

    }


    @Override
    public void resume() {

    }


    @Override
    public void hide() {

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
