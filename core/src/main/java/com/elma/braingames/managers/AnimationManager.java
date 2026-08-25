package com.elma.braingames.managers;

import com.badlogic.gdx.Gdx;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

public class AnimationManager {

    private final LevelMap levelMap;

    public AnimationManager(LevelMap levelMap) {
        this.levelMap = levelMap;
    }

    public void update() {

        float delta = Gdx.graphics.getDeltaTime();

        for (LevelNode node : levelMap.getLevels()) {

            float target = node.isSelected() ? 1.25f : 1f;

            float scale = node.getAnimationScale();

            scale += (target - scale) * 10f * delta;

            node.setAnimationScale(scale);
        }
    }
}
