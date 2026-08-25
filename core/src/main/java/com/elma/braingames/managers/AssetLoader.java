package com.elma.braingames.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

    private static final AssetManager assetManager =
        new AssetManager();

    private AssetLoader(){}

    public static void load(){

        /*assetManager.load(
            "ui/level_open.png",
            Texture.class
        );

        assetManager.load(
            "ui/level_locked.png",
            Texture.class
        );

        assetManager.load(
            "ui/star.png",
            Texture.class
        );

        assetManager.load(
            "ui/star_empty.png",
            Texture.class
        );

        assetManager.finishLoading();*/

    }

    public static AssetManager getManager(){

        return assetManager;

    }
    public static Texture getLevelOpen() {
        return assetManager.get("ui/level_open.png", Texture.class);
    }

    public static Texture getLevelLocked() {
        return assetManager.get("ui/level_locked.png", Texture.class);
    }

    public static Texture getLevelSelected() {
        return assetManager.get("ui/level_selected.png", Texture.class);
    }

    public static void dispose(){

        assetManager.dispose();

    }

}
