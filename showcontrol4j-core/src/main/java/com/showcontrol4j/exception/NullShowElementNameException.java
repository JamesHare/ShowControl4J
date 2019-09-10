package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the name argument of a Show Element constructor
 * is null.
 *
 * @author James Hare
 */
public class NullShowElementNameException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullShowElementNameException() {
        super("Show Element name cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullShowElementNameException(String message) {
        super(message);
    }

}
