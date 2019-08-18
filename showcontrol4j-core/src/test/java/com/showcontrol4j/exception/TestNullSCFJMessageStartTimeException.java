package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * A class to test the {@link NullSCFJMessageStartTimeException}.
 *
 * @author James Hare
 */
public class TestNullSCFJMessageStartTimeException {

    /**
     * A test to ensure that the {@link NullSCFJMessageStartTimeException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullSCFJMessageStartTimeException() {
        NullSCFJMessageStartTimeException exception = new NullSCFJMessageStartTimeException();
        assertThat(exception, instanceOf(NullSCFJMessageStartTimeException.class));
        assertEquals("SCFJMessage start time cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullSCFJMessageStartTimeException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullSCFJMessageStartTimeExceptionWithMessage() {
        NullSCFJMessageStartTimeException exception = new NullSCFJMessageStartTimeException("Test message.");
        assertThat(exception, instanceOf(NullSCFJMessageStartTimeException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
