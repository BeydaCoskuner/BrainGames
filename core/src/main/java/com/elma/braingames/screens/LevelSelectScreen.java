package com.elma.braingames.screens;

import com.elma.braingames.BrainGames;
import com.elma.braingames.puzzles.memory.MemoryPuzzleScreen;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.input.LevelInputHandler;
import com.elma.braingames.managers.AnimationManager;
import com.elma.braingames.managers.LevelManager;
import com.elma.braingames.input.CameraController;
import com.elma.braingames.render.PathRenderer;
import com.elma.braingames.generator.LevelGenerator;
import com.elma.braingames.render.LevelMapRenderer;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.utils.GameConfig;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;



public class LevelSelectScreen implements Screen {

    private OrthographicCamera camera;
    private Viewport viewport;
    private LevelMapRenderer levelMapRenderer;
    private LevelMap levelMap;
    //private PathRenderer pathRenderer;
    private CameraController cameraController;
    private LevelManager levelManager;
    private LevelInputHandler inputHandler;
    private AnimationManager animationManager;
    private final BrainGames game;

    public LevelSelectScreen(BrainGames game) {

        this.game = game;

    }


    @Override
    public void show() {
        Gdx.app.log("show","1");
        Gdx.app.log("BrainGames","LvlvSlctScreen açıldı");


        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH,4000,camera);

        viewport.apply();
        camera.position.set(2000 / 2f,4000 / 2f, 0);
        camera.update();

        levelMap = new LevelMap();
        levelMapRenderer = new LevelMapRenderer(levelMap);
        Gdx.app.log("show","3");

        //pathRenderer = new PathRenderer(levelMap);



        LevelGenerator.generate(levelMap,12);
        Gdx.app.log("show","2");


        levelManager = new LevelManager(levelMap);

        animationManager = new AnimationManager(levelMap);

        inputHandler = new LevelInputHandler(
            game,
            viewport,
            levelMap,
            levelManager
        );
        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(multiplexer);

        cameraController = new CameraController(camera,levelMap);

    }
    @Override
    public void render(float delta) {
        Gdx.app.log("android","render okeyy");

        Gdx.app.log("ANDROID",
            "viewport = "
                + viewport.getWorldWidth()
                + " x "
                + viewport.getWorldHeight());

        cameraController.update();
        camera.update();
        Gdx.app.log(
            "CAMERA",
            "x=" + camera.position.x +
                " y=" + camera.position.y +
                " zoom=" + camera.zoom
        );
        animationManager.update();
        Gdx.gl.glClearColor(0.12f, 0.12f, 0.18f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //pathRenderer.render(camera);
        levelMapRenderer.render(camera);
        /*if (levelManager.hasSelection()) {

            int levelId = levelManager.getSelectedLevelId();

            if (levelId == 1) {

                game.setScreen(new MemoryPuzzleScreen(game));
            }

        }*/

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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

        levelMapRenderer.dispose();
        //pathRenderer.dispose();
    }

}
