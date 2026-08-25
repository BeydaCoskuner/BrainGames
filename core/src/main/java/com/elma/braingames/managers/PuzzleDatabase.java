package com.elma.braingames.managers;

import com.elma.braingames.enums.PuzzleType;
import com.elma.braingames.models.PuzzleData;

import java.util.HashMap;
import java.util.Map;

public class PuzzleDatabase {

    private static final Map<Integer, PuzzleData> puzzles =
        new HashMap<>();

    static {

        puzzles.put(
            1,
            new PuzzleData(
                1,
                PuzzleType.MEMORY,
                250,
                0,
                15,
                20,
                25
            )
        );

        puzzles.put(
            2,
            new PuzzleData(
                2,
                PuzzleType.SLIDING,
                250,
                0,
                15,
                20,
                25
            )
        );

        puzzles.put(
            3,
            new PuzzleData(
                3,
                PuzzleType.SHAKE,
                0,
                800,
                16,
                30,
                45

            )
        );
        puzzles.put(
            4,
            new PuzzleData(
                4,
                PuzzleType.MAZE,
                0,
                60000,
                45,
                90,
                120
            )
        );
        puzzles.put(
            5,
            new PuzzleData(
                5,
                PuzzleType.HANOI,
                800,
                0,
                31,
                40,
                50
            )
        );
        puzzles.put(
            6,
            new PuzzleData(
                6,
                PuzzleType.KEYRESCUE,
                0,
                2000,
                15,
                20,
                25
            )
        );
        puzzles.put(
            7,
            new PuzzleData(
                7,
                PuzzleType.LASER,
                0,
                2000,
                20,
                30,
                40
            )
        );
        puzzles.put(
            8,
            new PuzzleData(
                8,
                PuzzleType.LIGHTSOUT,
                1000,
                0,
                28,
                35,
                40
            )
        );
        puzzles.put(
            9,
            new PuzzleData(
                9,
                PuzzleType.COLORSEQUENCE,
                1000,
                0,
                10,
                7,
                3
            )
        );
        puzzles.put(
            10,
            new PuzzleData(
                10,
                PuzzleType.NUMBERSEQUENCE,
                0,
                10000,
                40,
                45,
                55
            )
        );
        puzzles.put(
            11,
            new PuzzleData(
                11,
                PuzzleType.TANGRAM,
                0,
                10000,
                40,
                45,
                55
            )
        );

        puzzles.put(
            12,
            new PuzzleData(
                12,
                PuzzleType.COLORMATCH,
                0,
                10000,
                40,
                45,
                55
            )
        );


    }

    private PuzzleDatabase() {

    }

    public static PuzzleData getPuzzle(int levelId) {

        return puzzles.get(levelId);

    }

}
