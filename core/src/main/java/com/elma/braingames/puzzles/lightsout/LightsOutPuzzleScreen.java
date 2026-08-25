package com.elma.braingames.puzzles.lightsout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;

import com.elma.braingames.puzzles.BasePuzzleScreen;

import com.elma.braingames.puzzles.colorsequence.ColorSequencePuzzleScreen;
import com.elma.braingames.puzzles.lightsout.input.LightsOutInputHandler;
import com.elma.braingames.puzzles.lightsout.layout.LightsOutLayout;
import com.elma.braingames.puzzles.lightsout.manager.LightsOutGameManager;
import com.elma.braingames.puzzles.lightsout.render.LightsOutRenderer;

import com.elma.braingames.puzzles.memory.MemoryPuzzleScreen;
import com.elma.braingames.screens.LevelSelectScreen;

import com.elma.braingames.ui.VictoryOverlay;


public class LightsOutPuzzleScreen extends BasePuzzleScreen {

    private final Vector3 touch = new Vector3();

    private LightsOutGameManager gameManager;

    private LightsOutLayout layout;

    private LightsOutRenderer renderer;

    private LightsOutInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public LightsOutPuzzleScreen(
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
            new LightsOutGameManager();

        layout =
            new LightsOutLayout();

        renderer =
            new LightsOutRenderer(
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

                () -> {

                    game.setScreen(
                        new LightsOutPuzzleScreen(
                            game
                        )
                    );

                },

                () -> {

                    //next

                    game.setScreen(
                        new ColorSequencePuzzleScreen(game)
                    );

                }

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


                    touch.set(
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
                            new LevelSelectScreen(
                                game
                            )
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
                            new LightsOutPuzzleScreen(
                                game
                            )
                        );

                        return true;
                    }


                    return false;
                }
            }
        );

        inputHandler =
            new LightsOutInputHandler(
                worldCamera,
                layout,
                gameManager
            );


        multiplexer.addProcessor(
            inputHandler
        );

        Gdx.input.setInputProcessor(
            multiplexer
        );


        hud.setUseTimer(
            false
        );

        hud.setMoves(
            0
        );

        hud.setStars(
            3
        );

        hud.setGameFinished(
            false
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


        worldCamera.update();


        renderer.render(
            worldCamera
        );

        uiViewport.apply();

        uiCamera.update();


        hud.render(
            uiCamera
        );

        victoryOverlay.render(
            uiCamera
        );
    }

    @Override
    protected void update(
        float delta
    ) {

        int moves =
            gameManager.getMoves();

        hud.setMoves(
            moves
        );

        int stars =
            calculateStars(
                moves
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
        int moves
    ) {

        if (
            moves <= 28
        ) {

            return 3;
        }


        if (
            moves <= 35
        ) {

            return 2;
        }


        if (
            moves <= 40
        ) {

            return 1;
        }


        return 0;
    }

    private void finishGame() {

        int moves =
            gameManager.getMoves();


        int stars =
            calculateStars(
                moves
            );

        hud.setStars(
            stars
        );


        hud.setMoves(
            moves
        );
        victoryOverlay.show(
            stars,
            moves
        );

        SaveManager.saveLevelResult(
            8,
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


        worldCamera.position.set(
            worldViewport.getWorldWidth() / 2f,
            worldViewport.getWorldHeight() / 2f,
            0
        );


        worldCamera.update();

        uiViewport.update(
            width,
            height,
            true
        );


        uiCamera.update();
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
