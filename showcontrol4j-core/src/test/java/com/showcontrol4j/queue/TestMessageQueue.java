package com.showcontrol4j.queue;

import com.showcontrol4j.exception.NullMessageQueueNameException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test for the {@link MessageQueue} class.
 *
 * @author James Hare
 */
public class TestMessageQueue {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * A test to ensure that the {@link MessageQueue} builder works with valid input.
     */
    @Test
    public void testMessageQueueBuilderWithValidName() {
        MessageQueue testMessageQueue = new MessageQueue.Builder().withName("test").build();
        assertThat(testMessageQueue, instanceOf(MessageQueue.class));
        assertEquals(testMessageQueue.getName(), "test");
    }

    /**
     * A test to ensure that {@link NullMessageQueueNameException} is thrown when the broker
     * connection factory is null in the {@link MessageQueue} constructor.
     */
    @Test
    public void testMessageQueueBuilderWithNullName() {
        exception.expect(NullMessageQueueNameException.class);
        exception.expectMessage("Message Queue name cannot be null.");
        MessageQueue testMessageQueue = new MessageQueue.Builder().build();
    }
}
