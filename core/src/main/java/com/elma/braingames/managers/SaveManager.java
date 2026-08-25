package com.elma.braingames.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SaveManager {

    private static final Preferences preferences =
        Gdx.app.getPreferences("BrainGamesSave");

    private SaveManager() {
    }
    public static void saveStars(int levelId, int stars) {

        preferences.putInteger(
            "stars_" + levelId,
            stars
        );

        preferences.flush();

    }
    public static int getStars(int levelId) {

        return preferences.getInteger(
            "stars_" + levelId,
            0
        );

    }
    public static void saveUnlocked(int levelId, boolean unlocked){

        preferences.putBoolean(
            "unlocked_" + levelId,
            unlocked
        );

        preferences.flush();

    }
    public static boolean isUnlocked(int levelId){

        if(levelId == 1){
            return true;
        }

        return preferences.getBoolean(
            "unlocked_" + levelId,
            false
        );

    }
    public static void saveLastLevel(int levelId){

        preferences.putInteger(
            "last_level",
            levelId
        );

        preferences.flush();

    }
    public static int getLastLevel(){

        return preferences.getInteger(
            "last_level",
            1
        );

    }
    public static void unlockLevel(int levelId){

        saveUnlocked(levelId, true);

    }

    public static void saveLevelResult(int levelId, int stars){

        if(stars > getStars(levelId)){

            saveStars(levelId, stars);

        }

        unlockLevel(levelId + 1);

        saveLastLevel(levelId);

    }



}
