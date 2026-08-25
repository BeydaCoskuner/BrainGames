package com.elma.braingames.puzzles.keyrescue.manager;

import com.elma.braingames.puzzles.keyrescue.model.KeyRescueBlock;
import com.elma.braingames.puzzles.keyrescue.model.KeyRescueBoard;

public class KeyRescueGameManager {

    private final KeyRescueBoard board;


    private KeyRescueBlock selectedBlock;

    private int moves;

    private float elapsedTime;

    private boolean completed;


    public KeyRescueGameManager() {

        board =
            new KeyRescueBoard();

        selectedBlock = null;

        moves = 0;

        elapsedTime = 0f;

        completed = false;
    }


    public void update(
        float delta
    ) {

        if (completed) {
            return;
        }

        elapsedTime += delta;

        checkCompleted();
    }


    public boolean selectBlock(
        int row,
        int col
    ) {

        if (completed) {
            return false;
        }

        if (selectedBlock != null) {
            return false;
        }


        for (
            KeyRescueBlock block
            : board.getBlocks()
        ) {

            if (
                block.occupies(
                    row,
                    col
                )
            ) {

                selectedBlock = block;

                moves++;

                return true;
            }
        }


        return false;
    }


    /*public boolean moveSelectedBlock(
        int row,
        int col
    ) {

        if (
            completed
                ||
                selectedBlock == null
        ) {

            return false;
        }

        if (
            selectedBlock.isHorizontal()
        ) {

            if (
                row
                    != selectedBlock.getRow()
            ) {

                return false;
            }

        }

        if (
            selectedBlock.isVertical()
        ) {

            if (
                col
                    != selectedBlock.getCol()
            ) {

                return false;
            }
        }

        if (
            !isInsideBoard(
                selectedBlock,
                row,
                col
            )
        ) {

            return false;
        }

        if (
            collidesWithOtherBlock(
                selectedBlock,
                row,
                col
            )
        ) {

            return false;
        }


        selectedBlock.setPosition(
            row,
            col
        );


        checkCompleted();

        return true;
    }*/
    public boolean moveSelectedBlock(
        int row,
        int col
    ) {

        if (
            completed
                ||
                selectedBlock == null
        ) {
            return false;
        }


        if (
            selectedBlock.isHorizontal()
                &&
                row != selectedBlock.getRow()
        ) {
            return false;
        }

        if (
            selectedBlock.isVertical()
                &&
                col != selectedBlock.getCol()
        ) {
            return false;
        }

        if (
            !isInsideBoard(
                selectedBlock,
                row,
                col
            )
        ) {
            return false;
        }


        if (selectedBlock.isHorizontal()) {

            int currentCol =
                selectedBlock.getCol();


            if (col > currentCol) {

                for (
                    int testCol = currentCol + 1;
                    testCol <= col;
                    testCol++
                ) {

                    if (
                        collidesWithOtherBlock(
                            selectedBlock,
                            selectedBlock.getRow(),
                            testCol
                        )
                    ) {

                        return false;
                    }
                }
            }


            else if (col < currentCol) {

                for (
                    int testCol = currentCol - 1;
                    testCol >= col;
                    testCol--
                ) {

                    if (
                        collidesWithOtherBlock(
                            selectedBlock,
                            selectedBlock.getRow(),
                            testCol
                        )
                    ) {

                        return false;
                    }
                }
            }
        }


        if (selectedBlock.isVertical()) {

            int currentRow =
                selectedBlock.getRow();


            if (row > currentRow) {

                for (
                    int testRow = currentRow + 1;
                    testRow <= row;
                    testRow++
                ) {

                    if (
                        collidesWithOtherBlock(
                            selectedBlock,
                            testRow,
                            selectedBlock.getCol()
                        )
                    ) {

                        return false;
                    }
                }
            }


            else if (row < currentRow) {

                for (
                    int testRow = currentRow - 1;
                    testRow >= row;
                    testRow--
                ) {

                    if (
                        collidesWithOtherBlock(
                            selectedBlock,
                            testRow,
                            selectedBlock.getCol()
                        )
                    ) {

                        return false;
                    }
                }
            }
        }


        selectedBlock.setPosition(
            row,
            col
        );


        checkCompleted();

        return true;
    }


    public void releaseBlock() {

        selectedBlock = null;
    }


    private boolean isInsideBoard(
        KeyRescueBlock block,
        int row,
        int col
    ) {

        if (row < 0 || col < 0) {
            return false;
        }


        if (block.isHorizontal()) {

            return row < KeyRescueBoard.ROWS
                &&
                col + block.getLength()
                    <= KeyRescueBoard.COLS;
        }


        return row + block.getLength()
            <= KeyRescueBoard.ROWS
            &&
            col < KeyRescueBoard.COLS;
    }


    private boolean collidesWithOtherBlock(
        KeyRescueBlock movingBlock,
        int newRow,
        int newCol
    ) {

        for (
            KeyRescueBlock other
            : board.getBlocks()
        ) {

            if (other == movingBlock) {
                continue;
            }


            if (
                blocksOverlap(
                    movingBlock,
                    newRow,
                    newCol,
                    other
                )
            ) {

                return true;
            }
        }


        return false;
    }


    private boolean blocksOverlap(
        KeyRescueBlock movingBlock,
        int newRow,
        int newCol,
        KeyRescueBlock other
    ) {

        for (
            int r = 0;
            r < KeyRescueBoard.ROWS;
            r++
        ) {

            for (
                int c = 0;
                c < KeyRescueBoard.COLS;
                c++
            ) {

                if (
                    occupies(
                        movingBlock,
                        newRow,
                        newCol,
                        r,
                        c
                    )
                        &&
                        other.occupies(
                            r,
                            c
                        )
                ) {

                    return true;
                }
            }
        }


        return false;
    }


    private boolean occupies(
        KeyRescueBlock block,
        int row,
        int col,
        int targetRow,
        int targetCol
    ) {

        if (
            block.isHorizontal()
        ) {

            return targetRow == row
                &&
                targetCol >= col
                &&
                targetCol
                    < col + block.getLength();
        }


        return targetCol == col
            &&
            targetRow >= row
            &&
            targetRow
                < row + block.getLength();
    }


    private void checkCompleted() {

        KeyRescueBlock keyBlock = null;


        for (
            KeyRescueBlock block
            : board.getBlocks()
        ) {

            if (block.isKeyBlock()) {

                keyBlock = block;

                break;
            }
        }


        if (keyBlock == null) {
            return;
        }


        //çıkış
        if (
            keyBlock.isHorizontal()
                &&
                keyBlock.getCol()
                    + keyBlock.getLength()
                    >= KeyRescueBoard.COLS
        ) {

            completed = true;
        }
    }


    public KeyRescueBoard getBoard() {

        return board;
    }


    public KeyRescueBlock getSelectedBlock() {

        return selectedBlock;
    }


    public int getMoves() {

        return moves;
    }


    public float getElapsedTime() {

        return elapsedTime;
    }


    public boolean isCompleted() {

        return completed;
    }
}
