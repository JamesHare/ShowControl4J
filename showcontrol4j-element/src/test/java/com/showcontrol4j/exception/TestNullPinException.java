package com.showcontrol4j.exception;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullPinException}.
 *
 * @author James Hare
 */
public class TestNullPinException {

    @Test
    public void testConstructor() {
        NullPinException exception = new NullPinException();
        assertThat(exception, CoreMatchers.instanceOf(NullPinException.class));
        assertEquals("Pin cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_withMessage() {
        NullPinException exception = new NullPinException("Test message.");
        assertThat(exception, CoreMatchers.instanceOf(NullPinException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
