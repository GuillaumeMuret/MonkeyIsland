/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.protocole;

public final class ProcessIn {

    /**
     * Process of the pirate subscription
     */
    public static final String  SUBSCRIBE_PIRATE =          "/I";
    
    /**
     * Process of the pirate movement
     */
    public static final String  MOVE_PIRATE =               "/D"; // directionX directionY
}
