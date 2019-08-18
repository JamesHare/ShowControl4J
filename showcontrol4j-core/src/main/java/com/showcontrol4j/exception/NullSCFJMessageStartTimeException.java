package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the SCFJ Message Start Time argument is null.
 *
 * @author James Hare
 */
public class NullSCFJMessageStartTimeException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullSCFJMessageStartTimeException() {
        super("SCFJMessage start time cannot be null.");
    }

    /**
     * Constructor.
     * @param message the message for the exception.
     */
    public NullSCFJMessageStartTimeException(String message) {
        super(message);
    }

}
