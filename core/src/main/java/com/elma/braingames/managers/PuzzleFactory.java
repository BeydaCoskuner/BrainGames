package com.elma.braingames.managers;

import com.badlogic.gdx.Screen;
import com.elma.braingames.BrainGames;
import com.elma.braingames.enums.PuzzleType;
import com.elma.braingames.models.PuzzleData;
import com.elma.braingames.puzzles.colorsequence.ColorSequencePuzzleScreen;
import com.elma.braingames.puzzles.colormatch.ColorMatchPuzzleScreen;
import com.elma.braingames.puzzles.hanoi.HanoiPuzzleScreen;
import com.elma.braingames.puzzles.keyrescue.KeyRescuePuzzleScreen;
import com.elma.braingames.puzzles.laser.LaserPuzzleScreen;
import com.elma.braingames.puzzles.lightsout.LightsOutPuzzleScreen;
import com.elma.braingames.puzzles.maze.MazePuzzleScreen;
import com.elma.braingames.puzzles.memory.MemoryPuzzleScreen;
import com.elma.braingames.puzzles.numbersequence.NumberSequencePuzzleScreen;
import com.elma.braingames.puzzles.shake.ShakePuzzleScreen;
import com.elma.braingames.puzzles.sliding.SlidingPuzzleScreen;
import com.elma.braingames.puzzles.tangram.TangramPuzzleScreen;

public class PuzzleFactory {

    private PuzzleFactory() {

    }

    public static Screen createPuzzle(
        BrainGames game,
        int levelId
    ) {

        PuzzleData puzzle =
            PuzzleDatabase.getPuzzle(levelId);

        if (puzzle == null) {

            return null;

        }

        switch (puzzle.getPuzzleType()) {

            case MEMORY:
                return new MemoryPuzzleScreen(game);
            case SLIDING:
                return new SlidingPuzzleScreen(game);
            case SHAKE:
                return new ShakePuzzleScreen(game);
            case MAZE:
                return new MazePuzzleScreen(game);
            case HANOI:
                return new HanoiPuzzleScreen(game);
            case KEYRESCUE:
                return new KeyRescuePuzzleScreen(game);
            case LASER:
                return new LaserPuzzleScreen(game);
            case LIGHTSOUT:
                return new LightsOutPuzzleScreen(game);
            case COLORSEQUENCE:
                return new ColorSequencePuzzleScreen(game);
            case NUMBERSEQUENCE:
                return new NumberSequencePuzzleScreen(game);
            case TANGRAM:
                return new TangramPuzzleScreen(game);
            case COLORMATCH:
                return new ColorMatchPuzzleScreen(game);


            default:
                return null;

        }

    }

}
