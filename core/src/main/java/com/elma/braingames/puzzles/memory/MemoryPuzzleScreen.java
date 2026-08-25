package com.elma.braingames.puzzles.memory;

import com.badlogic.gdx.Gdx;
import com.elma.braingames.input.VictoryOverlayInput;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.puzzles.memory.input.MemoryInputHandler;
import com.elma.braingames.puzzles.memory.model.MemoryCard;
import com.elma.braingames.puzzles.memory.render.MemoryRenderer;
import com.elma.braingames.BrainGames;
import com.elma.braingames.puzzles.BasePuzzleScreen;
import com.elma.braingames.puzzles.memory.layout.MemoryLayout;
import com.elma.braingames.puzzles.memory.manager.MemoryGameManager;
import com.elma.braingames.puzzles.sliding.SlidingPuzzleScreen;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;

public class MemoryPuzzleScreen extends BasePuzzleScreen {


    private final Vector3 touch = new Vector3();
    private MemoryGameManager gameManager;

    private MemoryLayout layout;

    private MemoryRenderer renderer;

    private MemoryInputHandler inputHandler;
    private VictoryOverlay victoryOverlay;

    public MemoryPuzzleScreen(BrainGames game){

        super(game);
    }

    @Override
    public void show() {

        super.show();

        worldViewport = new com.badlogic.gdx.utils.viewport.ScreenViewport(
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
            "MEMORY_WORLD",
            "World Width = "
                + worldViewport.getWorldWidth()
                + " World Height = "
                + worldViewport.getWorldHeight()
        );

        gameManager = new MemoryGameManager();

        layout = new MemoryLayout();

        victoryOverlay = new VictoryOverlay();
        InputMultiplexer multiplexer = new InputMultiplexer();

       /* multiplexer.addProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if (!victoryOverlay.isVisible()) {
                    return false;
                }

                touch.set(screenX, screenY, 0);

                uiViewport.unproject(touch);

                if (victoryOverlay.isRestartPressed(touch.x, touch.y)) {

                    game.setScreen(new MemoryPuzzleScreen(game));
                    return true;

                }

                if (victoryOverlay.isMapPressed(touch.x, touch.y)) {

                    game.setScreen(new LevelSelectScreen(game));
                    return true;

                }

                if (victoryOverlay.isNextPressed(touch.x, touch.y)) {

                    // Şimdilik sonraki level yok.
                    game.setScreen(new LevelSelectScreen(game));
                    return true;

                }

                return false;
            }

        });*/

        multiplexer.addProcessor(

            new VictoryOverlayInput(

                game,

                uiViewport,

                victoryOverlay,

                () -> game.setScreen(
                    new MemoryPuzzleScreen(game)
                ),

                () -> game.setScreen(
                    new SlidingPuzzleScreen(game)
                )

            )

        );



        layout.layoutCards(

            gameManager.getCards(),

            worldViewport.getWorldWidth(),

            worldViewport.getWorldHeight()

        );

        MemoryCard first = gameManager.getCards().get(0);

        for (MemoryCard card : gameManager.getCards()) {


        }

        renderer = new MemoryRenderer();

        inputHandler = new MemoryInputHandler(
            worldViewport,
            gameManager
        );

        multiplexer.addProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if(victoryOverlay.isVisible()){
                    return false;
                }

                touch.set(screenX, screenY, 0);

                uiViewport.unproject(touch);

                if (hud.isBackPressed(touch.x, touch.y)) {

                    game.setScreen(new LevelSelectScreen(game));

                    return true;
                }

                if (hud.isRestartPressed(touch.x, touch.y)) {

                    game.setScreen(new MemoryPuzzleScreen(game));

                    return true;
                }

                return false;
            }

        });

        multiplexer.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(multiplexer);

        hud.setUseTimer(false);
        hud.setMoves(0);

    }

    @Override
    public void render(float delta) {

        super.render(delta);
        worldViewport.apply();

        Gdx.app.log(
            "Cameraiki",
            "Viewport = "
                + worldCamera.viewportWidth
                + " x "
                + worldCamera.viewportHeight
        );

        renderer.render(worldCamera, gameManager);

        uiViewport.apply();

        victoryOverlay.render(uiCamera);

    }

    private int calculateStars(int moves) {

        if (moves <= 15) {
            return 3;
        }

        if (moves <= 20) {
            return 2;
        }

        if (moves <= 25) {
            return 1;
        }

        return 0;
    }
    @Override
    protected void update(float delta) {

        gameManager.update(delta);

        int moves = gameManager.getMoves();

        hud.setMoves(moves);

        int stars = calculateStars(moves);

        hud.setStars(stars);

        if (gameManager.isCompleted() && !hud.isGameFinished()) {

            hud.setGameFinished(true);

            finishGame();
        }
    }
    @Override
    public void resize(int width, int height)
    {

        super.resize(width, height);


        worldCamera.position.set(
            worldViewport.getWorldWidth() / 2f,
            worldViewport.getWorldHeight() / 2f,
            0
        );

        worldCamera.update();

        worldViewport.update(
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight(),
            true
        );

        layout.layoutCards(
            gameManager.getCards(),
            worldViewport.getWorldWidth(),
            worldViewport.getWorldHeight()
        );


        Gdx.app.log(
            "MEMORY_RESIZE",
            "World = "
                + worldViewport.getWorldWidth()
                + " x "
                + worldViewport.getWorldHeight()
        );
    }
    private void finishGame() {

        int moves = gameManager.getMoves();

        int stars = calculateStars(moves);

        hud.setStars(stars);

        victoryOverlay.show(
            stars,
            moves
        );

        SaveManager.saveLevelResult(
            1,
            stars
        );
    }

    @Override
    public void dispose() {

        super.dispose();

        renderer.dispose();

    }

}
