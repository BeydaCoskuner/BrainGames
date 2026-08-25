package com.elma.braingames.puzzles.hanoi.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.elma.braingames.puzzles.hanoi.layout.HanoiLayout;
import com.elma.braingames.puzzles.hanoi.manager.HanoiGameManager;
import com.elma.braingames.puzzles.hanoi.model.HanoiDisk;
import com.elma.braingames.puzzles.hanoi.model.HanoiRod;

public class HanoiRenderer {

    private final ShapeRenderer shapeRenderer;

    private final HanoiGameManager gameManager;

    private final HanoiLayout layout;
    private final SpriteBatch batch;

    private final BitmapFont font;


    public HanoiRenderer(
        HanoiGameManager gameManager,
        HanoiLayout layout
    ) {

        this.gameManager =
            gameManager;

        this.layout =
            layout;

        shapeRenderer =
            new ShapeRenderer();
        batch =
            new SpriteBatch();

        font =
            new BitmapFont();
    }


    public void render(
        OrthographicCamera camera
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


        drawRods();

        drawDisks();

        drawSelectedDisk();
        drawLabels();
    }
    private void drawLabels() {

        float width =
            layout.getBoardWidth();

        float height =
            layout.getBoardHeight();


        float fontScale =
            Math.min(width, height) / 700f;

        font.getData().setScale(
            fontScale
        );

        font.setColor(
            Color.DARK_GRAY
        );


        batch.setProjectionMatrix(
            shapeRenderer.getProjectionMatrix()
        );

        batch.begin();


        font.draw(
            batch,
            "START",
            layout.getRodX(0)
                - width * 0.055f,
            layout.getBaseY()
                - height * 0.035f
        );


        font.draw(
            batch,
            "FINISH",
            layout.getRodX(2)
                - width * 0.065f,
            layout.getBaseY()
                - height * 0.035f
        );


        batch.end();
    }


    private void drawRods() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        shapeRenderer.setColor(
            Color.DARK_GRAY
        );

        for (
            int i = 0;
            i < HanoiGameManager.ROD_COUNT;
            i++
        ) {

            float x =
                layout.getRodX(i);

            float y =
                layout.getRodY();

            float width =
                layout.getRodWidth();

            float height =
                layout.getRodHeight();


            shapeRenderer.rect(
                x - width / 2f,
                y,
                width,
                height
            );
        }

        for (
            int i = 0;
            i < HanoiGameManager.ROD_COUNT;
            i++
        ) {

            float x =
                layout.getRodX(i);

            float baseWidth =
                layout.getBoardWidth()
                    * 0.20f;


            shapeRenderer.rect(
                x - baseWidth / 2f,
                layout.getBaseY(),
                baseWidth,
                layout.getRodWidth()
            );
        }


        shapeRenderer.end();
    }


    private void drawDisks() {

        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        for (
            int rodIndex = 0;
            rodIndex < HanoiGameManager.ROD_COUNT;
            rodIndex++
        ) {

            HanoiRod rod =
                gameManager.getRod(
                    rodIndex
                );


            for (
                int diskIndex = 0;
                diskIndex < rod.size();
                diskIndex++
            ) {

                HanoiDisk disk =
                    rod.getDisk(
                        diskIndex
                    );


                drawDisk(
                    disk,
                    rodIndex,
                    diskIndex
                );
            }
        }


        shapeRenderer.end();
    }


    private void drawDisk(
        HanoiDisk disk,
        int rodIndex,
        int diskIndex
    ) {

        float centerX =
            layout.getRodX(
                rodIndex
            );


        float diskHeight =
            layout.getDiskHeight();


        float centerY =
            layout.getBaseY()
                + layout.getRodWidth()
                + diskHeight * diskIndex
                + diskHeight / 2f;


        float maxWidth =
            layout.getBoardWidth()
                * 0.20f;


        float minWidth =
            layout.getBoardWidth()
                * 0.07f;


        float width =
            minWidth
                + (
                disk.getSize() - 1
            )
                * (
                (maxWidth - minWidth)
                    / (
                    HanoiGameManager.DISK_COUNT - 1
                )
            );


        shapeRenderer.setColor(
            getDiskColor(
                disk.getSize()
            )
        );


        drawRoundedDisk(
            centerX,
            centerY,
            width,
            diskHeight,
            disk.getSize()
        );
    }
    private void drawRoundedDisk(
        float centerX,
        float centerY,
        float width,
        float height,
        int size
    ) {

        float radius =
            height / 2f;


        Color color =
            getDiskColor(size);

        shapeRenderer.setColor(color);


        shapeRenderer.rect(
            centerX - width / 2f + radius,
            centerY - height / 2f,
            width - height,
            height
        );

        shapeRenderer.circle(
            centerX - width / 2f + radius,
            centerY,
            radius
        );

        shapeRenderer.circle(
            centerX + width / 2f - radius,
            centerY,
            radius
        );
    }


    /*private void drawSelectedDisk() {

        HanoiDisk disk =
            gameManager.getSelectedDisk();


        if (disk == null) {
            return;
        }


        float centerX =
            layout.getRodX(
                gameManager.getSelectedRod()
            );


        float diskHeight =
            layout.getDiskHeight();


        float centerY =
            layout.getRodY()
                + layout.getRodHeight()
                + diskHeight;


        float maxWidth =
            layout.getBoardWidth()
                * 0.20f;


        float minWidth =
            layout.getBoardWidth()
                * 0.07f;


        float width =
            minWidth
                + (
                disk.getSize() - 1
            )
                * (
                (maxWidth - minWidth)
                    / (
                    HanoiGameManager.DISK_COUNT - 1
                )
            );


        shapeRenderer.setColor(
            getDiskColor(
                disk.getSize()
            )
        );


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );


        Color selectedColor =
            getDiskColor(
                disk.getSize()
            );

        selectedColor =
            selectedColor.cpy().mul(
                1.25f
            );

        shapeRenderer.setColor(
            selectedColor
        );

        drawRoundedDisk(
            centerX,
            centerY,
            width,
            diskHeight,
            disk.getSize()
        );


        shapeRenderer.end();
    }*/

    private void drawSelectedDisk() {

        HanoiDisk disk =
            gameManager.getSelectedDisk();

        if (disk == null) {
            return;
        }


        float centerX =
            layout.getRodX(
                gameManager.getSelectedRod()
            );


        float diskHeight =
            layout.getDiskHeight();


        float centerY =
            layout.getRodY()
                + layout.getRodHeight()
                + diskHeight;


        float maxWidth =
            layout.getBoardWidth()
                * 0.20f;


        float minWidth =
            layout.getBoardWidth()
                * 0.07f;


        float width =
            minWidth
                +
                (
                    disk.getSize() - 1
                )
                    *
                    (
                        (maxWidth - minWidth)
                            /
                            (HanoiGameManager.DISK_COUNT - 1)
                    );


        shapeRenderer.begin(
            ShapeRenderer.ShapeType.Filled
        );

        shapeRenderer.setColor(
            new Color(
                1f,
                1f,
                1f,
                0.08f
            )
        );

        drawGlow(
            centerX,
            centerY,
            width,
            diskHeight,
            1.35f
        );

        shapeRenderer.setColor(
            new Color(
                1f,
                1f,
                1f,
                0.14f
            )
        );

        drawGlow(
            centerX,
            centerY,
            width,
            diskHeight,
            1.18f
        );

        shapeRenderer.setColor(
            new Color(
                1f,
                1f,
                1f,
                0.22f
            )
        );

        drawGlow(
            centerX,
            centerY,
            width,
            diskHeight,
            1.08f
        );

        drawRoundedDisk(
            centerX,
            centerY,
            width,
            diskHeight,
            disk.getSize()
        );

        shapeRenderer.setColor(
            new Color(
                1f,
                1f,
                1f,
                0.30f
            )
        );

        shapeRenderer.rect(
            centerX - width * 0.30f,
            centerY + diskHeight * 0.18f,
            width * 0.60f,
            diskHeight * 0.12f
        );


        shapeRenderer.end();
    }

    private void drawGlow(
        float centerX,
        float centerY,
        float width,
        float height,
        float scale
    ) {

        float glowWidth =
            width * scale;

        float glowHeight =
            height * scale;

        float radius =
            glowHeight / 2f;


        shapeRenderer.rect(
            centerX - glowWidth / 2f + radius,
            centerY - glowHeight / 2f,
            glowWidth - glowHeight,
            glowHeight
        );


        shapeRenderer.circle(
            centerX - glowWidth / 2f + radius,
            centerY,
            radius
        );


        shapeRenderer.circle(
            centerX + glowWidth / 2f - radius,
            centerY,
            radius
        );
    }

    private Color getDiskColor(int size) {

        switch (size) {

            case 5:
                return Color.GOLD;

            case 4:
                return Color.RED;

            case 3:
                return Color.GREEN;

            case 2:
                return Color.PURPLE;

            case 1:
                return Color.CYAN;

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
