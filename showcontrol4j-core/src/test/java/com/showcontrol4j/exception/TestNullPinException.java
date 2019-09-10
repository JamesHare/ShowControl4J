package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullPinException}.
 *
 * @author James Hare
 */
public class TestNullPinException {

    /**
     * A test to ensure that the {@link NullPinException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullPinExceptionException() {
        NullPinException exception = new NullPinException();
        assertThat(exception, instanceOf(NullPinException.class));
        assertEquals("Pin cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullPinException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullPinExceptionWithMessage() {
        NullPinException exception = new NullPinException("Test message.");
        assertThat(exception, instanceOf(NullPinException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
