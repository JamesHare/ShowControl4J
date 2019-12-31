package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullMessageExchangeNameException}.
 *
 * @author James Hare
 */
public class TestNullMessageExchangeNameException {

    @Test
    public void testConstructor() {
        NullMessageExchangeNameException exception = new NullMessageExchangeNameException();
        assertThat(exception, instanceOf(NullMessageExchangeNameException.class));
        assertEquals("Message Queue name cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_withMessage() {
        NullMessageExchangeNameException exception = new NullMessageExchangeNameException("Test message.");
        assertThat(exception, instanceOf(NullMessageExchangeNameException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
