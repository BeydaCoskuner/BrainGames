package com.elma.braingames.puzzles.memory.manager;

import com.elma.braingames.puzzles.memory.model.MemoryCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGameManager {

    private MemoryCard firstCard;
    private MemoryCard secondCard;

    private float closeTimer;

    private boolean waiting;

    private int moves;

    private final List<MemoryCard> cards;

    public MemoryGameManager() {

        moves = 0;
        cards = new ArrayList<>();

        createCards();

        shuffleCards();

    }

    private void createCards() {

        for (int i = 1; i <= 10; i++) {

            cards.add(new MemoryCard(i));

            cards.add(new MemoryCard(i));

        }

    }

    private void shuffleCards() {

        Collections.shuffle(cards);

    }

    public List<MemoryCard> getCards() {

        return cards;

    }
    public void update(float delta) {

        for (MemoryCard card : cards) {

            if (!card.isFlipping()) {
                continue;
            }

            float progress = card.getFlipProgress();

            progress += delta * 4f;

            if (progress >= 1f) {

                progress = 1f;

                card.setFlipping(false);

            }

            card.setFlipProgress(progress);

        }
        for (MemoryCard card : cards) {

            if (!card.isBouncing()) {
                continue;
            }

            float time = card.getBounceTime() + delta;

            card.setBounceTime(time);

            float scale;

            if (time < 0.10f) {

                scale = 1f + time * 2f;

            } else if (time < 0.20f) {

                scale = 1.2f - (time - 0.10f) * 2f;

            } else {

                scale = 1f;

                card.setAnimationScale(1f);

                card.setBouncing(false);

                continue;
            }

            card.setAnimationScale(scale);

        }

        if (!waiting) {
            return;
        }

        closeTimer += delta;

        if (closeTimer >= 0.6f) {

            firstCard.setFlipped(false);
            secondCard.setFlipped(false);

            firstCard.setFlipping(true);
            firstCard.setFlipProgress(0f);
            firstCard.setFlipForward(false);

            secondCard.setFlipping(true);
            secondCard.setFlipProgress(0f);
            secondCard.setFlipForward(false);

            firstCard = null;
            secondCard = null;

            waiting = false;
            closeTimer = 0f;
        }
    }
    public void flipCard(MemoryCard card) {

        if (waiting) return;

        if (card.isMatched()) return;

        if (card.isFlipped()) return;

        card.setFlipped(true);


        card.setFlipping(true);

        card.setFlipProgress(0f);

        card.setFlipForward(true);

        if (firstCard == null) {

            firstCard = card;

            return;
        }

        secondCard = card;

        checkCards();
    }
    private void checkCards() {

        moves++;
        if (firstCard.getPairId() == secondCard.getPairId()) {

            firstCard.setMatched(true);
            secondCard.setMatched(true);

            firstCard.setBouncing(true);
            secondCard.setBouncing(true);

            firstCard.setBounceTime(0f);
            secondCard.setBounceTime(0f);

            firstCard = null;
            secondCard = null;

        } else {

            waiting = true;
            closeTimer = 0f;

        }

    }
    public boolean isCompleted() {

        for (MemoryCard card : cards) {

            if (!card.isMatched()) {
                return false;
            }

        }

        return true;
    }
    public int getMoves() {
        return moves;
    }

}
