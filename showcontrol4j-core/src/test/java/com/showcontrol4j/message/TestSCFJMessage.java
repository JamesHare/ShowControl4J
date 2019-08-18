package com.showcontrol4j.message;

import com.showcontrol4j.exception.NullSCFJMessageCommandException;
import com.showcontrol4j.exception.NullSCFJMessageStartTimeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Test for the {@link SCFJMessage} class.
 *
 * @author James Hare
 */
public class TestSCFJMessage {

    private final String testCommand = "TEST";
    private final Long testTime = 1234567891234L;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * A test to ensure that the {@link SCFJMessage} builder works with valid input.
     */
    @Test
    public void testSCFJMessageWithValidInput() {
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).withStartTime(testTime).build();
        assertThat(testSCFJMessage, instanceOf(SCFJMessage.class));
        assertEquals(testCommand, testSCFJMessage.getCommand());
        assertEquals(testTime, testSCFJMessage.getStartTime());
        assertEquals("TEST:1234567891234", testSCFJMessage.toString());
    }

    /**
     * A test to ensure that {@link NullSCFJMessageCommandException} is thrown when the SCFJMessage command
     * is null in the {@link SCFJMessage} constructor.
     */
    @Test
    public void testNullSCFJMessageCommandExceptionThrownWhenSCFJMessageCommandIsNull() {
        exception.expect(NullSCFJMessageCommandException.class);
        exception.expectMessage("SCFJMessage command cannot be null.");
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(null).withStartTime(testTime).build();
    }

    /**
     * A test to ensure that {@link NullSCFJMessageStartTimeException} is thrown when the SCFJMessage
     * start time is null in the {@link SCFJMessage} constructor.
     */
    @Test
    public void testNullSCFJMessageStartTimeExceptionThrownWhenSCFJMessageStartTimeIsNull() {
        exception.expect(NullSCFJMessageStartTimeException.class);
        exception.expectMessage("SCFJMessage start time cannot be null.");
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).withStartTime(null).build();
    }

}
