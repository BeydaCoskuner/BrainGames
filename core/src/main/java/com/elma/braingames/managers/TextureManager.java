package com.elma.braingames.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureManager {

    private static Texture cardBack;
    private static TextureRegion cardBackRegion;

    public static void load() {

        cardBack = new Texture("cards/backs.png");

        cardBackRegion = new TextureRegion(
            cardBack, 152, 201, 719,  1042
        );

        Gdx.app.log(
            "TEXTURE",
            "Back Width = " + cardBack.getWidth()
                + " Height = " + cardBack.getHeight()
        );
    }

    public static Texture getCardBack() {
        return cardBack;
    }

    public static TextureRegion getCardBackRegion() {
        return cardBackRegion;
    }

    public static void dispose() {

        if (cardBack != null) {
            cardBack.dispose();
        }
    }
}
