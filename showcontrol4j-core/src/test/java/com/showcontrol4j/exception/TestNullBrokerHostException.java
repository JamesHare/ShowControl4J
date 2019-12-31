package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullBrokerHostException}.
 *
 * @author James Hare
 */
public class TestNullBrokerHostException {

    @Test
    public void testConstructor() {
        NullBrokerHostException exception = new NullBrokerHostException();
        assertThat(exception, instanceOf(NullBrokerHostException.class));
        assertEquals("Broker hostname cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_withMessage() {
        NullBrokerHostException exception = new NullBrokerHostException("Test message.");
        assertThat(exception, instanceOf(NullBrokerHostException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
