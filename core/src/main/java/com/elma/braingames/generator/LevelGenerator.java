package com.elma.braingames.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.elma.braingames.managers.SaveManager;
import com.elma.braingames.models.Path;
import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

import java.awt.font.NumericShaper;

public class LevelGenerator {


    public static void generate(LevelMap levelMap, int levelCount) {


        float startX = 200;
        float[] lanes = {
            1400,
            1700,
            2000,
            2300,
            2600
        };

        float distance = 550;


        LevelNode previousNode = null;

        for (int i = 1; i <= levelCount; i++) {

            float x = startX + (i - 1) * distance;

            int[] pattern = {
                2, 4, 1, 3, 0,
                2, 1, 4, 2, 3,
                1, 0, 3, 2, 4
            };

            int laneIndex = pattern[(i - 1) % pattern.length];

            float y = lanes[laneIndex];
            boolean unlocked = SaveManager.isUnlocked(i);


            int stars = SaveManager.getStars(i);
            LevelNode currentNode = new LevelNode(
                i,
                x,
                y,
                unlocked,
                stars
            );


            /*LevelNode currentNode = new LevelNode(
                i,
                x,
                y,
                unlocked,
                0
            );*/

            levelMap.add(currentNode);

            if(previousNode != null){

                float controlX =
                    (previousNode.getX() + currentNode.getX()) / 2f;

                float controlY =
                    (previousNode.getY() + currentNode.getY()) / 2f;

                if(previousNode.getY() < currentNode.getY()){

                    controlY += 120;

                }else{

                    controlY -= 120;

                }
                levelMap.addPath(

                    new Path(
                        previousNode,
                        currentNode,
                        controlX,
                        controlY
                    )

                );

            }

            previousNode = currentNode;
        }
    }
}
