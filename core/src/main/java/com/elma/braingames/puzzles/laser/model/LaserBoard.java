package com.elma.braingames.puzzles.laser.model;

import java.util.ArrayList;
import java.util.List;

public class LaserBoard {

    public static final int ROWS = 8;
    public static final int COLS = 8;

    private final List<LaserMirror> mirrors;

    private final LaserSource source;

    private final LaserTarget target;


    public LaserBoard() {

        mirrors = new ArrayList<>();

        source =
            new LaserSource(
                3,
                0,
                LaserSource.Direction.RIGHT
            );

        target =
            new LaserTarget(
                4,
                7
            );

        initializeMirrors();
    }


    private void initializeMirrors() {

        mirrors.add(
            new LaserMirror(
                3,
                2,
                LaserMirror.Orientation.BACKSLASH
            )
        );

        mirrors.add(
            new LaserMirror(
                2,
                2,
                LaserMirror.Orientation.SLASH
            )
        );

        mirrors.add(
            new LaserMirror(
                2,
                1,
                LaserMirror.Orientation.BACKSLASH
            )
        );

        mirrors.add(
            new LaserMirror(
                3,
                1,
                LaserMirror.Orientation.SLASH
            )
        );

        mirrors.add(
            new LaserMirror(
                3,
                5,
                LaserMirror.Orientation.VERTICAL
            )
        );


        mirrors.add(
            new LaserMirror(
                4,
                6,
                LaserMirror.Orientation.HORIZONTAL
            )
        );


        mirrors.add(
            new LaserMirror(
                0,
                3,
                LaserMirror.Orientation.BACKSLASH
            )
        );


        mirrors.add(
            new LaserMirror(
                2,
                6,
                LaserMirror.Orientation.SLASH
            )
        );


        mirrors.add(
            new LaserMirror(
                7,
                5,
                LaserMirror.Orientation.VERTICAL
            )
        );


        mirrors.add(
            new LaserMirror(
                6,
                5,
                LaserMirror.Orientation.BACKSLASH
            )
        );


        mirrors.add(
            new LaserMirror(
                7,
                1,
                LaserMirror.Orientation.HORIZONTAL
            )
        );


        mirrors.add(
            new LaserMirror(
                6,
                3,
                LaserMirror.Orientation.SLASH
            )
        );
    }


    public List<LaserMirror> getMirrors() {
        return mirrors;
    }


    public LaserSource getSource() {
        return source;
    }


    public LaserTarget getTarget() {
        return target;
    }
}
