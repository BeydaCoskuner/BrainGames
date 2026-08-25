package com.elma.braingames.puzzles.shake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.elma.braingames.BrainGames;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.maze.MazePuzzleScreen;
import com.elma.braingames.puzzles.shake.manager.ShakeGameManager;
import com.elma.braingames.puzzles.shake.render.ShakeRenderer;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;
import com.elma.braingames.input.VictoryOverlayInput;

public class ShakePuzzleScreen extends BasePuzzleScreen {

    private ShakeGameManager gameManager;

    private ShakeRenderer renderer;

    private VictoryOverlay victoryOverlay;

    public ShakePuzzleScreen(BrainGames game) {

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
            new ShakeGameManager();

        renderer =
            new ShakeRenderer();

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
                    new ShakePuzzleScreen(game)
                ),

                // Next
                () -> game.setScreen(
                    new MazePuzzleScreen(game)
                )
            )
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

        // Shake kontrolü
        gameManager.update(delta);

        if (
            gameManager.isCompleted()
                &&
                !hud.isGameFinished()
        ) {

            hud.setGameFinished(true);

            finishGame();
        }

        worldViewport.apply();

        worldCamera.update();

        renderer.render(
            worldCamera
        );

        uiViewport.apply();

        uiCamera.update();

        victoryOverlay.render(
            uiCamera
        );
    }

    @Override
    protected void update(float delta) {

    }

    private void finishGame() {

        int stars = 3;

        hud.setStars(stars);

        victoryOverlay.show(
            stars,
            0
        );

        SaveManager.saveLevelResult(
            3,
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
