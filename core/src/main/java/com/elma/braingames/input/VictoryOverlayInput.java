package com.elma.braingames.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elma.braingames.BrainGames;
import com.elma.braingames.puzzles.memory.MemoryPuzzleScreen;
import com.elma.braingames.screens.LevelSelectScreen;
import com.elma.braingames.ui.VictoryOverlay;

public class VictoryOverlayInput extends InputAdapter {

    private final BrainGames game;

    private final Viewport viewport;

    private final VictoryOverlay overlay;

    private final Runnable restartAction;

    private final Runnable nextAction;

    private final Vector3 touch = new Vector3();

    public VictoryOverlayInput(

        BrainGames game,
        Viewport viewport,
        VictoryOverlay overlay,
        Runnable restartAction,
        Runnable nextAction

    ) {

        this.game = game;
        this.viewport = viewport;
        this.overlay = overlay;

        this.restartAction = restartAction;
        this.nextAction = nextAction;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if (!overlay.isVisible()) {
            return false;
        }

        touch.set(screenX, screenY, 0);

        viewport.unproject(touch);

        if (overlay.isRestartPressed(touch.x, touch.y)) {

            restartAction.run();
            return true;

        }

        if (overlay.isNextPressed(touch.x, touch.y)) {

            nextAction.run();
            return true;

        }

        if (overlay.isMapPressed(touch.x, touch.y)) {

            game.setScreen(new LevelSelectScreen(game));
            return true;

        }

        return false;
    }
}
