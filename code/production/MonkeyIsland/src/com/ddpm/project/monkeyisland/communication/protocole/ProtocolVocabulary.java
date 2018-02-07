/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.protocole;

public final class ProtocolVocabulary {

    public static final int COMMAND_STRING_SIZE = 2;
    
    /**
     * Separator of whitespace
     */
    public static final String SEPARATOR_WHITESPACE = " ";
    
    /**
     * Separator by minus
     */
    public static final String SEPARATOR_MINUS = "-";
    
    /**
     * Separator by underscore
     */
    public static final String SEPARATOR_UNDERSCORE = "_";
    
    /**
     * Separator by 3 underscore
     */
    public static final String SEPARATOR_3_UNDERSCORE = "___";
    
    /**
     * Ground value
     */
    public static final String GROUND_VALUE = "1";
    
    /**
     * See value
     */
    public static final String SEA_VALUE = "0";
    
    /**
     * Error message when a process error
     */
    public static final String ERROR_PROCESS = "ERROR_PROCESS";
}
