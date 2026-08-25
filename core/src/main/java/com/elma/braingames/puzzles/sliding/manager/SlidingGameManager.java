package com.elma.braingames.puzzles.sliding.manager;

import com.elma.braingames.puzzles.sliding.model.SlidingTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlidingGameManager {

    public static final int SIZE = 3;

    private final SlidingTile[][] board;

    private int emptyRow;
    private int emptyCol;

    private int moves;

    private boolean completed;

    public SlidingGameManager() {

        board = new SlidingTile[SIZE][SIZE];

        moves = 0;
        completed = false;

        createBoard();
        shuffleBoard();
    }

    private void createBoard() {

        int value = 1;

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                if (value == 9) {

                    board[row][col] =
                        new SlidingTile(0, row, col);

                    emptyRow = row;
                    emptyCol = col;

                } else {

                    board[row][col] =
                        new SlidingTile(
                            value,
                            row,
                            col
                        );

                }

                value++;
            }
        }
    }

    private void shuffleBoard() {

        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            values.add(i);
        }

        do {

            Collections.shuffle(values);

        } while (!isSolvable(values)
            || isSolved(values));

        int index = 0;

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                int value = values.get(index++);

                board[row][col] =
                    new SlidingTile(
                        value,
                        row,
                        col
                    );

                if (value == 0) {

                    emptyRow = row;
                    emptyCol = col;
                }
            }
        }
    }

    private boolean isSolved(List<Integer> values) {

        for (int i = 0; i < 8; i++) {

            if (values.get(i) != i + 1) {
                return false;
            }
        }

        return values.get(8) == 0;
    }

    private boolean isSolvable(List<Integer> values) {

        int inversions = 0;

        for (int i = 0; i < values.size(); i++) {

            if (values.get(i) == 0) {
                continue;
            }

            for (int j = i + 1; j < values.size(); j++) {

                if (values.get(j) == 0) {
                    continue;
                }

                if (values.get(i) > values.get(j)) {
                    inversions++;
                }
            }
        }

        return inversions % 2 == 0;
    }
    public int getTile(int row, int col) {

        return board[row][col].getValue();
    }
    public boolean move(int row, int col) {

        if (board[row][col].isEmpty()) {
            return false;
        }

        int rowDifference = Math.abs(row - emptyRow);
        int colDifference = Math.abs(col - emptyCol);

        // Sadece yatay veya dikey komşu taş hareket edebilir
        boolean adjacent =
            (rowDifference == 1 && colDifference == 0)
                ||
                (rowDifference == 0 && colDifference == 1);

        if (!adjacent) {
            return false;
        }

        SlidingTile selectedTile =
            board[row][col];

        SlidingTile emptyTile =
            board[emptyRow][emptyCol];

        board[emptyRow][emptyCol] =
            selectedTile;

        board[row][col] =
            emptyTile;

        selectedTile.setPosition(
            emptyRow,
            emptyCol
        );

        emptyTile.setPosition(
            row,
            col
        );

        emptyRow = row;
        emptyCol = col;

        moves++;

        if (checkCompleted()) {
            completed = true;
        }

        return true;
    }
    private boolean checkCompleted() {

        int expectedValue = 1;

        for (int row = 0; row < SIZE; row++) {

            for (int col = 0; col < SIZE; col++) {

                if (row == SIZE - 1 &&
                    col == SIZE - 1) {

                    return board[row][col].isEmpty();
                }

                if (
                    board[row][col].getValue()
                        != expectedValue
                ) {

                    return false;
                }

                expectedValue++;
            }
        }

        return true;
    }

    public int getMoves() {

        return moves;
    }
    public boolean isCompleted() {

        return completed;
    }
    public int getEmptyRow() {

        return emptyRow;
    }

    public int getEmptyCol() {

        return emptyCol;
    }
    public void update(float delta) {

    }
}
