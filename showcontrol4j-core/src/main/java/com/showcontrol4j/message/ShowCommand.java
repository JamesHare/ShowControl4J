package com.showcontrol4j.message;

/**
 * Serves as a helper class to create standard {@link SCFJMessage} objects.
 *
 * @author James Hare
 */
public class ShowCommand {

    // private constructor so that the class cannot be instantiated.
    private ShowCommand() {}

    /**
     * Creates a {@link SCFJMessage} object with the GO show command. Accepts a parameter to specify a time
     * in milliseconds to wait before the show loop is started. This allows for better show syncing if a
     * co-element experiences network lag when accepting messages. All show elements will wait for the time
     * specified before starting allowing the lag to catch up.
     *
     * @param syncTimeout milliseconds to wait before starting show loop.
     * @return a {@link SCFJMessage} String with the GO show command.
     */
    public static String GO(Long syncTimeout) {
        return new SCFJMessage.Builder()
                .withCommand("GO")
                .withStartTime(System.currentTimeMillis() + (syncTimeout != null ? syncTimeout : 0L))
                .build()
                .toString();
    }

    /**
     * Creates a {@link SCFJMessage} object with the GO show command.
     *
     * @return a {@link SCFJMessage} object with the GO show command.
     */
    public static String GO() {
        return new SCFJMessage.Builder()
                .withCommand("GO")
                .withStartTime(System.currentTimeMillis())
                .build()
                .toString();
    }

    /**
     * Creates a {@link SCFJMessage} object with the IDLE show command. Accepts a parameter to specify a time
     * in milliseconds to wait before the show loop is started. This allows for better show syncing if a
     * co-element experiences network lag when accepting messages. All show elements will wait for the time
     * specified before starting allowing the lag to catch up.
     *
     * @param syncTimeout milliseconds to wait before starting show loop.
     * @return a {@link SCFJMessage} String with the IDLE show command.
     */
    public static String IDLE(Long syncTimeout) {
        return new SCFJMessage.Builder()
                .withCommand("IDLE")
                .withStartTime(System.currentTimeMillis() + (syncTimeout != null ? syncTimeout : 0L))
                .build()
                .toString();
    }

    /**
     * Creates a {@link SCFJMessage} object with the IDLE show command.
     *
     * @return a {@link SCFJMessage} object with the IDLE show command.
     */
    public static String IDLE() {
        return new SCFJMessage.Builder()
                .withCommand("IDLE")
                .withStartTime(System.currentTimeMillis())
                .build()
                .toString();
    }

    /**
     * Creates a {@link SCFJMessage} object with the STOP show command.
     *
     * @return a {@link SCFJMessage} object with the STOP show command.
     */
    public static String STOP() {
        return new SCFJMessage.Builder()
                .withCommand("STOP")
                .withStartTime(System.currentTimeMillis())
                .build()
                .toString();
    }

}
