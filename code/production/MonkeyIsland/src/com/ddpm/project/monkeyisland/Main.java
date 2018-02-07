/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland;

import com.ddpm.project.monkeyisland.controller.Brain;

public class Main {
    
    /**
     * First process called to launch the server
     * @param args : the args that user can add
     */
    public static void main(String[] args) {
        Brain.getInstance().wakeUpBrain();
    }
}