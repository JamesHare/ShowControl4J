package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullMessageExchangeException}.
 *
 * @author James Hare
 */
public class TestNullMessageExchangeException {

    @Test
    public void testConstructor() {
        NullMessageExchangeException exception = new NullMessageExchangeException();
        assertThat(exception, instanceOf(NullMessageExchangeException.class));
        assertEquals("Message Exchange cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_withMessage() {
        NullMessageExchangeException exception = new NullMessageExchangeException("Test message.");
        assertThat(exception, instanceOf(NullMessageExchangeException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
