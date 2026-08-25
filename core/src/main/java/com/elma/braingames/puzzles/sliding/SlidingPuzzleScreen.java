package com.elma.braingames.puzzles.sliding;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.shake.ShakePuzzleScreen;
import com.elma.braingames.puzzles.sliding.input.SlidingInputHandler;
import com.elma.braingames.puzzles.sliding.manager.SlidingGameManager;
import com.elma.braingames.puzzles.sliding.render.SlidingRenderer;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;
import com.elma.braingames.input.VictoryOverlayInput;

public class SlidingPuzzleScreen extends BasePuzzleScreen {

    private final Vector3 touch = new Vector3();

    private SlidingGameManager gameManager;
    private SlidingRenderer renderer;
    private SlidingInputHandler inputHandler;
    private VictoryOverlay victoryOverlay;

    public SlidingPuzzleScreen(BrainGames game) {

        super(game);
    }

    @Override
    public void show() {

        super.show();

        worldViewport =
            new ScreenViewport(worldCamera);

        worldViewport.apply();

        worldCamera.position.set(
            worldViewport.getWorldWidth() / 2f,
            worldViewport.getWorldHeight() / 2f,
            0
        );

        worldCamera.update();

        gameManager =
            new SlidingGameManager();

        renderer =
            new SlidingRenderer();

        victoryOverlay =
            new VictoryOverlay();

        inputHandler =
            new SlidingInputHandler(
                worldViewport,
                gameManager,
                renderer.getLayout()
            );

        InputMultiplexer multiplexer =
            new InputMultiplexer();

        multiplexer.addProcessor(
            new VictoryOverlayInput(
                game,
                uiViewport,
                victoryOverlay,

                // Restart
                () -> game.setScreen(
                    new SlidingPuzzleScreen(game)
                ),

                // Map
                () -> game.setScreen(
                    new ShakePuzzleScreen(game)
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

                    if (victoryOverlay.isVisible()) {
                        return false;
                    }

                    touch.set(
                        screenX,
                        screenY,
                        0
                    );

                    uiViewport.unproject(touch);

                    if (hud.isBackPressed(
                        touch.x,
                        touch.y
                    )) {

                        game.setScreen(
                            new LevelSelectScreen(game)
                        );

                        return true;
                    }

                    if (hud.isRestartPressed(
                        touch.x,
                        touch.y
                    )) {

                        game.setScreen(
                            new SlidingPuzzleScreen(game)
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
    public void render(float delta) {

        super.render(delta);

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
    protected void update(float delta) {

        gameManager.update(delta);

        int moves = gameManager.getMoves();

        hud.setMoves(moves);

        int stars = calculateStars(moves);

        hud.setStars(stars);

        if (
            gameManager.isCompleted()
                &&
                !hud.isGameFinished()
        ) {

            hud.setGameFinished(true);

            finishGame();
        }
    }
    private int calculateStars(int moves) {

        if (moves <= 25) {

            return 3;

        } else if (moves <= 40) {

            return 2;

        } else if (moves <= 60) {

            return 1;

        } else {

            return 0;
        }
    }

    private void finishGame() {

        int moves = gameManager.getMoves();

        int stars = calculateStars(moves);

        hud.setStars(stars);

        victoryOverlay.show(
            stars,
            moves
        );

        SaveManager.saveLevelResult(2, stars
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
