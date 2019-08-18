package com.showcontrol4j.broker;

import com.rabbitmq.client.Connection;
import com.showcontrol4j.exception.NullHostBrokerException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test for the {@link BrokerConnectionFactory} class.
 *
 * @author James Hare
 */
public class TestBrokerConnectionFactory {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private final String HOSTNAME = "localhost";

    /**
     * Test to ensure that {@link BrokerConnectionFactory} builder works with valid input.
     */
    @Test
    public void testBrokerConnectionFactoryWithValidInput() {
        BrokerConnectionFactory testBrokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname(HOSTNAME)
                .build();
        Connection testConnection = null;
        try {
            testConnection = testBrokerConnectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            System.out.println("An error occurred while getting a new connection. " + e);
        }
        assertThat(testBrokerConnectionFactory, instanceOf(BrokerConnectionFactory.class));
        assertEquals(HOSTNAME, testBrokerConnectionFactory.getHostname());
        assertThat(testConnection, instanceOf(Connection.class));
    }

    /**
     * Test to ensure that {@link NullHostBrokerException} is thrown when the hostname is null
     * in the {@link BrokerConnectionFactory} builder.
     */
    @Test
    public void testBrokerConnectionFactoryWithNullHostName() {
        exception.expect(NullHostBrokerException.class);
        exception.expectMessage("Broker hostname cannot be null.");
        BrokerConnectionFactory testBrokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname(null)
                .build();
    }

}
