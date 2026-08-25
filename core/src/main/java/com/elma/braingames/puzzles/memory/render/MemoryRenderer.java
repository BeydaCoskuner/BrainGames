package com.elma.braingames.puzzles.memory.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.elma.braingames.managers.TextureManager;
import com.elma.braingames.puzzles.memory.manager.MemoryGameManager;
import com.elma.braingames.puzzles.memory.model.MemoryCard;

public class MemoryRenderer {

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private static final float BACK_SCALE = 1.04f;


    public MemoryRenderer() {

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getData().setScale(3f);
    }


    public void render(
        OrthographicCamera camera,
        MemoryGameManager gameManager
    ) {

        if (gameManager.getCards().isEmpty()) {
            return;
        }


        MemoryCard first =
            gameManager.getCards().get(0);

        Gdx.app.log(
            "BEFORE_RENDER",
            "W=" + first.getWidth()
                + " H=" + first.getHeight()
        );


        shapeRenderer.setProjectionMatrix(
            camera.combined
        );

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        for (MemoryCard card :
            gameManager.getCards()) {

            drawFrontCard(card);
        }

        shapeRenderer.end();



        batch.setProjectionMatrix(
            camera.combined
        );

        batch.begin();

        for (MemoryCard card :
            gameManager.getCards()) {

            drawBackCard(card);
        }

        batch.end();
    }



    private void drawFrontCard(
        MemoryCard card
    ) {

        boolean showFront =
            card.isFlipped();

        if (card.isFlipping()) {

            if (card.isFlipForward()) {

                showFront =
                    card.getFlipProgress() >= 0.5f;

            } else {

                showFront =
                    card.getFlipProgress() < 0.5f;
            }
        }

        if (!showFront &&
            !card.isMatched()) {

            return;
        }


        float drawWidth =
            card.getWidth()
                * card.getAnimationScale();


        float drawHeight =
            card.getHeight()
                * card.getAnimationScale();


        if (card.isFlipping()) {

            float progress =
                card.getFlipProgress();

            float scale;

            if (progress < 0.5f) {

                scale =
                    1f - progress * 2f;

            } else {

                scale =
                    (progress - 0.5f) * 2f;
            }

            drawWidth =
                card.getWidth() * scale;
        }

        float drawX =
            card.getX()
                + (card.getWidth() - drawWidth) / 2f;


        float drawY =
            card.getY()
                + (card.getHeight() - drawHeight) / 2f;


        Color color;

        if (card.isMatched()) {

            color =
                getCardColor(
                    card.getPairId()
                )
                    .cpy()
                    .mul(0.8f);

        } else {

            color =
                getCardColor(
                    card.getPairId()
                );
        }


        shapeRenderer.setColor(color);


        shapeRenderer.rect(
            drawX,
            drawY,
            drawWidth,
            drawHeight
        );
    }

    private void drawBackCard(
        MemoryCard card
    ) {

        boolean showFront =
            card.isFlipped();


        if (card.isFlipping()) {

            if (card.isFlipForward()) {

                showFront =
                    card.getFlipProgress() >= 0.5f;

            } else {

                showFront =
                    card.getFlipProgress() < 0.5f;
            }
        }


        if (showFront ||
            card.isMatched()) {

            return;
        }


        TextureRegion texture =
            TextureManager.getCardBackRegion();


        if (texture == null) {

            Gdx.app.error(
                "MEMORY_RENDERER",
                "Card back texture NULL!"
            );

            return;
        }



        float drawWidth =
            card.getWidth()
                * card.getAnimationScale();


        float drawHeight =
            card.getHeight()
                * card.getAnimationScale();

        if (card.isFlipping()) {

            float progress =
                card.getFlipProgress();


            if (progress < 0.5f) {

                drawWidth =
                    card.getWidth()
                        * (1f - progress * 2f);

            } else {

                drawWidth =
                    card.getWidth()
                        * ((progress - 0.5f) * 2f);
            }
        }


        drawWidth *= BACK_SCALE;
        drawHeight *= BACK_SCALE;

        float drawX =
            card.getX()
                + (card.getWidth() - drawWidth) / 2f;


        float drawY =
            card.getY()
                + (card.getHeight() - drawHeight) / 2f;


        Gdx.app.log(
            "BACK_DRAW",
            "X=" + drawX
                + " Y=" + drawY
                + " W=" + drawWidth
                + " H=" + drawHeight
        );


        batch.draw(
            texture,
            drawX,
            drawY,
            drawWidth,
            drawHeight
        );
    }

    private Color getCardColor(
        int pairId
    ) {

        switch (pairId) {

            case 1:
                return Color.RED;

            case 2:
                return Color.BLUE;

            case 3:
                return Color.GREEN;

            case 4:
                return Color.YELLOW;

            case 5:
                return Color.PURPLE;

            case 6:
                return Color.ORANGE;

            case 7:
                return Color.CYAN;

            case 8:
                return Color.PINK;

            case 9:
                return Color.LIME;

            case 10:
                return Color.BROWN;

            default:
                return Color.WHITE;
        }
    }


    public void dispose() {

        shapeRenderer.dispose();

        batch.dispose();

        font.dispose();
    }
}
