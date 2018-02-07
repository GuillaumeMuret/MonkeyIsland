/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy;

import com.ddpm.project.monkeyisland.communication.PostmanServer;
import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.AbstractEncoder;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.ErraticMonkeyPosition;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.HunterMonkeyPosition;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.IdNewPirateSubscribe;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.MoveAccepted;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.MoveRefused;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.NewGame;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.NewPirateOnMap;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.OtherPiratePosition;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.PirateMoved;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.PirateRemoved;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.RumPosition;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.RumVisibility;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.SetMap;
import com.ddpm.project.monkeyisland.communication.proxy.encoder.TreasureFound;
import com.ddpm.project.monkeyisland.controller.Brain;

import java.util.HashMap;

public final class ProxyClient extends ProxyModel {
    
    /**
     * Map of all the encoders KEY = the process to call, VALUE = the class that encode the data
     */
    private HashMap<String,AbstractEncoder> encoders;
    
    /**
     * Singleton management
     */
    private static ProxyClient instance;
    
    /**
     * Main constructor of the client proxy
     * @param postman : the postman which manage sockets
     * @param brain : the brain which manage the managers
     */
    private ProxyClient(PostmanServer postman, Brain brain) {
        super(postman);
        this.encoders = new HashMap<>();

        this.encoders.put(ProcessOut.PROXY_CLIENT_SET_MAP.process,
            new SetMap(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_MOVE_REFUSED.process,
            new MoveRefused(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_MOVE_ACCEPTED.process,
            new MoveAccepted(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_MOVE_PIRATE.process,
            new PirateMoved(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_ID_NEW_PIRATE_SUBSCRIBED.process,
            new IdNewPirateSubscribe(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_TREASURE_POSITION.process,
            new TreasureFound(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_BOTTLE_POSITION.process,
            new RumPosition(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_OTHER_PIRATES_POSITIONS.process,
            new OtherPiratePosition(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_MONKEY_ERRATIC_POSITION.process,
            new ErraticMonkeyPosition(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_MONKEY_HUNTER_POSITION.process,
            new HunterMonkeyPosition(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_NEW_PIRATE.process,
            new NewPirateOnMap(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_SUPPRESS_PIRATE.process,
            new PirateRemoved(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_BOTTLE_VISIBILITY.process,
            new RumVisibility(this, brain));
        this.encoders.put(ProcessOut.PROXY_CLIENT_NEW_GAME.process,
            new NewGame(this, brain));
    }
    
    /**
     * Getter of the instance proxy client
     * @param postmanServer : the postman of the communication
     * @param brain : the brain of the system
     * @return the instance proxy client
     */
    public static ProxyClient getInstance (PostmanServer postmanServer, Brain brain) {
        if (instance == null) {
            instance = new ProxyClient(postmanServer, brain);
        }
        return instance;
    }
    
    /**
     * Process called to get the encoders of the proxy
     * @return : the encoders
     */
    public HashMap<String, AbstractEncoder> getEncoders() {
        return encoders;
    }
    
}
