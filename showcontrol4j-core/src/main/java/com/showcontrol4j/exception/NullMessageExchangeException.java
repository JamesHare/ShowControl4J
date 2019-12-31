package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Message Queue argument is null.
 *
 * @author James Hare
 */
public class NullMessageExchangeException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullMessageExchangeException() {
        super("Message Exchange cannot be null.");
    }

    /**
     * Constructor.
     *
     * @param message the message for the exception.
     */
    public NullMessageExchangeException(String message) {
        super(message);
    }

}
