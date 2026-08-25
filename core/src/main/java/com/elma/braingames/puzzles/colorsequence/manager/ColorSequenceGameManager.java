package com.elma.braingames.puzzles.colorsequence.manager;

import com.elma.braingames.puzzles.colorsequence.model.ColorSequenceBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorSequenceGameManager {

    public enum GameState {

        COMPUTER_TURN,

        PLAYER_TURN,

        ROUND_TRANSITION,

        COMPLETED,

        FAILED
    }

    private static final int MAX_ROUNDS = 10;

    private static final float FLASH_DURATION = 0.55f;

    private static final float PAUSE_DURATION = 0.20f;

    private static final float PLAYER_FEEDBACK_DURATION = 0.15f;

    private static final float ROUND_TRANSITION_DURATION = 0.45f;


    private final ColorSequenceBoard board;

    private final List<Integer> sequence;

    private final Random random;


    private GameState state;

    private int currentRound;

    private int computerIndex;

    private int playerIndex;

    private int activeButton;

    private float timer;

    private boolean flashing;

    private boolean waiting;


    private boolean playerFeedback;

    private int playerFeedbackButton;

    private float playerFeedbackTimer;


    public ColorSequenceGameManager() {

        board = new ColorSequenceBoard();

        sequence = new ArrayList<>();

        random = new Random();


        state = GameState.COMPUTER_TURN;

        currentRound = 1;

        computerIndex = 0;

        playerIndex = 0;

        activeButton = -1;

        timer = 0f;

        flashing = false;

        waiting = false;


        playerFeedback = false;

        playerFeedbackButton = -1;

        playerFeedbackTimer = 0f;


        addRandomButton();

        startComputerTurn();
    }

    public void update(float delta) {

        updatePlayerFeedback(delta);

        if (state == GameState.COMPUTER_TURN) {

            updateComputerTurn(delta);

            return;
        }

        if (state == GameState.ROUND_TRANSITION) {

            updateRoundTransition(delta);
        }
    }

    private void updateComputerTurn(float delta) {

        if (computerIndex >= sequence.size()) {

            finishComputerTurn();

            return;
        }

        if (!flashing && !waiting) {

            activeButton =
                sequence.get(computerIndex);

            board.setActive(
                activeButton,
                true
            );

            flashing = true;

            timer = 0f;

            return;
        }

        if (flashing) {

            timer += delta;

            if (timer >= FLASH_DURATION) {

                board.setActive(
                    activeButton,
                    false
                );

                activeButton = -1;

                flashing = false;

                waiting = true;

                timer = 0f;
            }

            return;
        }

        if (waiting) {

            timer += delta;

            if (timer >= PAUSE_DURATION) {

                waiting = false;

                timer = 0f;

                computerIndex++;
            }
        }
    }


    private void finishComputerTurn() {

        board.resetActiveButtons();

        activeButton = -1;

        flashing = false;

        waiting = false;

        computerIndex = 0;

        playerIndex = 0;

        state = GameState.PLAYER_TURN;
    }


    private void startComputerTurn() {

        state = GameState.COMPUTER_TURN;

        computerIndex = 0;

        playerIndex = 0;

        activeButton = -1;

        timer = 0f;

        flashing = false;

        waiting = false;

        board.resetActiveButtons();
    }

    public boolean pressButton(int buttonIndex) {

        if (state != GameState.PLAYER_TURN) {

            return false;
        }


        if (
            buttonIndex < 0 ||
                buttonIndex >= ColorSequenceBoard.BUTTON_COUNT
        ) {

            return false;
        }


        if (playerIndex >= sequence.size()) {

            return false;
        }


        int expectedButton =
            sequence.get(playerIndex);

        if (buttonIndex == expectedButton) {

            showPlayerFeedback(buttonIndex);

            playerIndex++;

            if (playerIndex >= sequence.size()) {

                if (currentRound >= MAX_ROUNDS) {

                    completeGame();

                }

                else {

                    currentRound++;

                    addRandomButton();

                    startRoundTransition();
                }
            }

            return true;
        }

        failGame();

        return true;
    }

    private void startRoundTransition() {

        state = GameState.ROUND_TRANSITION;

        timer = 0f;

        computerIndex = 0;

        playerIndex = 0;

        activeButton = -1;

        flashing = false;

        waiting = false;

    }


    private void updateRoundTransition(float delta) {

        timer += delta;

        if (
            playerFeedback &&
                playerFeedbackTimer >=
                    PLAYER_FEEDBACK_DURATION
        ) {

            board.setActive(
                playerFeedbackButton,
                false
            );

            playerFeedback = false;

            playerFeedbackButton = -1;

            playerFeedbackTimer = 0f;
        }

        if (
            timer >=
                ROUND_TRANSITION_DURATION
        ) {

            board.resetActiveButtons();

            startComputerTurn();
        }
    }

    private void showPlayerFeedback(int buttonIndex) {

        board.resetActiveButtons();

        board.setActive(
            buttonIndex,
            true
        );

        playerFeedback = true;

        playerFeedbackButton =
            buttonIndex;

        playerFeedbackTimer = 0f;
    }


    private void updatePlayerFeedback(float delta) {

        if (!playerFeedback) {

            return;
        }


        playerFeedbackTimer += delta;

        if (
            state == GameState.ROUND_TRANSITION
        ) {

            return;
        }


        if (
            playerFeedbackTimer >=
                PLAYER_FEEDBACK_DURATION
        ) {

            board.setActive(
                playerFeedbackButton,
                false
            );

            playerFeedback = false;

            playerFeedbackButton = -1;

            playerFeedbackTimer = 0f;
        }
    }

    private void addRandomButton() {

        int button =
            random.nextInt(
                ColorSequenceBoard.BUTTON_COUNT
            );

        sequence.add(button);
    }

    private void completeGame() {

        board.resetActiveButtons();

        activeButton = -1;

        playerFeedback = false;

        playerFeedbackButton = -1;

        state = GameState.COMPLETED;
    }


    private void failGame() {

        board.resetActiveButtons();

        activeButton = -1;

        playerFeedback = false;

        playerFeedbackButton = -1;

        state = GameState.FAILED;
    }

    public ColorSequenceBoard getBoard() {

        return board;
    }


    public GameState getState() {

        return state;
    }


    public int getCurrentRound() {

        return currentRound;
    }

    public int getMoves() {

        return currentRound;
    }


    public int getActiveButton() {

        return activeButton;
    }


    public List<Integer> getSequence() {

        return sequence;
    }


    public boolean isComputerTurn() {

        return
            state ==
                GameState.COMPUTER_TURN;
    }


    public boolean isPlayerTurn() {

        return
            state ==
                GameState.PLAYER_TURN;
    }


    public boolean isCompleted() {

        return
            state ==
                GameState.COMPLETED;
    }


    public boolean isFailed() {

        return
            state ==
                GameState.FAILED;
    }


    public boolean isGameOver() {

        return
            state == GameState.COMPLETED ||
                state == GameState.FAILED;
    }

    public int calculateStars() {

        int moves = getMoves();


        if (moves <= 3) {

            return 0;
        }


        if (moves <= 6) {

            return 1;
        }


        if (moves <= 8) {

            return 2;
        }


        return 3;
    }
}
