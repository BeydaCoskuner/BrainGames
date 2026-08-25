package com.elma.braingames.puzzles.colorsequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;

import com.elma.braingames.puzzles.BasePuzzleScreen;

import com.elma.braingames.puzzles.colorsequence.input.ColorSequenceInputHandler;
import com.elma.braingames.puzzles.colorsequence.layout.ColorSequenceLayout;
import com.elma.braingames.puzzles.colorsequence.manager.ColorSequenceGameManager;
import com.elma.braingames.puzzles.colorsequence.render.ColorSequenceRenderer;

import com.elma.braingames.puzzles.numbersequence.NumberSequencePuzzleScreen;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;


public class ColorSequencePuzzleScreen
    extends BasePuzzleScreen {


    private final Vector3 touch =
        new Vector3();


    private ColorSequenceGameManager gameManager;

    private ColorSequenceLayout layout;

    private ColorSequenceRenderer renderer;

    private ColorSequenceInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public ColorSequencePuzzleScreen(
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
            "COLOR_SEQUENCE_WORLD",
            "World Width = "
                + worldViewport.getWorldWidth()
                + " World Height = "
                + worldViewport.getWorldHeight()
        );

        gameManager =
            new ColorSequenceGameManager();


        layout =
            new ColorSequenceLayout();


        renderer =
            new ColorSequenceRenderer(
                layout
            );


        inputHandler =
            new ColorSequenceInputHandler(
                worldViewport,
                layout,
                gameManager
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

                () ->
                    game.setScreen(
                        new ColorSequencePuzzleScreen(
                            game
                        )
                    ),

                //next
                () ->
                    game.setScreen(
                        new NumberSequencePuzzleScreen(
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
                            new ColorSequencePuzzleScreen(
                                game
                            )
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

        hud.setUseTimer(false);

        hud.setMoves(1);

        hud.setStars(
            gameManager.calculateStars()
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
            worldCamera,
            gameManager
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

        int moves =
            gameManager.getMoves();


        hud.setMoves(
            moves
        );

        int stars =
            gameManager.calculateStars();


        hud.setStars(
            stars
        );

        if (
            gameManager.isGameOver()
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

        if (moves <= 3) {

            return 0;
        }


        if (moves <= 6) {

            return 1;
        }


        if (moves <= 8) {

            return 2;
        }


        return 3;
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

        victoryOverlay.show(
            stars,
            moves
        );

        SaveManager.saveLevelResult(
            9,
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


        Gdx.app.log(
            "COLOR_SEQUENCE_RESIZE",
            "World = "
                + worldViewport.getWorldWidth()
                + " x "
                + worldViewport.getWorldHeight()
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
