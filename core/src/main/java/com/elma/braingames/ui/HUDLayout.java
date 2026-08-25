package com.elma.braingames.ui;

import com.badlogic.gdx.math.Rectangle;

public class HUDLayout {

    public final Rectangle backButton = new Rectangle();

    public final Rectangle starsPanel = new Rectangle();

    public final Rectangle movesPanel = new Rectangle();

    public final Rectangle restartButton = new Rectangle();


    public void update(float width, float height) {


        float unit = Math.min(width, height);




        float horizontalPadding = unit * 0.04f;

        float topPadding = unit * 0.025f;

        float gap = unit * 0.015f;


        float backSize = unit * 0.22f;


        float topBarHeight = backSize;


        float topY =
            height
                - topPadding
                - topBarHeight;


        float usableWidth =
            width
                - horizontalPadding * 2f
                - backSize
                - gap * 2f;


        float starsWidth =
            usableWidth * 0.64f;



        float movesWidth =
            usableWidth - starsWidth;



        backButton.set(
            horizontalPadding,
            topY,
            backSize,
            backSize
        );

        starsPanel.set(
            backButton.x
                + backButton.width
                + gap,

            topY,

            starsWidth,

            topBarHeight
        );
        movesPanel.set(
            starsPanel.x
                + starsPanel.width
                + gap,

            topY,

            movesWidth,

            topBarHeight
        );


        float restartWidth =
            unit * 0.40f;

        float restartHeight =
            restartWidth * 0.9f;


        float bottomPadding =0f;
            //unit * 0.035f;


        restartButton.set(

            (width - restartWidth) / 2f,

            bottomPadding,

            restartWidth,

            restartHeight
        );
    }
}
