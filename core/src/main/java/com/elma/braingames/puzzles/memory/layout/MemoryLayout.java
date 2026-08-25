package com.elma.braingames.puzzles.memory.layout;

import com.badlogic.gdx.Gdx;
import com.elma.braingames.puzzles.memory.model.MemoryCard;

import java.util.List;

public class MemoryLayout {

    private static final int COLUMNS = 4;
    private static final int ROWS = 5;

    private static final float CARD_RATIO = 1.5f;


    public void layoutCards(
        List<MemoryCard> cards,
        float worldWidth,
        float worldHeight
    ) {

        Gdx.app.log(
            "MEMORY_LAYOUT",
            "World = "
                + worldWidth
                + " x "
                + worldHeight
        );

        float topReserved =
            worldHeight * 0.12f;

        float bottomReserved =
            worldHeight * 0.12f;


        float horizontalMargin =
            worldWidth * 0.04f;

        float horizontalSpacing =
            worldWidth * 0.018f;

        float verticalSpacing =
            worldHeight * 0.018f;

        float playAreaX =
            horizontalMargin;

        float playAreaY =
            bottomReserved;

        float playAreaWidth =
            worldWidth
                - horizontalMargin * 2f;

        float playAreaHeight =
            worldHeight
                - topReserved
                - bottomReserved;


        Gdx.app.log(
            "MEMORY_AREA",
            "X=" + playAreaX
                + " Y=" + playAreaY
                + " W=" + playAreaWidth
                + " H=" + playAreaHeight
        );

        float cardWidth =
            (
                playAreaWidth
                    - horizontalSpacing * (COLUMNS - 1)
            )
                / COLUMNS;


        float cardHeight =
            cardWidth * CARD_RATIO;

        float requiredHeight =
            cardHeight * ROWS
                + verticalSpacing * (ROWS - 1);

        //kartlar sığmıyorsa küçült
        if (requiredHeight > playAreaHeight) {

            cardHeight =
                (
                    playAreaHeight
                        - verticalSpacing * (ROWS - 1)
                )
                    / ROWS;

            cardWidth =
                cardHeight / CARD_RATIO;
        }


        Gdx.app.log(
            "MEMORY_SIZE",
            "Card Width = "
                + cardWidth
                + " Card Height = "
                + cardHeight
        );

        float totalWidth =
            cardWidth * COLUMNS
                + horizontalSpacing * (COLUMNS - 1);

        float totalHeight =
            cardHeight * ROWS
                + verticalSpacing * (ROWS - 1);

        float startX =
            playAreaX
                + (playAreaWidth - totalWidth) / 2f;


        float startY =
            playAreaY
                + (playAreaHeight - totalHeight) / 2f
                + totalHeight
                - cardHeight;


        Gdx.app.log(
            "MEMORY_START",
            "StartX = "
                + startX
                + " StartY = "
                + startY
        );


        int index = 0;


        for (int row = 0; row < ROWS; row++) {

            for (int col = 0; col < COLUMNS; col++) {

                if (index >= cards.size()) {
                    return;
                }


                MemoryCard card =
                    cards.get(index++);


                card.setWidth(cardWidth);

                card.setHeight(cardHeight);


                card.setX(
                    startX
                        + col
                        * (
                        cardWidth
                            + horizontalSpacing
                    )
                );


                card.setY(
                    startY
                        - row
                        * (
                        cardHeight
                            + verticalSpacing
                    )
                );


                /*
                 * DEBUG
                 */

                if (index == 1
                    || index == cards.size()) {

                    Gdx.app.log(
                        "MEMORY_CARD",
                        "Index="
                            + index
                            + " X="
                            + card.getX()
                            + " Y="
                            + card.getY()
                            + " W="
                            + card.getWidth()
                            + " H="
                            + card.getHeight()
                    );
                }
            }
        }
    }
}
