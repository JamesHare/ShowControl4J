package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Message Queue argument is null.
 *
 * @author James Hare
 */
public class NullMessageQueueException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullMessageQueueException() {
        super("Message Queue cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullMessageQueueException(String message) {
        super(message);
    }

}
