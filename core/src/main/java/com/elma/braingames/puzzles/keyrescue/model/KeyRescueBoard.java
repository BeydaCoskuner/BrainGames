package com.elma.braingames.puzzles.keyrescue.model;

import java.util.ArrayList;
import java.util.List;

public class KeyRescueBoard {

    public static final int ROWS = 6;

    public static final int COLS = 6;


    private final List<KeyRescueBlock> blocks;


    public KeyRescueBoard() {

        blocks =
            new ArrayList<>();

        initialize();
    }


    /*private void initialize() {

        //ANahtar
        blocks.add(
            new KeyRescueBlock(
                0,
                2,
                0,
                2,
                KeyRescueBlock.Orientation.HORIZONTAL,
                true
            )
        );

        //sol üst
        blocks.add(
            new KeyRescueBlock(
                1,
                0,
                0,
                2,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );

        //üst
        blocks.add(
            new KeyRescueBlock(
                2,
                0,
                3,
                3,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                3,
                1,
                5,
                3,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );
        //orta
        blocks.add(
            new KeyRescueBlock(
                4,
                1,
                1,
                2,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                5,
                1,
                3,
                3,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                6,
                3,
                0,
                3,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );

        //alt
        blocks.add(
            new KeyRescueBlock(
                7,
                4,
                2,
                2,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                8,
                5,
                0,
                2,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                9,
                3,
                4,
                3,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );
    }*/
    private void initialize() {

        // anahtar

        blocks.add(
            new KeyRescueBlock(
                0,
                2,
                0,
                2,
                KeyRescueBlock.Orientation.HORIZONTAL,
                true
            )
        );


        // üst

        blocks.add(
            new KeyRescueBlock(
                1,
                0,
                0,
                2,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                2,
                0,
                3,
                3,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                3,
                1,
                3,
                2,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );


        // sağ dikey
        blocks.add(
            new KeyRescueBlock(
                4,
                1,
                5,
                3,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );


        // orta
        blocks.add(
            new KeyRescueBlock(
                5,
                3,
                0,
                3,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                6,
                2,
                4,
                2,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                7,
                4,
                2,
                2,
                KeyRescueBlock.Orientation.VERTICAL,
                false
            )
        );

        blocks.add(
            new KeyRescueBlock(
                8,
                5,
                0,
                2,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );


        // alt
        blocks.add(
            new KeyRescueBlock(
                9,
                4,
                4,
                2,
                KeyRescueBlock.Orientation.HORIZONTAL,
                false
            )
        );
    }


    public List<KeyRescueBlock> getBlocks() {

        return blocks;
    }


    public KeyRescueBlock getBlock(
        int index
    ) {

        return blocks.get(index);
    }


    public int size() {

        return blocks.size();
    }
}
