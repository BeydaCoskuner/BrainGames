package com.elma.braingames.puzzles.maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.hanoi.HanoiPuzzleScreen;
import com.elma.braingames.puzzles.maze.input.MazeInputHandler;
import com.elma.braingames.puzzles.maze.layout.MazeLayout;
import com.elma.braingames.puzzles.maze.manager.MazeGameManager;
import com.elma.braingames.puzzles.maze.render.MazeRenderer;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;
import com.elma.braingames.input.VictoryOverlayInput;

public class MazePuzzleScreen
    extends BasePuzzleScreen {

    private MazeGameManager gameManager;

    private MazeRenderer renderer;

    private VictoryOverlay victoryOverlay;
    private MazeLayout layout;

    private MazeInputHandler inputHandler;

    public MazePuzzleScreen(
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
            new MazeGameManager();

        layout =
            new MazeLayout();

        layout.update(
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );

        renderer =
            new MazeRenderer(
                gameManager
            );

        inputHandler =
            new MazeInputHandler(
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
                    new MazePuzzleScreen(game)
                ),

                () -> game.setScreen(
                    new HanoiPuzzleScreen(game)
                )
            )
        );
        multiplexer.addProcessor(
            inputHandler
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

                    if (victoryOverlay.isVisible()) {
                        return false;
                    }

                    Vector3 touch =
                        new Vector3(
                            screenX,
                            screenY,
                            0
                        );

                    uiViewport.unproject(touch);

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
                            new MazePuzzleScreen(game)
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
    public void render(float delta) {

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
    protected void update(float delta) {

        gameManager.update(delta);

        float elapsed =
            gameManager.getElapsedTime();


        hud.setRemainingTime(elapsed);
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

        if (seconds <= 45f) {

            return 3;

        } else if (seconds <= 90f) {

            return 2;

        } else if (seconds <= 120f) {

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
            0
        );

        SaveManager.saveLevelResult(
            4,
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
