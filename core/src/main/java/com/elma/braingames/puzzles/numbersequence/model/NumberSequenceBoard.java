package com.elma.braingames.puzzles.numbersequence.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberSequenceBoard {

    public static final int ROWS = 6;

    public static final int COLS = 5;

    public static final int CELL_COUNT =
        ROWS * COLS;


    private final NumberSequenceCell[] cells;


    public NumberSequenceBoard() {

        cells =
            new NumberSequenceCell[CELL_COUNT];

        initialize();
    }


    private void initialize() {

        List<Integer> numbers =
            new ArrayList<>();

        for (
            int i = 1;
            i <= CELL_COUNT;
            i++
        ) {

            numbers.add(i);
        }

        Collections.shuffle(numbers);

        for (
            int i = 0;
            i < CELL_COUNT;
            i++
        ) {

            int row =
                i / COLS;

            int col =
                i % COLS;

            int number =
                numbers.get(i);


            cells[i] =
                new NumberSequenceCell(
                    i,
                    row,
                    col,
                    number
                );
        }
    }


    public NumberSequenceCell getCell(
        int index
    ) {

        if (
            index < 0 ||
                index >= CELL_COUNT
        ) {

            return null;
        }

        return cells[index];
    }


    public NumberSequenceCell getCell(
        int row,
        int col
    ) {

        if (
            row < 0 ||
                row >= ROWS ||
                col < 0 ||
                col >= COLS
        ) {

            return null;
        }


        return cells[
            row * COLS + col
            ];
    }


    public NumberSequenceCell[] getCells() {

        return cells;
    }

    public NumberSequenceCell findNumber(
        int number
    ) {

        for (
            NumberSequenceCell cell :
            cells
        ) {

            if (
                cell.getNumber()
                    == number
            ) {

                return cell;
            }
        }


        return null;
    }
}
