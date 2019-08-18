package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Broker Host argument is null.
 *
 * @author James Hare
 */
public class NullHostBrokerException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullHostBrokerException() {
        super("Broker hostname cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullHostBrokerException(String message) {
        super(message);
    }

}
