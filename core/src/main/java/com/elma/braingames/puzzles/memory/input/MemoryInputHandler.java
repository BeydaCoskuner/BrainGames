package com.elma.braingames.puzzles.memory.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.elma.braingames.puzzles.memory.manager.MemoryGameManager;
import com.elma.braingames.puzzles.memory.model.MemoryCard;

public class MemoryInputHandler extends InputAdapter {

    private final Viewport viewport;
    private final MemoryGameManager gameManager;

    private final Vector3 touch = new Vector3();

    public MemoryInputHandler(
        Viewport viewport,
        MemoryGameManager gameManager
    ) {
        this.viewport = viewport;
        this.gameManager = gameManager;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, screenY, 0);

        viewport.unproject(touch);
        Gdx.app.log(
            "MemoryTouch",
            "Touch = X:" + touch.x + " Y:" + touch.y
        );

        for (MemoryCard card : gameManager.getCards()) {

            if (touch.x >= card.getX()
                && touch.x <= card.getX() + card.getWidth()
                && touch.y >= card.getY()
                && touch.y <= card.getY() + card.getHeight()) {

                Gdx.app.log(
                    "MemoryTouch",
                    "Card -> X:" + card.getX()
                        + " Y:" + card.getY()
                        + " W:" + card.getWidth()
                        + " H:" + card.getHeight()
                );
                gameManager.flipCard(card);
                break;
            }
        }

        return true;
    }
}
