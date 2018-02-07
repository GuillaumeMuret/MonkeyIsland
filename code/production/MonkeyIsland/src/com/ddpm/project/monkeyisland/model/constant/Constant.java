/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.constant;

public final class Constant {

    public static final int ENERGY_TO_REMOVE_WHEN_MOVE = 1;
    public static final int DIRECTION_LIST_SIZE = 4;

    public static final int TIME_REINIT_GAME = 2000;
    
    // Config file constant
    public static final String CONFIG_FILE_NAME         = "MonkeyIslandConfFile.json";
    public static final String CONFIG_DIRECTORY_NAME    = "MonkeyIslandConfRepo";
    
    ///////////////////////////
    // CHARACTER EVENT NAMES //
    ///////////////////////////
    public static final String EVENT_NONE_VALUE                = "EVENT_NONE_VALUE";
    public static final String EVENT_DIE_BY_ENERGY_VALUE       = "EVENT_DIE_BY_ENERGY_VALUE";
    public static final String EVENT_DIE_BY_MONKEY_VALUE       = "EVENT_DIE_BY_MONKEY_VALUE";
    public static final String EVENT_DRINK_VALUE               = "EVENT_DRINK_VALUE";
    public static final String EVENT_TREASURE_VALUE            = "EVENT_TREASURE_VALUE";
    public static final String EVENT_KILL_VALUE                = "EVENT_KILL_VALUE";
    
    ////////////////////////
    // USE TO CUSTOM GAME //
    ////////////////////////
    public static final int RUM_NUMBER_INIT = 10;
    public static final int RUM_ENERGY_INIT = 10;
    public static final int MONKEY_ERRATIC_NUMBER_INIT = 5;
    public static final int MONKEY_HUNTER_NUMBER_INIT = 5;
    
    
}
