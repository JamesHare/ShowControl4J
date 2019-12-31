package com.showcontrol4j.exception;

/**
 * A custom exception intended to be thrown when the SCFJ Message Command argument is null.
 *
 * @author James Hare
 */
public class NullSCFJMessageCommandException extends RuntimeException {

    /**
     * Constructor.
     */
    public NullSCFJMessageCommandException() {
        super("SCFJMessage command cannot be null.");
    }

    /**
     * Constructor.
     *
     * @param message the message for the exception.
     */
    public NullSCFJMessageCommandException(String message) {
        super(message);
    }

}
