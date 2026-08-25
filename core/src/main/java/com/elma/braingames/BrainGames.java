package com.elma.braingames;

import com.elma.braingames.managers.FontManager;
import com.badlogic.gdx.Game;
import com.elma.braingames.managers.AssetLoader;
import com.elma.braingames.managers.TextureManager;
import com.elma.braingames.screens.LevelSelectScreen;

public class BrainGames extends Game {

    @Override
    public void create() {

        AssetLoader.load();
        FontManager.load();
        TextureManager.load();
        setScreen(new LevelSelectScreen(this));

    }

    @Override
    public void dispose() {

        AssetLoader.dispose();
        FontManager.dispose();
        TextureManager.dispose();
        super.dispose();


    }
}
