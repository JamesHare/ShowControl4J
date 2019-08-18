package com.showcontrol4j.message;

/**
 * Serves as a helper class to create standard {@link SCFJMessage} objects.
 *
 * @author James Hare
 */
public class ShowCommand {

    /**
     * Creates a {@link SCFJMessage} object with the GO show command.
     * @return a {@link SCFJMessage} object with the GO show command.
     */
    public String GO() {
        return new SCFJMessage.Builder()
                .withCommand("GO")
                .withStartTime(System.currentTimeMillis() + 10000L)
                .build()
                .toString();
    }

    /**
     * Creates a {@link SCFJMessage} object with the STOP show command.
     * @return a {@link SCFJMessage} object with the STOP show command.
     */
    public String STOP() {
        return new SCFJMessage.Builder()
                .withCommand("STOP")
                .withStartTime(System.currentTimeMillis() + 10000L)
                .build()
                .toString();
    }

    /**
     * Creates a {@link SCFJMessage} object with the IDLE show command.
     * @return a {@link SCFJMessage} object with the IDLE show command.
     */
    public String IDLE() {
        return new SCFJMessage.Builder()
                .withCommand("IDLE")
                .withStartTime(System.currentTimeMillis() + 10000L)
                .build()
                .toString();
    }

}
