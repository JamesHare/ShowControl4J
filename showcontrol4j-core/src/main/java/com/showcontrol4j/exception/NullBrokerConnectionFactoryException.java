package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the Broker Connection Factory argument is null.
 *
 * @author James Hare
 */
public class NullBrokerConnectionFactoryException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullBrokerConnectionFactoryException() {
        super("Broker Connection Factory cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullBrokerConnectionFactoryException(String message) {
        super(message);
    }

}
