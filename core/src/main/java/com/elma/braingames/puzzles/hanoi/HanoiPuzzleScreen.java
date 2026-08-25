package com.elma.braingames.puzzles.hanoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;

import com.elma.braingames.puzzles.BasePuzzleScreen;

import com.elma.braingames.puzzles.hanoi.input.HanoiInputHandler;
import com.elma.braingames.puzzles.hanoi.layout.HanoiLayout;
import com.elma.braingames.puzzles.hanoi.manager.HanoiGameManager;
import com.elma.braingames.puzzles.hanoi.render.HanoiRenderer;

import com.elma.braingames.puzzles.keyrescue.KeyRescuePuzzleScreen;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;


public class HanoiPuzzleScreen
    extends BasePuzzleScreen {


    private HanoiGameManager gameManager;

    private HanoiRenderer renderer;

    private HanoiLayout layout;

    private HanoiInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public HanoiPuzzleScreen(
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
            new HanoiGameManager();


        layout =
            new HanoiLayout();


        layout.update(
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );


        renderer =
            new HanoiRenderer(
                gameManager,
                layout
            );


        inputHandler =
            new HanoiInputHandler(
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


                () -> game.setScreen(
                    new HanoiPuzzleScreen(game)
                ),

                //next
                () -> game.setScreen(
                    new KeyRescuePuzzleScreen(game)
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
                            new HanoiPuzzleScreen(game)
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

        if (
            gameManager.isCompleted()
                &&
                !hud.isGameFinished()
        ) {

            hud.setGameFinished(true);

            finishGame();
        }

        hud.setMoves(
            gameManager.getMoves()
        );
        hud.setStars(
            calculateStars(
                gameManager.getMoves()
            )
        );
    }


    private int calculateStars(
        int moves
    ) {

        if (moves <= 32) {

            return 3;

        } else if (moves <= 40) {

            return 2;

        } else if (moves <= 50) {

            return 1;

        } else {

            return 0;
        }
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
            5,
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


        if (renderer != null) {

            renderer.dispose();
        }


        if (victoryOverlay != null) {

            victoryOverlay.dispose();
        }
    }
}
