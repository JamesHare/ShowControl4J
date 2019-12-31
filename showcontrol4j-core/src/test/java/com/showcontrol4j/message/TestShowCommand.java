package com.showcontrol4j.message;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for the {@link ShowCommand} class.
 *
 * @author James Hare
 */
public class TestShowCommand {

    private final String GO = "GO";
    private final String IDLE = "IDLE";
    private final String STOP = "STOP";

    @Test
    public void testGo() {
        long currentTime = System.currentTimeMillis();
        String testCommand = ShowCommand.GO();
        String[] testCommandArr = testCommand.split(":");
        assertEquals(GO, testCommandArr[0]);
        long startTime = Long.parseLong(testCommandArr[1]);
        assertTrue(startTime > currentTime - 100);
        assertTrue(startTime < currentTime + 100);
    }

    @Test
    public void testGo_withSyncTimeout() {
        long currentTime = System.currentTimeMillis();
        String testCommand = ShowCommand.GO(10000L);
        String[] testCommandArr = testCommand.split(":");
        assertEquals(GO, testCommandArr[0]);
        long startTime = Long.parseLong(testCommandArr[1]);
        assertTrue(startTime > (currentTime + 10000) - 100);
        assertTrue(startTime < (currentTime + 10000) + 100);
    }

    @Test
    public void testGo_nullSyncTimeout() {
        long currentTime = System.currentTimeMillis();
        String testCommand = ShowCommand.GO(null);
        String[] testCommandArr = testCommand.split(":");
        assertEquals(GO, testCommandArr[0]);
        long startTime = Long.parseLong(testCommandArr[1]);
        assertTrue(startTime > currentTime - 100);
        assertTrue(startTime < currentTime + 100);
    }

    @Test
    public void testIdle_withSyncTimeout() {
        long currentTime = System.currentTimeMillis();
        String testCommand = ShowCommand.IDLE(10000L);
        String[] testCommandArr = testCommand.split(":");
        assertEquals(IDLE, testCommandArr[0]);
        long startTime = Long.parseLong(testCommandArr[1]);
        assertTrue(startTime > (currentTime + 10000) - 100);
        assertTrue(startTime < (currentTime + 10000) + 100);
    }

    @Test
    public void testIdle_nullSyncTimeout() {
        long currentTime = System.currentTimeMillis();
        String testCommand = ShowCommand.IDLE();
        String[] testCommandArr = testCommand.split(":");
        assertEquals(IDLE, testCommandArr[0]);
        long startTime = Long.parseLong(testCommandArr[1]);
        assertTrue(startTime > currentTime - 100);
        assertTrue(startTime < currentTime + 100);
    }

    @Test
    public void testStop() {
        long currentTime = System.currentTimeMillis();
        String testCommand = ShowCommand.STOP();
        String[] testCommandArr = testCommand.split(":");
        assertEquals(STOP, testCommandArr[0]);
        long startTime = Long.parseLong(testCommandArr[1]);
        assertTrue(startTime > currentTime - 100);
        assertTrue(startTime < currentTime + 100);
    }

}
