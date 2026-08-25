package com.elma.braingames.puzzles.colorsequence.model;

import com.badlogic.gdx.graphics.Color;

public class ColorSequenceBoard {

    public static final int ROWS = 4;
    public static final int COLS = 3;
    public static final int BUTTON_COUNT = ROWS * COLS;

    private final ColorSequenceButton[] buttons;

    public ColorSequenceBoard() {

        buttons = new ColorSequenceButton[BUTTON_COUNT];

        initializeButtons();
    }

    private void initializeButtons() {

        Color[] colors = {

            new Color(1f, 0.20f, 0.15f, 1f),    // kırmızı
            new Color(1f, 0.55f, 0.05f, 1f),    // turuncu
            new Color(1f, 0.85f, 0.10f, 1f),    // sarı

            new Color(0.20f, 0.80f, 0.30f, 1f), // yeşil
            new Color(0.05f, 0.75f, 0.70f, 1f), // turkuaz
            new Color(0.10f, 0.55f, 1f, 1f),    // mavi

            new Color(0.25f, 0.30f, 1f, 1f),    // lacivert
            new Color(0.55f, 0.25f, 1f, 1f),    // mor
            new Color(0.85f, 0.25f, 0.75f, 1f), // pembe

            new Color(1f, 0.30f, 0.50f, 1f),    // fuşya
            new Color(0.30f, 0.90f, 0.90f, 1f), // açık turkuaz
            new Color(0.70f, 0.45f, 0.20f, 1f)  // kahverengi
        };

        for (int i = 0; i < BUTTON_COUNT; i++) {

            int row = i / COLS;
            int col = i % COLS;

            buttons[i] = new ColorSequenceButton(
                i,
                row,
                col,
                colors[i]
            );
        }
    }

    public ColorSequenceButton getButton(int index) {

        if (index < 0 || index >= BUTTON_COUNT) {
            return null;
        }

        return buttons[index];
    }

    public ColorSequenceButton getButton(
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

        return buttons[row * COLS + col];
    }

    public ColorSequenceButton[] getButtons() {

        return buttons;
    }

    public void setActive(
        int index,
        boolean active
    ) {

        ColorSequenceButton button =
            getButton(index);

        if (button != null) {

            button.setActive(active);
        }
    }

    public void resetActiveButtons() {

        for (ColorSequenceButton button : buttons) {

            button.setActive(false);
        }
    }
}
