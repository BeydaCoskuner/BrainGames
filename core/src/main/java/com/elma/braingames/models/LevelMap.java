package com.elma.braingames.models;

import java.util.ArrayList;
import java.util.List;

public class LevelMap {

    private final List<LevelNode> levels;
    private final List<Path> paths;

    public LevelMap() {

        levels = new ArrayList<>();
        paths = new ArrayList<>();
        /*levels.add(new LevelNode(1,200,250,true,3));
        levels.add(new LevelNode(2,500,450,true,2));
        levels.add(new LevelNode(3,800,250,false,0));
        levels.add(new LevelNode(4,1100,450,false,0));*/

    }

    public void add(LevelNode levelNode) {

        levels.add(levelNode);

    }

    public List<LevelNode> getLevels() {

        return levels;

    }

    public void addPath(Path path){
        paths.add(path);
    }
    public List<Path> getPaths(){
        return paths;
    }

    public float getMapWidth() {

        if (levels.isEmpty()) {
            return 0;
        }

        return levels.get(levels.size() - 1).getX();

    }

}
