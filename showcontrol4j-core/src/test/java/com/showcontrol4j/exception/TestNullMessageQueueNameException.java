package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * A class to test the {@link NullMessageQueueNameException}.
 *
 * @author James Hare
 */
public class TestNullMessageQueueNameException {

    /**
     * A test to ensure that the {@link NullMessageQueueNameException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullMessageQueueNameException() {
        NullMessageQueueNameException exception = new NullMessageQueueNameException();
        assertThat(exception, instanceOf(NullMessageQueueNameException.class));
        assertEquals("Message Queue name cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullMessageQueueNameException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullMessageQueueNameExceptionWithMessage() {
        NullMessageQueueNameException exception = new NullMessageQueueNameException("Test message.");
        assertThat(exception, instanceOf(NullMessageQueueNameException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
