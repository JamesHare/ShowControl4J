package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * A class to test the {@link NullBrokerConnectionFactoryException}.
 *
 * @author James Hare
 */
public class TestNullBrokerConnectionFactoryException {

    /**
     * A test to ensure that the {@link NullBrokerConnectionFactoryException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullBrokerConnectionFactoryException() {
        NullBrokerConnectionFactoryException exception = new NullBrokerConnectionFactoryException();
        assertThat(exception, instanceOf(NullBrokerConnectionFactoryException.class));
        assertEquals("Broker Connection Factory cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullBrokerConnectionFactoryException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullBrokerConnectionFactoryExceptionWithMessage() {
        NullBrokerConnectionFactoryException exception = new NullBrokerConnectionFactoryException("Test message.");
        assertThat(exception, instanceOf(NullBrokerConnectionFactoryException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
