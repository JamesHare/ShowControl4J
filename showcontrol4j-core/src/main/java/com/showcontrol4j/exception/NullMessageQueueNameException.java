package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Message Queue Name argument is null.
 *
 * @author James Hare
 */
public class NullMessageQueueNameException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullMessageQueueNameException() {
        super("Message Queue name cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullMessageQueueNameException(String message) {
        super(message);
    }

}
