package com.elma.braingames.puzzles.hanoi.manager;

import com.elma.braingames.puzzles.hanoi.model.HanoiDisk;
import com.elma.braingames.puzzles.hanoi.model.HanoiRod;

public class HanoiGameManager {

    public static final int DISK_COUNT = 5;
    public static final int ROD_COUNT = 3;

    private final HanoiRod[] rods;

    private HanoiDisk selectedDisk;

    private int selectedRod;

    private int moves;

    private boolean completed;


    public HanoiGameManager() {

        rods = new HanoiRod[ROD_COUNT];

        for (int i = 0; i < ROD_COUNT; i++) {

            rods[i] = new HanoiRod();
        }

        selectedDisk = null;

        selectedRod = -1;

        moves = 0;

        completed = false;

        initialize();
    }


    private void initialize() {


        for (
            int size = DISK_COUNT;
            size >= 1;
            size--
        ) {

            rods[0].addDisk(
                new HanoiDisk(size)
            );
        }
    }


    public boolean selectRod(int rodIndex) {

        if (completed) {
            return false;
        }

        if (
            rodIndex < 0 ||
                rodIndex >= ROD_COUNT
        ) {

            return false;
        }

        if (selectedDisk == null) {

            if (rods[rodIndex].isEmpty()) {

                return false;
            }

            selectedDisk =
                rods[rodIndex].removeTop();

            selectedRod = rodIndex;

            moves++;

            return true;
        }

        return placeSelectedDisk(rodIndex);
    }


    private boolean placeSelectedDisk(
        int targetRod
    ) {

        HanoiDisk targetTop =
            rods[targetRod].getTop();


        if (targetTop == null) {

            rods[targetRod].addDisk(
                selectedDisk
            );

            clearSelection();

            checkCompleted();

            return true;
        }

        if (
            selectedDisk.getSize()
                < targetTop.getSize()
        ) {

            rods[targetRod].addDisk(
                selectedDisk
            );

            clearSelection();

            checkCompleted();

            return true;
        }



        return false;
    }


    private void clearSelection() {

        selectedDisk = null;

        selectedRod = -1;
    }


    private void checkCompleted() {

        if (
            rods[2].size()
                != DISK_COUNT
        ) {

            return;
        }



        for (
            int i = 0;
            i < DISK_COUNT;
            i++
        ) {

            int expectedSize =
                DISK_COUNT - i;

            if (
                rods[2]
                    .getDisk(i)
                    .getSize()
                    != expectedSize
            ) {

                return;
            }
        }

        completed = true;
    }


    public HanoiRod getRod(int index) {

        return rods[index];
    }


    public HanoiDisk getSelectedDisk() {

        return selectedDisk;
    }


    public int getSelectedRod() {

        return selectedRod;
    }


    public int getMoves() {

        return moves;
    }


    public boolean isCompleted() {

        return completed;
    }
}
