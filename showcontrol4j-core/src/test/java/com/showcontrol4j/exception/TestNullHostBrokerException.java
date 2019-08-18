package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * A class to test the {@link NullHostBrokerException}.
 *
 * @author James Hare
 */
public class TestNullHostBrokerException {

    /**
     * A test to ensure that the {@link NullHostBrokerException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullHostBrokerException() {
        NullHostBrokerException exception = new NullHostBrokerException();
        assertThat(exception, instanceOf(NullHostBrokerException.class));
        assertEquals("Broker hostname cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullHostBrokerException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullHostBrokerExceptionWithMessage() {
        NullHostBrokerException exception = new NullHostBrokerException("Test message.");
        assertThat(exception, instanceOf(NullHostBrokerException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
