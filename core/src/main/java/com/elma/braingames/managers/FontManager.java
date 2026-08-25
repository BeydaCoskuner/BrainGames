package com.elma.braingames.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontManager {

    private static BitmapFont defaultFont;

    public static void load(){

        defaultFont = new BitmapFont();

    }

    public static BitmapFont getDefaultFont(){

        return defaultFont;

    }

    public static void dispose(){

        defaultFont.dispose();

    }

}
