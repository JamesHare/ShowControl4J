package com.showcontrol4j.broker;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.showcontrol4j.exception.NullBrokerHostException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test for the {@link BrokerConnectionFactory} class.
 *
 * @author James Hare
 */
public class TestBrokerConnectionFactory {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private final String HOSTNAME = "test";

    @Mock
    ConnectionFactory mockConnectionFactory;
    @Mock
    Connection mockConnection;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructor() {
        BrokerConnectionFactory testBrokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname(HOSTNAME).build();
        assertThat(testBrokerConnectionFactory, instanceOf(BrokerConnectionFactory.class));
    }

    @Test
    public void testConstructor_nullHostname() {
        exception.expect(NullBrokerHostException.class);
        exception.expectMessage("Broker hostname cannot be null.");
        BrokerConnectionFactory testBrokerConnectionFactory = new BrokerConnectionFactory.Builder().build();
    }

    @Test
    public void testGetHostname() {
        BrokerConnectionFactory testBrokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname(HOSTNAME).build();
        assertEquals(HOSTNAME, testBrokerConnectionFactory.getHostname());
    }

    @Test
    public void testNewConnection() throws Exception {
        BrokerConnectionFactory testBrokerConnectionFactory = new BrokerConnectionFactory.Builder()
                .withHostname(HOSTNAME).build();
        Field connectionFactoryField = testBrokerConnectionFactory.getClass().getDeclaredField("CONNECTION_FACTORY");
        connectionFactoryField.setAccessible(true);
        connectionFactoryField.set(testBrokerConnectionFactory, mockConnectionFactory);
        when(mockConnectionFactory.newConnection()).thenReturn(mockConnection);

        Connection testConnection = null;
        try {
            testConnection = testBrokerConnectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            System.out.println("An error occurred while getting a new connection. " + e);
        }

        assertThat(testConnection, instanceOf(Connection.class));
    }

}
