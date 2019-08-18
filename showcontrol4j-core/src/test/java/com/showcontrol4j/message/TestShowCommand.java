package com.showcontrol4j.message;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test for the {@link ShowCommand} class.
 *
 * @author James Hare
 */
public class TestShowCommand {

    /**
     * A test to ensure that the GO method creates the expected command.
     */
    @Test
    public void testGoCommand() {
        String testCommand = new ShowCommand().GO();
        String[] testCommandArr = testCommand.split(":");
        assertThat(testCommand, instanceOf(String.class));
        assertEquals("GO", testCommandArr[0]);
    }

    /**
     * A test to ensure that the STOP method creates the expected command.
     */
    @Test
    public void testStopCommand() {
        String testCommand = new ShowCommand().STOP();
        String[] testCommandArr = testCommand.split(":");
        assertThat(testCommand, instanceOf(String.class));
        assertEquals("STOP", testCommandArr[0]);
    }

    /**
     * A test to ensure that the IDLE method creates the expected command.
     */
    @Test
    public void testIdleCommand() {
        String testCommand = new ShowCommand().IDLE();
        String[] testCommandArr = testCommand.split(":");
        assertThat(testCommand, instanceOf(String.class));
        assertEquals("IDLE", testCommandArr[0]);
    }

}
