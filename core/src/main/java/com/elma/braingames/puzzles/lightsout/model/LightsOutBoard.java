package com.elma.braingames.puzzles.lightsout.model;

import java.util.Random;

public class LightsOutBoard {

    public static final int ROWS = 6;
    public static final int COLS = 6;

    private final LightsOutCell[][] cells;

    private final Random random;


    public LightsOutBoard() {

        cells =
            new LightsOutCell[
                ROWS
                ][
                COLS
                ];

        random =
            new Random();

        initialize();
    }


    private void initialize() {

        for (
            int row = 0;
            row < ROWS;
            row++
        ) {

            for (
                int col = 0;
                col < COLS;
                col++
            ) {

                cells[row][col] =
                    new LightsOutCell(
                        row,
                        col,
                        false
                    );
            }
        }

        //generateSolvableBoard();
    }


    private void generateSolvableBoard() {

        int numberOfMoves =
            8 + random.nextInt(13);

        for (
            int i = 0;
            i < numberOfMoves;
            i++
        ) {

            int row =
                random.nextInt(ROWS);

            int col =
                random.nextInt(COLS);

            toggleCross(
                row,
                col
            );
        }
    }


    public void toggleCross(
        int row,
        int col
    ) {

        toggleCell(
            row,
            col
        );

        toggleCell(
            row - 1,
            col
        );

        toggleCell(
            row + 1,
            col
        );

        toggleCell(
            row,
            col - 1
        );

        toggleCell(
            row,
            col + 1
        );
    }


    private void toggleCell(
        int row,
        int col
    ) {

        if (
            row < 0
                ||
                row >= ROWS
                ||
                col < 0
                ||
                col >= COLS
        ) {

            return;
        }

        cells[row][col].toggle();
    }


    public LightsOutCell getCell(
        int row,
        int col
    ) {

        return cells[row][col];
    }


    public boolean areAllLightsOn() {

        for (
            int row = 0;
            row < ROWS;
            row++
        ) {

            for (
                int col = 0;
                col < COLS;
                col++
            ) {

                if (
                    !cells[row][col].isOn()
                ) {

                    return false;
                }
            }
        }

        return true;
    }
}
