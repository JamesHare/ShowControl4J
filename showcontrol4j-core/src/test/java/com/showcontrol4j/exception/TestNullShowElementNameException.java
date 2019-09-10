package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullShowElementNameException}.
 *
 * @author James Hare
 */
public class TestNullShowElementNameException {

    /**
     * A test to ensure that the {@link NullShowElementNameException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullShowElementNameException() {
        NullShowElementNameException exception = new NullShowElementNameException();
        assertThat(exception, instanceOf(NullShowElementNameException.class));
        assertEquals("Show Element name cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullShowElementNameException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullShowElementNameExceptionWithMessage() {
        NullShowElementNameException exception = new NullShowElementNameException("Test message.");
        assertThat(exception, instanceOf(NullShowElementNameException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
