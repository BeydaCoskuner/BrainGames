package com.elma.braingames.input;

import com.elma.braingames.BrainGames;
import com.elma.braingames.managers.PuzzleFactory;
import com.elma.braingames.puzzles.memory.MemoryPuzzleScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elma.braingames.constants.LevelConstants;
import com.elma.braingames.managers.LevelManager;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

import javax.print.attribute.standard.PrinterMessageFromOperator;

public class LevelInputHandler extends InputAdapter {

    private final Viewport viewport;
    private final LevelMap levelMap;
    private final LevelManager levelManager;

    private final Vector3 touch = new Vector3();

    private final BrainGames game;

    public LevelInputHandler(
        BrainGames game,
        Viewport viewport,
        LevelMap levelMap,
        LevelManager levelManager
    ){
        this.game = game;
        this.viewport = viewport;
        this.levelMap = levelMap;
        this.levelManager = levelManager;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, screenY, 0);

        viewport.unproject(touch);
        Gdx.app.log("TOUCH", "X=" + touch.x + " Y=" + touch.y);
        checkNodeSelection(touch.x, touch.y);

        return true;

    }
    private void checkNodeSelection(float x, float y) {

        for (LevelNode node : levelMap.getLevels()) {

            float dx = x - node.getX();
            float dy = y - node.getY();

            float distanceSquared = dx * dx + dy * dy;

            float touchRadius = LevelConstants.NODE_TOUCH_RADIUS;

            if (distanceSquared <= touchRadius * touchRadius) {

                if (levelManager.isSelected(node)) {

                    openLevel(node);

                } else {

                    levelManager.select(node);

                }

                return;

            }
        }

        levelManager.clearSelection();
    }
    private void openLevel(LevelNode node) {

        Gdx.app.log("LEVEL", "Open Level " + node.getId());

        if (PuzzleFactory.createPuzzle(game, node.getId()) != null) {

            game.setScreen(
                PuzzleFactory.createPuzzle(
                    game,
                    node.getId()
                )
            );

        } else {

            Gdx.app.log("LEVEL", "Bu bölüm henüz eklenmedi.");

        }

    }

}
