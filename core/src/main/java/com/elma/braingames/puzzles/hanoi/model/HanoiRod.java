package com.elma.braingames.puzzles.hanoi.model;

import java.util.ArrayList;
import java.util.List;

public class HanoiRod {

    private final List<HanoiDisk> disks;

    public HanoiRod() {

        disks = new ArrayList<>();
    }

    public void addDisk(HanoiDisk disk) {

        disks.add(disk);
    }

    public HanoiDisk removeTop() {

        if (disks.isEmpty()) {
            return null;
        }

        return disks.remove(disks.size() - 1);
    }

    public HanoiDisk getTop() {

        if (disks.isEmpty()) {
            return null;
        }

        return disks.get(disks.size() - 1);
    }

    public int size() {

        return disks.size();
    }

    public boolean isEmpty() {

        return disks.isEmpty();
    }

    public HanoiDisk getDisk(int index) {

        return disks.get(index);
    }

    public List<HanoiDisk> getDisks() {

        return disks;
    }
}
