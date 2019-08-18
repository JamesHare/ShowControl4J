package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * A class to test the {@link NullMessageQueueException}.
 *
 * @author James Hare
 */
public class TestNullMessageQueueException {

    /**
     * A test to ensure that the {@link NullMessageQueueException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullMessageQueueException() {
        NullMessageQueueException exception = new NullMessageQueueException();
        assertThat(exception, instanceOf(NullMessageQueueException.class));
        assertEquals("Message Queue cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullMessageQueueException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullMessageQueueExceptionWithMessage() {
        NullMessageQueueException exception = new NullMessageQueueException("Test message.");
        assertThat(exception, instanceOf(NullMessageQueueException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
