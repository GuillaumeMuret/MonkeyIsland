/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.protocole;

public enum ProcessOut {

    PROXY_CLIENT_ID_NEW_PIRATE_SUBSCRIBED   (Proxy.PROXY_CLIENT,"/i" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_MOVE_REFUSED               (Proxy.PROXY_CLIENT,"/R" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_MOVE_ACCEPTED              (Proxy.PROXY_CLIENT,"/A" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_OTHER_PIRATES_POSITIONS    (Proxy.PROXY_CLIENT,"/P" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_NEW_PIRATE                 (Proxy.PROXY_CLIENT,"/n" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_SUPPRESS_PIRATE            (Proxy.PROXY_CLIENT,"/s" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_MOVE_PIRATE                (Proxy.PROXY_CLIENT,"/p" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_SET_MAP                    (Proxy.PROXY_CLIENT,"/C" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_MONKEY_ERRATIC_POSITION    (Proxy.PROXY_CLIENT,"/e" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_MONKEY_HUNTER_POSITION     (Proxy.PROXY_CLIENT,"/c" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_BOTTLE_POSITION            (Proxy.PROXY_CLIENT,"/B" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_BOTTLE_VISIBILITY          (Proxy.PROXY_CLIENT,"/b" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_TREASURE_POSITION          (Proxy.PROXY_CLIENT,"/T" + ProtocolVocabulary.SEPARATOR_WHITESPACE),
    PROXY_CLIENT_NEW_GAME                   (Proxy.PROXY_CLIENT,"/N" + ProtocolVocabulary.SEPARATOR_WHITESPACE),

    // Process added
    PROXY_CLIENT_NO_POSITION_AVAILABLE      (Proxy.PROXY_CLIENT,"NO_POSITION_AVAILABLE"), // Error no position available
    PROXY_CLIENT_TEST_BROADCAST             (Proxy.PROXY_CLIENT,"TEST_BROADCAST"); // Test broadcast

    /**
     * The owner
     */
    public final String owner;

    /**
     * The process called
     */
    public final String process;

    /**
     * Constructor of the enum with owner, num owner and the process
     * @param owner : the owner
     * @param process : the process called
     */
    ProcessOut(final String owner,final String process) {
        this.owner = owner;
        this.process = process;
    }
}


