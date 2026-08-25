package com.elma.braingames.puzzles.sliding.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.elma.braingames.puzzles.sliding.layout.SlidingLayout;
import com.elma.braingames.puzzles.sliding.manager.SlidingGameManager;

public class SlidingRenderer {

    private static final Color COLOR_CORRECT = new Color(0.35f, 0.85f, 0.35f, 1f); // Açık yeşil
    private static final Color COLOR_DEFAULT = Color.CYAN;                           // Varsayılan taş rengi
    private static final Color COLOR_EMPTY = Color.LIGHT_GRAY;                       // Boş kare rengi

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private final BitmapFont font;

    private final SlidingLayout layout;
    private final GlyphLayout glyphLayout;

    public SlidingRenderer() {

        shapeRenderer = new ShapeRenderer();

        batch = new SpriteBatch();

        font = new BitmapFont();

        layout = new SlidingLayout();

        glyphLayout = new GlyphLayout();
    }

    public void render(
        OrthographicCamera camera,
        SlidingGameManager gameManager
    ) {

        float width =
            camera.viewportWidth;

        float height =
            camera.viewportHeight;

        layout.update(
            width,
            height
        );

        shapeRenderer.setProjectionMatrix(
            camera.combined
        );

        batch.setProjectionMatrix(
            camera.combined
        );

        drawBoard(gameManager);

        drawNumbers(
            gameManager
        );
    }

    private void drawBoard(
        SlidingGameManager gameManager
    ) {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                int value =
                    gameManager.getTile(
                        row,
                        col
                    );

                if (value == 0) {

                    shapeRenderer.setColor(
                        COLOR_EMPTY
                    );

                } else {

                    int correctValue = row * 3 + col + 1;

                    if (value == correctValue) {
                        shapeRenderer.setColor(COLOR_CORRECT);
                    } else {
                        shapeRenderer.setColor(COLOR_DEFAULT);
                    }
                }

                shapeRenderer.rect(
                    layout.getTileX(col),
                    layout.getTileY(row),
                    layout.getTileSize(),
                    layout.getTileSize()
                );
            }
        }

        shapeRenderer.end();
    }

    private void drawNumbers(
        SlidingGameManager gameManager
    ) {

        batch.begin();

        for (int row = 0; row < 3; row++) {

            for (int col = 0; col < 3; col++) {

                int value =
                    gameManager.getTile(
                        row,
                        col
                    );

                if (value == 0) {
                    continue;
                }

                String text =
                    String.valueOf(value);

                float fontScale =
                    layout.getTileSize() / 120f;

                font.getData().setScale(
                    fontScale
                );

                font.setColor(
                    Color.BLACK
                );

                glyphLayout.setText(
                    font,
                    text
                );

                float textX =
                    layout.getTileX(col)
                        + (
                        layout.getTileSize()
                            - glyphLayout.width
                    ) / 2f;

                float textY =
                    layout.getTileY(row)
                        + (
                        layout.getTileSize()
                            + glyphLayout.height
                    ) / 2f;

                font.draw(
                    batch,
                    glyphLayout,
                    textX,
                    textY
                );
            }
        }

        batch.end();
    }

    public SlidingLayout getLayout() {

        return layout;
    }

    public void dispose() {

        shapeRenderer.dispose();

        batch.dispose();

        font.dispose();
    }
}
