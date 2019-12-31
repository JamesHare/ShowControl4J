package com.showcontrol4j.trigger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.impl.AMQImpl;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageExchangeException;
import com.showcontrol4j.exchange.MessageExchange;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TestShowTriggerBase {

    private final String NAME = "TEST TRIGGER";
    private final Long ID = 123456L;
    private final Long SYNC_TIMEOUT = 5000L;
    private final String NOT_SPECIFIED = "NOT SPECIFIED";
    private final Long UNKNOWN_ID = 0L;

    @Mock
    private MessageExchange mockMessageExchange;
    @Mock
    private BrokerConnectionFactory mockBrokerConnectionFactory;
    @Mock
    private Connection mockConnection;
    @Mock
    private Channel mockChannel;
    @Mock
    private AMQImpl.Exchange.DeclareOk mockExchangeDeclareOk;
    @Mock
    private AMQImpl.Queue.DeclareOk mockQueueDeclareOk;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConstructor() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockMessageExchange.getName()).thenReturn("test");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(NAME, ID, SYNC_TIMEOUT, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };

        assertThat(testShowTriggerBase, CoreMatchers.instanceOf(ShowTriggerBase.class));
    }

    @Test
    public void testConstructor_nullMessageExchange() {
        exception.expect(NullMessageExchangeException.class);
        exception.expectMessage("Message Exchange cannot be null.");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(NAME, ID, SYNC_TIMEOUT, null, mockBrokerConnectionFactory) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };
    }

    @Test
    public void testConstructor_nullBrokerConnectionFactory() {
        exception.expect(NullBrokerConnectionFactoryException.class);
        exception.expectMessage("Broker Connection Factory cannot be null.");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(NAME, ID, SYNC_TIMEOUT, mockMessageExchange, null) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };
    }

    @Test
    public void testGetName() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockMessageExchange.getName()).thenReturn("test");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(NAME, ID, SYNC_TIMEOUT, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };

        assertEquals(NAME, testShowTriggerBase.getName());
    }

    @Test
    public void testGetName_nullName() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockMessageExchange.getName()).thenReturn("test");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(null, ID, SYNC_TIMEOUT, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };

        assertEquals(NOT_SPECIFIED, testShowTriggerBase.getName());
    }

    @Test
    public void testGetId() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockMessageExchange.getName()).thenReturn("test");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(NAME, ID, SYNC_TIMEOUT, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };

        assertEquals(ID, testShowTriggerBase.getId());
    }

    @Test
    public void testGetId_nullId() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockMessageExchange.getName()).thenReturn("test");

        ShowTriggerBase testShowTriggerBase = new ShowTriggerBase(NAME, null, SYNC_TIMEOUT, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            protected void startListener() {
                // do nothing
            }
        };

        assertEquals(UNKNOWN_ID, testShowTriggerBase.getId());
    }

}
