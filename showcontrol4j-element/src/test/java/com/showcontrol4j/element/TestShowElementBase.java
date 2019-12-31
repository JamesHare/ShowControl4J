package com.showcontrol4j.element;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.impl.AMQImpl;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageExchangeException;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.message.SCFJMessage;
import junit.framework.TestCase;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test for the {@link ShowElementBase} class.
 *
 * @author James Hare
 */
public class TestShowElementBase {

    private final String NAME = "TEST ELEMENT";
    private final Long ID = 123456L;
    private final String NOT_SPECIFIED = "NOT SPECIFIED";
    private final Long UNKNOWN_ID = 0L;
    private final String TEST_BROKER_HOSTNAME = "Test Broker Hostname";
    private final String TEST_MESSAGE_EXCHANGE_NAME = "Test Message Exchange Name";
    private ExecutorService executor;
    SCFJMessage testIdleSCFJMessage = new SCFJMessage.Builder().withCommand("IDLE").build();
    SCFJMessage testGoSCFJMessage = new SCFJMessage.Builder().withCommand("GO").build();
    SCFJMessage testStopSCFJMessage = new SCFJMessage.Builder().withCommand("STOP").build();
    SCFJMessage testErrorSCFJMessage = new SCFJMessage.Builder().withCommand("ERROR").build();

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
    private AMQImpl.Queue.BindOk mockBindOk;
    @Mock
    private AMQImpl.Queue.DeclareOk mockQueueDeclareOk;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        executor = Executors.newFixedThreadPool(5);
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdownNow();
    }

    @Test
    public void testConstructor() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertThat(testShowElementBase, CoreMatchers.instanceOf(ShowElementBase.class));
    }

    @Test
    public void testConstructor_nullMessageExchange() {
        exception.expect(NullMessageExchangeException.class);
        exception.expectMessage("Message Exchange cannot be null.");

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, null, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };
    }

    @Test
    public void testConstructor_nullBrokerConnectionFactory() {
        exception.expect(NullBrokerConnectionFactoryException.class);
        exception.expectMessage("Broker Connection Factory cannot be null.");

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, null) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
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
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertEquals(NAME, testShowElementBase.getName());
    }

    @Test
    public void testGetName_nullName() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        ShowElementBase testShowElementBase = new ShowElementBase(null, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertEquals(NOT_SPECIFIED, testShowElementBase.getName());
    }

    @Test
    public void testGetId() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertEquals(ID, testShowElementBase.getId());
    }

    @Test
    public void testGetId_nullId() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, null, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertEquals(UNKNOWN_ID, testShowElementBase.getId());
    }

    @Test
    public void testGetBrokerHostname() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);
        when(mockBrokerConnectionFactory.getHostname()).thenReturn(TEST_BROKER_HOSTNAME);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertEquals(TEST_BROKER_HOSTNAME, testShowElementBase.getBrokerHostname());
    }

    @Test
    public void testGetMessageExchangeName() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);
        when(mockMessageExchange.getName()).thenReturn(TEST_MESSAGE_EXCHANGE_NAME);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        assertEquals(TEST_MESSAGE_EXCHANGE_NAME, testShowElementBase.getMessageExchangeName());
    }

    /**
     * A test to ensure that the show element is paused for a given time of 1 second with a fault
     * tolerance of 1/10th of a second.
     */
    @Test
    public void testPause() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        long timeStampOne = System.currentTimeMillis();
        try {
            testShowElementBase.pause(1000);
        } catch (InterruptedException e) {
            System.out.println("An error occurred while pausing the Show Element. " + e);
        }
        long timeStampTwo = System.currentTimeMillis();
        long totalPaused = timeStampTwo - timeStampOne;

        TestCase.assertTrue(totalPaused > 900L);
        TestCase.assertTrue(totalPaused < 1100L);
    }

    @Test
    public void testToString() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);
        when(mockMessageExchange.getName()).thenReturn(TEST_MESSAGE_EXCHANGE_NAME);
        when(mockBrokerConnectionFactory.getHostname()).thenReturn(TEST_BROKER_HOSTNAME);

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        final String expected = "ShowElement = [ name = TEST ELEMENT id = 123456, broker hostname = Test Broker Hostname, registered queue = Test Message Exchange Name ]";
        assertEquals(expected, testShowElementBase.toString());
    }

    @Test
    public void testHandleMessage_goMessage() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        boolean[] ranShowSequence = {false};
        boolean[] ranIdleLoop = {false};

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                ranShowSequence[0] = true;
            }

            @Override
            public void idleLoop() throws InterruptedException {
                ranIdleLoop[0] = true;
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        executor.submit(new TestTask(testShowElementBase, testGoSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);

        assertTrue(ranShowSequence[0]);
        assertTrue(ranIdleLoop[0]);
    }

    @Test
    public void testHandleMessage_idleMessage() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        boolean[] ranIdleLoop = {false};

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                ranIdleLoop[0] = true;
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        executor.submit(new TestTask(testShowElementBase, testIdleSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);

        assertTrue(ranIdleLoop[0]);
    }

    @Test
    public void testHandleMessage_stopMessage() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        boolean[] ranShutdownProcedure = {false};

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            public void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                // do nothing
            }

            @Override
            public void shutdownProcedure() {
                ranShutdownProcedure[0] = true;
            }
        };

        executor.submit(new TestTask(testShowElementBase, testStopSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);

        assertTrue(ranShutdownProcedure[0]);
    }

    @Test
    public void testHandleMessage_errorMessage() throws Exception {
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);

        boolean[] ranIdleLoop = {false};

        ShowElementBase testShowElementBase = new ShowElementBase(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing
            }

            @Override
            public void idleLoop() throws InterruptedException {
                ranIdleLoop[0] = true;
            }

            @Override
            public void shutdownProcedure() {
                // do nothing
            }
        };

        executor.submit(new TestTask(testShowElementBase, testErrorSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);

        assertTrue(ranIdleLoop[0]);
    }

    //------------------------------------ HELPER METHODS ------------------------------------//

    private static Method getHandleMessageMethod() {
        Class[] params = new Class[1];
        params[0] = SCFJMessage.class;
        Method handleMessageMethod = null;
        try {
            handleMessageMethod = ShowElementBase.class.getDeclaredMethod("analyzeMessage", SCFJMessage.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        handleMessageMethod.setAccessible(true);
        return handleMessageMethod;
    }

    private class TestTask implements Runnable {

        private final ShowElementBase element;
        private final SCFJMessage message;

        public TestTask(ShowElementBase element, SCFJMessage message) {
            this.element = element;
            this.message = message;
        }

        @Override
        public void run() {
            Method handleMessageMethod = getHandleMessageMethod();
            try {
                handleMessageMethod.invoke(element, message);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
