package com.elma.braingames.puzzles.colorsequence.layout;

import com.elma.braingames.puzzles.colorsequence.model.ColorSequenceBoard;

public class ColorSequenceLayout {

    private float boardX;
    private float boardY;

    private float boardWidth;
    private float boardHeight;

    private float buttonWidth;
    private float buttonHeight;

    private float horizontalGap;
    private float verticalGap;


    public void update(
        float width,
        float height
    ) {


        float availableHeight =
            height * 0.68f;


        boardWidth =
            Math.min(
                width * 0.82f,
                availableHeight * 0.78f
            );


        //4 satır
        horizontalGap =
            boardWidth * 0.035f;

        verticalGap =
            boardWidth * 0.035f;


        buttonWidth =
            (
                boardWidth
                    - horizontalGap
                    * (ColorSequenceBoard.COLS - 1)
            )
                / ColorSequenceBoard.COLS;


        buttonHeight =
            buttonWidth;


        boardHeight =
            buttonHeight
                * ColorSequenceBoard.ROWS
                + verticalGap
                * (ColorSequenceBoard.ROWS - 1);
        //tahta yatayda

        boardX =
            (width - boardWidth) / 2f;

        boardY =
            height * 0.22f;
    }


    public float getBoardX() {

        return boardX;
    }


    public float getBoardY() {

        return boardY;
    }


    public float getBoardWidth() {

        return boardWidth;
    }


    public float getBoardHeight() {

        return boardHeight;
    }


    public float getButtonWidth() {

        return buttonWidth;
    }


    public float getButtonHeight() {

        return buttonHeight;
    }


    public float getHorizontalGap() {

        return horizontalGap;
    }


    public float getVerticalGap() {

        return verticalGap;
    }


    public float getButtonX(
        int col
    ) {

        return
            boardX
                + col
                * (
                buttonWidth
                    + horizontalGap
            );
    }


    public float getButtonY(
        int row
    ) {

        return
            boardY
                + (
                ColorSequenceBoard.ROWS
                    - 1
                    - row
            )
                * (
                buttonHeight
                    + verticalGap
            );
    }


    public float getButtonCenterX(
        int col
    ) {

        return
            getButtonX(col)
                + buttonWidth / 2f;
    }


    public float getButtonCenterY(
        int row
    ) {

        return
            getButtonY(row)
                + buttonHeight / 2f;
    }
}
