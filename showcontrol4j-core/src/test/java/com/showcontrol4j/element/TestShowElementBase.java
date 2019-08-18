package com.showcontrol4j.element;

import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.TestNullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageQueueException;
import com.showcontrol4j.queue.MessageQueue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Test for the {@link ShowElementBase} class.
 *
 * @author James Hare
 */
public class TestShowElementBase {

    MessageQueue messageQueue;
    BrokerConnectionFactory brokerConnectionFactory;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        messageQueue = new MessageQueue.Builder().withName("TEST").build();
        brokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname("localhost").build();
    }

    /**
     * A test to ensure that the {@link ShowElementBase} constructor works with valid input.
     */
    @Test
    public void testShowElementBaseWithValidInput() {
        ShowElementBase testShowElementBase = new ShowElementBase(messageQueue, brokerConnectionFactory) {
            @Override
            public void loop() {
                // do nothing
            }

            @Override
            public void idle() {
                // do nothing
            }
        };
        assertThat(testShowElementBase, instanceOf(ShowElementBase.class));
    }

    /**
     * A test to ensure that {@link NullMessageQueueException} is thrown when the message queue is null
     * in the {@link ShowElementBase} constructor.
     */
    @Test
    public void testShowElementBaseWithNullMessageQueue() {
        exception.expect(NullMessageQueueException.class);
        exception.expectMessage("Message Queue cannot be null.");
        ShowElementBase testShowElementBase = new ShowElementBase(null, brokerConnectionFactory) {
            @Override
            public void loop() {
                // do nothing
            }

            @Override
            public void idle() {
                // do nothing
            }
        };
    }

    /**
     * A test to ensure that {@link TestNullBrokerConnectionFactoryException} is thrown when the broker
     * connection factory is null in the {@link ShowElementBase} constructor.
     */
    @Test
    public void testShowElementBaseWithNullBrokerConnectionFactory() {
        exception.expect(NullBrokerConnectionFactoryException.class);
        exception.expectMessage("Broker Connection Factory cannot be null.");
        ShowElementBase testShowElementBase = new ShowElementBase(messageQueue, null) {
            @Override
            public void loop() {
                // do nothing
            }

            @Override
            public void idle() {
                // do nothing
            }
        };
    }

    /**
     * A test to ensure that the show element is paused for a given time of 1 second with a fault
     * tolerance of 1/10th of a second.
     */
    @Test
    public void testShowElementBasePause(){
        ShowElementBase testShowElementBase = new ShowElementBase(messageQueue, brokerConnectionFactory) {
            @Override
            public void loop() {
                // do nothing
            }

            @Override
            public void idle() {
                // do nothing
            }
        };
        long timeStampOne = System.currentTimeMillis();
        try {
            testShowElementBase.pause(1000);
        } catch (InterruptedException e) {
            System.out.println("An error occured while pausing the Show Element. " + e);
        }
        long timeStampTwo = System.currentTimeMillis();
        long totalPaused = timeStampTwo - timeStampOne;
        assertTrue(totalPaused > 900L);
        assertTrue(totalPaused < 1100L);
    }
}
