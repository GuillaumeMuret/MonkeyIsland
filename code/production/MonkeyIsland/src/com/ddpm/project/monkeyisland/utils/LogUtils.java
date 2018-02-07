/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.utils;

public class LogUtils {

    /**
     * The error tag
     */
    private static final String ERROR_TAG = "ERROR - ";

    /**
     * The debug tag
     */
    private static final String DEBUG_TAG = "DEBUG - ";
    
    /**
     * The test tag
     */
    private static final String TEST_TAG = "TEST - ";
    
    /**
     * Process called to make a debug console message
     * @param message : the message
     */
    public static void debug(String message) {
        System.out.println(DEBUG_TAG + message);
    }
    
    /**
     * Process called to make a test console message
     * @param message : the message
     */
    public static void test(String message) {
        System.out.println(TEST_TAG + message);
    }

    /**
     * Process useless to return nothing but the beat
     */
    public static void useless() {

    }
    
    /**
     * Process called to make an error console message
     * @param message : the message
     */
    public static void error(String message) {
        System.out.println(ERROR_TAG + message);
    }
    
    /**
     * Process called to make an error console message with the throwable to display
     * @param message : the message to display
     * @param throwable : the throwable to display
     */
    public static void error(String message, Throwable throwable) {
        System.out.println(ERROR_TAG + message);
        throwable.printStackTrace();
    }
}
