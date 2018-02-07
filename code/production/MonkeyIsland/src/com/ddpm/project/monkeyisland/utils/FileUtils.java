/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.utils;

import com.ddpm.project.monkeyisland.model.constant.Constant;
import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    
    /**
     * Process called to save the game configuration in a json file
     */
    public static void saveGameConfiguration() {
        exportJsonFile(new File(FileUtils.getRootFile(), Constant.CONFIG_FILE_NAME));
    }
    
    /**
     * Process called to generate custom game configuration
     */
    public static void generateCustomGameConfiguration() {
        // GameConfig.getInstance().setMapWidthX(20);
        // GameConfig.getInstance().setMapHeightY(20);
        // GameConfig.getInstance().setMaxEnergyPirateValue(100);
        // GameConfig.getInstance().setMonkeyErraticSpeed(1000);
        // GameConfig.getInstance().setMonkeyHunterSpeed(750);
        // GameConfig.getInstance().setRumEnergyValue(10);
        // GameConfig.getInstance().setTimeRumVisibility(10000);
        // GameConfig.getInstance().setRumList(Brain.getInstance().getObjectManager().getRumList());
        // GameConfig.getInstance().setSquaresList(Brain.getInstance().getMapManager().getMap().getSquareList());
        // ArrayList<Monkey> monkeyArrayList = new ArrayList<Monkey>();
        // for(int i=0;i<10;i++) {
        //     if (i % 2 == 0) {
        //         monkeyArrayList.add(new Monkey(PositionUtils.getSpawnPosition(
        //             Map.getInstance().getGroundSquareList(),
        //             CharacterManager.getInstance(Brain.getInstance()).getCharacterList(),
        //             ObjectManager.getInstance(Brain.getInstance()).getObjectList()
        //         ), Monkey.MonkeyType.ERRATIC));
        //     } else {
        //         monkeyArrayList.add(new Monkey(PositionUtils.getSpawnPosition(
        //             Map.getInstance().getGroundSquareList(),
        //             CharacterManager.getInstance(Brain.getInstance()).getCharacterList(),
        //             ObjectManager.getInstance(Brain.getInstance()).getObjectList()
        //         ), Monkey.MonkeyType.HUNTER));
        //     }
        // }
        // GameConfig.getInstance().setMonkeyList(monkeyArrayList);
        // for(int i=0;i< Brain.getInstance().getMapManager().getMap().getSquareList().size();i++) {
        //     for (int j = 0; j < Brain.getInstance().getMapManager().getMap().getSquareList().get(i).size(); j++) {
        //         if (i == 0 || j==0 || i == GameConfig.getInstance().getMapWidthX()-1
        //                     || j == GameConfig.getInstance().getMapHeightY()-1) {
        //             Brain.getInstance().getMapManager().getMap().getSquareList().get(i).get(j)
        //                         .setSquareType(Square.SquareType.SEA);
        //         }else{
        //             Brain.getInstance().getMapManager().getMap().getSquareList().get(i).get(j)
        //                 .setSquareType(Square.SquareType.GROUND);
        //         }
        //     }
        // }
        // GameConfig.getInstance().setSquaresList(Brain.getInstance().getMapManager().getMap().getSquareList());
        // saveGameConfiguration();
    }
    
    /**
     * Process called to get the root file
     *
     * @return : the root file
     */
    static File getRootFile() {
        File root = new File(Constant.CONFIG_DIRECTORY_NAME);
        boolean directoryCreated = true;
        if (!root.exists()) {
            directoryCreated = root.mkdirs();
        }
        if (directoryCreated) {
            return root;
        } else {
            return null;
        }
    }
    
    /**
     * Process called to export the json file in custom repository
     * @param configFile : the config json file
     */
    private static void exportJsonFile(File configFile) {
        Gson gson = new Gson();
        
        // Java object to JSON, and assign to a String
        String jsonInString = gson.toJson(GameConfig.getInstance());
        
        try {
            FileWriter writer = new FileWriter(configFile);
            
            writer.append(jsonInString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            LogUtils.error("exportJsonFile => ",e);
        }
    }
    
    /**
     * Process called to load the current json configuration file
     */
    public static void loadGameConfiguration() {
        File configFile = new File(FileUtils.getRootFile(), Constant.CONFIG_FILE_NAME);
        manageGameConfigFile(configFile);
    }
    
    /**
     * Manage the json file of the app configuration
     *
     * @param configFile : the config file
     */
    private static void manageGameConfigFile(File configFile) {
        Gson gson = new Gson();
        try {
            GameConfig.setInstance(gson.fromJson(new FileReader(configFile), GameConfig.class));
        } catch (FileNotFoundException e) {
            LogUtils.error("FileNotFoundException => ", e);
        }
    }
}
