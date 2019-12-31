package com.showcontrol4j.trigger.keyboard;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.impl.AMQImpl;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageExchangeException;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.trigger.ShowTriggerBase;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TestKeyboardEventTrigger {

    private final String NAME = "TEST TRIGGER";
    private final Long ID = 123456L;
    private final Long SYNC_TIMEOUT = 5000L;
    private final String NOT_SPECIFIED = "NOT SPECIFIED";
    private final Long UNKNOWN_ID = 0L;
    private final String TRIGGER_KEY = "a";
    private final String IDLE = "idle";
    private final String STOP = "stop";
    private ExecutorService executor;

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
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        executor = Executors.newFixedThreadPool(5);
        setupMockRules();
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdownNow();
    }

    @Test
    public void testConstructor() throws Exception {
        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, NAME, ID, SYNC_TIMEOUT,
                mockMessageExchange, mockBrokerConnectionFactory);

        assertThat(testKeyboardEventTrigger, CoreMatchers.instanceOf(ShowTriggerBase.class));
    }

    @Test
    public void testConstructor_nullMessageExchange() {
        exception.expect(NullMessageExchangeException.class);
        exception.expectMessage("Message Exchange cannot be null.");

        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, NAME, ID, SYNC_TIMEOUT,
                null, mockBrokerConnectionFactory);
    }

    @Test
    public void testConstructor_nullBrokerConnectionFactory() {
        exception.expect(NullBrokerConnectionFactoryException.class);
        exception.expectMessage("Broker Connection Factory cannot be null.");

        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, NAME, ID, SYNC_TIMEOUT,
                mockMessageExchange, null);
    }

    @Test
    public void testGetName() throws Exception {
        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, NAME, ID, SYNC_TIMEOUT,
                mockMessageExchange, mockBrokerConnectionFactory);

        assertEquals(NAME, testKeyboardEventTrigger.getName());
    }

    @Test
    public void testGetName_nullName() throws Exception {
        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, null, ID, SYNC_TIMEOUT,
                mockMessageExchange, mockBrokerConnectionFactory);

        assertEquals(NOT_SPECIFIED, testKeyboardEventTrigger.getName());
    }

    @Test
    public void testGetId() throws Exception {
        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, NAME, ID, SYNC_TIMEOUT,
                mockMessageExchange, mockBrokerConnectionFactory);

        assertEquals(ID, testKeyboardEventTrigger.getId());
    }

    @Test
    public void testGetId_nullId() throws Exception {
        KeyboardEventTrigger testKeyboardEventTrigger = new KeyboardEventTrigger(TRIGGER_KEY, NAME, null, SYNC_TIMEOUT,
                mockMessageExchange, mockBrokerConnectionFactory);

        assertEquals(UNKNOWN_ID, testKeyboardEventTrigger.getId());
    }

    //------------------------------------ HELPER METHODS ------------------------------------//

    private void setupMockRules() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockMessageExchange.getName()).thenReturn("test");
    }

    private class TestTask implements Runnable {

        private final KeyboardEventTrigger trigger;

        public TestTask(KeyboardEventTrigger trigger) {
            this.trigger = trigger;
        }

        @Override
        public void run() {
            trigger.startListener();
        }
    }

}
