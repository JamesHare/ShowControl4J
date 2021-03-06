package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Broker Host argument is null.
 *
 * @author James Hare
 */
public class NullBrokerHostException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullBrokerHostException() {
        super("Broker hostname cannot be null.");
    }

    /**
     * Constructor.
     *
     * @param message the message for the exception.
     */
    public NullBrokerHostException(String message) {
        super(message);
    }

}
