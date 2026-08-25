package com.elma.braingames.puzzles;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.elma.braingames.BrainGames;
import com.badlogic.gdx.Screen;
import com.elma.braingames.ui.GameHUD;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elma.braingames.utils.GameConfig;

public abstract class BasePuzzleScreen implements Screen {

    protected GameHUD hud;
    protected OrthographicCamera worldCamera;
    protected Viewport worldViewport;

    protected OrthographicCamera uiCamera;
    protected Viewport uiViewport;

    protected final BrainGames game;

    public BasePuzzleScreen(BrainGames game){

        this.game = game;

    }

    @Override
    public void show() {

        worldCamera = new OrthographicCamera();

        worldViewport = new FitViewport(
            GameConfig.WORLD_WIDTH,
            GameConfig.WORLD_HEIGHT,
            worldCamera
        );

        worldViewport.apply();

        worldCamera.position.set(
            GameConfig.WORLD_WIDTH / 2f,
            GameConfig.WORLD_HEIGHT / 2f,
            0
        );
        Gdx.app.log(
            "Camerabir",
            "Zoom = " + worldCamera.zoom
        );

        worldCamera.update();


        uiCamera = new OrthographicCamera();

        uiViewport = new ScreenViewport(uiCamera);

        uiViewport.apply();

        uiCamera.position.set(
            uiViewport.getWorldWidth() / 2f,
            uiViewport.getWorldHeight() / 2f,
            0
        );

        uiCamera.update();

        hud = new GameHUD();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(
            1f,
            1f,
            1f,
            1f
        );

        Gdx.gl.glClear(
            GL20.GL_COLOR_BUFFER_BIT
        );

        worldViewport.apply();
        Gdx.app.log(
            "Viewport",
            "WorldViewport = "
                + worldViewport.getWorldWidth()
                + " x "
                + worldViewport.getWorldHeight()
        );

        worldCamera.update();

        update(delta);

        uiViewport.apply();

        uiCamera.update();

        hud.update(delta);

        hud.render(uiCamera);

    }

    protected abstract void update(float delta);

    @Override
    public void resize(int width, int height) {

        worldViewport.update(
            width,
            height,
            true
        );

        uiViewport.update(
            width,
            height,
            true
        );


        Gdx.app.log(
            "UI_SIZE",
            "UI Width = "
                + uiViewport.getWorldWidth()
                + " UI Height = "
                + uiViewport.getWorldHeight()
        );
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

        hud.dispose();

    }

}
