package com.elma.braingames.puzzles.tangram;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.elma.braingames.utils.GameConfig;
import com.elma.braingames.BrainGames;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.tangram.input.TangramInputHandler;
import com.elma.braingames.puzzles.tangram.layout.TangramLayout;
import com.elma.braingames.puzzles.tangram.manager.TangramGameManager;
import com.elma.braingames.puzzles.tangram.render.TangramRenderer;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;

public class TangramPuzzleScreen
    extends BasePuzzleScreen {


    private TangramGameManager gameManager;

    private TangramRenderer renderer;

    private TangramLayout layout;

    private TangramInputHandler inputHandler;

    private VictoryOverlay victoryOverlay;


    public TangramPuzzleScreen(
        BrainGames game
    ) {

        super(game);
    }


    @Override
    public void show() {

        super.show();

        /*worldViewport =
            new FitViewport(
                GameConfig.TANGRAM_WORLD_WIDTH,
                GameConfig.TANGRAM_WORLD_HEIGHT,
                worldCamera
            );

        worldViewport.apply();

        worldCamera.position.set(
            GameConfig.TANGRAM_WORLD_WIDTH / 2f,
            GameConfig.TANGRAM_WORLD_HEIGHT / 2f,
            0
        );*/

        worldCamera.update();

        gameManager =
            new TangramGameManager();

        layout =
            new TangramLayout();


        layout.update(
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );

        layout.applyToBoard(
            gameManager.getBoard()
        );

        renderer =
            new TangramRenderer(
                gameManager,
                layout
            );

        inputHandler =
            new TangramInputHandler(
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

                () ->
                    game.setScreen(
                        new TangramPuzzleScreen(
                            game
                        )
                    ),

                //next
                () ->
                    game.setScreen(
                        new LevelSelectScreen(
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
                            new TangramPuzzleScreen(
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

        hud.setUseTimer(
            true
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


        float seconds =
            gameManager.getElapsedTime();


        hud.setRemainingTime(
            seconds
        );


        hud.setMoves(
            gameManager.getMoves()
        );


        hud.setStars(
            gameManager.calculateStars()
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

        if (
            seconds <= 35f
        ) {

            return 3;
        }


        if (
            seconds <= 45f
        ) {

            return 2;
        }


        if (
            seconds <= 60f
        ) {

            return 1;
        }


        return 0;
    }

    private void finishGame() {

        float seconds =
            gameManager.getElapsedTime();


        int stars =
            gameManager.calculateStars();


        hud.setStars(
            stars
        );


        victoryOverlay.show(
            stars,
            (int) seconds
        );


        SaveManager.saveLevelResult(
            11,
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

        layout.update(
            GameConfig.TANGRAM_WORLD_WIDTH,
            GameConfig.TANGRAM_WORLD_HEIGHT
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
