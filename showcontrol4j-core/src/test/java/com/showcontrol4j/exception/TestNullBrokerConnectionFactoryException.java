package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullBrokerConnectionFactoryException}.
 *
 * @author James Hare
 */
public class TestNullBrokerConnectionFactoryException {

    @Test
    public void testConstructor() {
        NullBrokerConnectionFactoryException exception = new NullBrokerConnectionFactoryException();
        assertThat(exception, instanceOf(NullBrokerConnectionFactoryException.class));
        assertEquals("Broker Connection Factory cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_withMessage() {
        NullBrokerConnectionFactoryException exception = new NullBrokerConnectionFactoryException("Test message.");
        assertThat(exception, instanceOf(NullBrokerConnectionFactoryException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
