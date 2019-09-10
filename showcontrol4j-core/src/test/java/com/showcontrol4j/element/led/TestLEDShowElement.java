package com.showcontrol4j.element.led;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.queue.MessageQueue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Test for the {@link LEDShowElement} class.
 *
 * @author James Hare
 */
public class TestLEDShowElement {

    private MessageQueue messageQueue;
    private BrokerConnectionFactory brokerConnectionFactory;
    private final String LED_NAME = "Test LED";
    private final Pin PIN = RaspiPin.GPIO_01;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        messageQueue = new MessageQueue.Builder().withName("TEST").build();
        brokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname("localhost").build();
    }

}
