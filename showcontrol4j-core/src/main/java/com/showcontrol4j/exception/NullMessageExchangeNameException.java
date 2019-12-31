package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Message Queue Name argument is null.
 *
 * @author James Hare
 */
public class NullMessageExchangeNameException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullMessageExchangeNameException() {
        super("Message Queue name cannot be null.");
    }

    /**
     * Constructor.
     *
     * @param message the message for the exception.
     */
    public NullMessageExchangeNameException(String message) {
        super(message);
    }

}
