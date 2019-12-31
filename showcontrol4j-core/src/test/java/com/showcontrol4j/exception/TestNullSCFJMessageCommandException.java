package com.showcontrol4j.exception;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * A class to test the {@link NullSCFJMessageCommandException}.
 *
 * @author James Hare
 */
public class TestNullSCFJMessageCommandException {

    @Test
    public void testConstructor() {
        NullSCFJMessageCommandException exception = new NullSCFJMessageCommandException();
        assertThat(exception, instanceOf(NullSCFJMessageCommandException.class));
        assertEquals("SCFJMessage command cannot be null.", exception.getMessage());
    }

    @Test
    public void testConstructor_withMessage() {
        NullSCFJMessageCommandException exception = new NullSCFJMessageCommandException("Test message.");
        assertThat(exception, instanceOf(NullSCFJMessageCommandException.class));
        assertEquals("Test message.", exception.getMessage());
    }

}
