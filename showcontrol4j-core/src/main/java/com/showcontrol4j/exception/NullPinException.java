package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the pin argument of a show element constructor is null.
 *
 * @author James Hare
 */
public class NullPinException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullPinException() {
        super("Pin cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullPinException(String message) {
        super(message);
    }

}
