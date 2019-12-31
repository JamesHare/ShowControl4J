package com.showcontrol4j.message;

import com.showcontrol4j.exception.NullSCFJMessageCommandException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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

    @Test
    public void testConstructor() {
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).withStartTime(testTime).build();
        assertThat(testSCFJMessage, instanceOf(SCFJMessage.class));
    }

    @Test
    public void testConstructor_nullCommand() {
        exception.expect(NullSCFJMessageCommandException.class);
        exception.expectMessage("SCFJMessage command cannot be null.");
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withStartTime(testTime).build();
    }

    @Test
    public void testConstructor_nullStartTime() {
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).build();
        assertThat(testSCFJMessage.getStartTime(), instanceOf(Long.class));
    }

    @Test
    public void testGetCommand() {
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).withStartTime(testTime).build();
        assertEquals(testCommand, testSCFJMessage.getCommand());
    }

    @Test
    public void testGetStartTime() {
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).withStartTime(testTime).build();
        assertEquals(testTime, testSCFJMessage.getStartTime());
    }

    @Test
    public void testToString() {
        SCFJMessage testSCFJMessage = new SCFJMessage.Builder().withCommand(testCommand).withStartTime(testTime).build();
        assertEquals("TEST:1234567891234", testSCFJMessage.toString());
    }

}
