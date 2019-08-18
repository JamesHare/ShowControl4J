package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * A class to test the {@link NullSCFJMessageCommandException}.
 *
 * @author James Hare
 */
public class TestNullSCFJMessageCommandException {

    /**
     * A test to ensure that the {@link NullSCFJMessageCommandException} default constructor
     * creates the object and that the message remains null.
     */
    @Test
    public void testNullSCFJMessageCommandException() {
        NullSCFJMessageCommandException exception = new NullSCFJMessageCommandException();
        assertThat(exception, instanceOf(NullSCFJMessageCommandException.class));
        assertEquals("SCFJMessage command cannot be null.", exception.getMessage());
    }

    /**
     * A test to ensure that the {@link NullSCFJMessageCommandException} constructor creates the
     * exception with a custom message.
     */
    @Test
    public void testNullSCFJMessageCommandExceptionWithMessage() {
        NullSCFJMessageCommandException exception = new NullSCFJMessageCommandException("Test message.");
        assertThat(exception, instanceOf(NullSCFJMessageCommandException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
