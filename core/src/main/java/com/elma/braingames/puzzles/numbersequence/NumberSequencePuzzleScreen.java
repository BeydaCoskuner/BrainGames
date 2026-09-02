package com.elma.braingames.puzzles.numbersequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;

import com.elma.braingames.input.VictoryOverlayInput;

import com.elma.braingames.managers.SaveManager;

import com.elma.braingames.puzzles.BasePuzzleScreen;

import com.elma.braingames.puzzles.hanoi.HanoiPuzzleScreen;
import com.elma.braingames.puzzles.numbersequence.input.NumberSequenceInputHandler;
import com.elma.braingames.puzzles.numbersequence.layout.NumberSequenceLayout;
import com.elma.braingames.puzzles.numbersequence.manager.NumberSequenceGameManager;
import com.elma.braingames.puzzles.numbersequence.render.NumberSequenceRenderer;

import com.elma.braingames.puzzles.colorsequence.ColorSequencePuzzleScreen;

import com.elma.braingames.screens.LevelSelectScreen;

import com.elma.braingames.ui.VictoryOverlay;


public class NumberSequencePuzzleScreen
    extends BasePuzzleScreen {


    private final Vector3 touch =
        new Vector3();


    private NumberSequenceGameManager gameManager;

    private NumberSequenceLayout layout;

    private NumberSequenceRenderer renderer;

    private NumberSequenceInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public NumberSequencePuzzleScreen(
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


        Gdx.app.log(
            "NUMBER_SEQUENCE_WORLD",
            "World Width = "
                +
                worldViewport.getWorldWidth()
                +
                " World Height = "
                +
                worldViewport.getWorldHeight()
        );

        gameManager =
            new NumberSequenceGameManager();


        layout =
            new NumberSequenceLayout();


        renderer =
            new NumberSequenceRenderer();


        victoryOverlay =
            new VictoryOverlay();

        InputMultiplexer multiplexer =
            new InputMultiplexer();

        multiplexer.addProcessor(

            new VictoryOverlayInput(

                game,

                uiViewport,

                victoryOverlay,

                () ->
                    game.setScreen(
                        new NumberSequencePuzzleScreen(
                            game
                        )
                    ),

                //next
                () ->
                    game.setScreen(
                        new HanoiPuzzleScreen(
                            game
                        )
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

                    if (
                        hud.isRestartPressed(
                            touch.x,
                            touch.y
                        )
                    ) {

                        game.setScreen(
                            new NumberSequencePuzzleScreen(
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
            new NumberSequenceInputHandler(
                worldViewport,
                layout,
                gameManager
            );


        multiplexer.addProcessor(
            inputHandler
        );


        Gdx.input.setInputProcessor(
            multiplexer
        );

        hud.setUseTimer(false);

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
            worldCamera,
            gameManager,
            layout
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

        gameManager.update(
            delta
        );

        float elapsed =
            gameManager.getElapsedTime();

        hud.setMoves(
            (int) elapsed
        );


        int stars =
            gameManager.calculateStars();


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
    }

    private void finishGame() {

        float elapsed =
            gameManager.getElapsedTime();


        int stars =
            gameManager.calculateStars();

        hud.setStars(
            stars
        );


        victoryOverlay.show(
            stars,
            Math.round(elapsed)
        );

        SaveManager.saveLevelResult(
            10,
            stars
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
    }
}
