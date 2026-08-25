package com.elma.braingames.managers;

import com.elma.braingames.models.LevelMap;
import com.elma.braingames.models.LevelNode;

public class LevelManager {

    private final LevelMap levelMap;

    private LevelNode selectedNode;

    public LevelManager(LevelMap levelMap){

        this.levelMap = levelMap;

    }
    public boolean isSelected(LevelNode node) {

        return selectedNode == node;

    }

    public LevelNode getSelectedNode(){

        return selectedNode;

    }

    public void select(LevelNode node){

        if(selectedNode != null){

            selectedNode.setSelected(false);

        }

        selectedNode = node;

        if(selectedNode != null){

            selectedNode.setSelected(true);

        }

    }
    public LevelNode getNodeById(int id){

        for(LevelNode node : levelMap.getLevels()){

            if(node.getId() == id){

                return node;

            }

        }

        return null;

    }
    public void clearSelection(){

        if(selectedNode != null){

            selectedNode.setSelected(false);

            selectedNode = null;

        }

    }
    public boolean hasSelection() {

        return selectedNode != null;

    }
    public int getSelectedLevelId() {

        if (selectedNode == null) {
            return -1;
        }

        return selectedNode.getId();

    }

}
