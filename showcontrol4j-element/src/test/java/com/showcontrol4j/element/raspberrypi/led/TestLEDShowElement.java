package com.showcontrol4j.element.raspberrypi.led;

import com.pi4j.io.gpio.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.impl.AMQImpl;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.element.ShowElementBase;
import com.showcontrol4j.element.TestShowElementBase;
import com.showcontrol4j.exception.NullPinException;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.message.SCFJMessage;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test for the {@link LEDShowElement} class.
 *
 * @author James Hare
 */
public class TestLEDShowElement {

    private final String NAME = "TEST ELEMENT";
    private final Long ID = 123456L;
    private ExecutorService executor;
    static final SimulatedGpioProvider simulator = new SimulatedGpioProvider();

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
    @Mock
    private Pin mockIOPin;
    @Mock
    private EnumSet<PinMode> mockPinModeEnumSet;
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void before_all() {
        GpioFactory.setDefaultProvider(simulator);
    }

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        executor = Executors.newFixedThreadPool(5);
        when(mockBrokerConnectionFactory.newConnection()).thenReturn(mockConnection);
        when(mockConnection.createChannel()).thenReturn(mockChannel);
        when(mockChannel.exchangeDeclare(anyString(), anyString())).thenReturn(mockExchangeDeclareOk);
        when(mockChannel.queueDeclare()).thenReturn(mockQueueDeclareOk);
        when(mockQueueDeclareOk.getQueue()).thenReturn("test");
        when(mockChannel.queueBind(anyString(), anyString(), anyString())).thenReturn(mockBindOk);
        when(mockIOPin.getProvider()).thenReturn("RaspberryPi GPIO Provider");
        when(mockIOPin.getSupportedPinModes()).thenReturn(mockPinModeEnumSet);
        when(mockPinModeEnumSet.contains(any(PinMode.class))).thenReturn(true);
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdownNow();
    }

    @Test
    public void testConstructor() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        assertThat(testLEDShowElement, CoreMatchers.instanceOf(LEDShowElement.class));
    }

    @Test
    public void testConstructor_nullPin() throws Exception {
        exception.expect(NullPinException.class);
        exception.expectMessage("Pin cannot be null.");

        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, null) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };
    }

    @Test
    public void testShowSequence() throws Exception {
        boolean[] ranShowSequence = {false};
        SCFJMessage testGoSCFJMessage = new SCFJMessage.Builder().withCommand("GO").build();


        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                ranShowSequence[0] = true;
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        executor.submit(new TestTask(testLEDShowElement, testGoSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);
        assertTrue(ranShowSequence[0]);

        shutdownExecutorOnLEDShowElement(testLEDShowElement);
    }

    @Test
    public void testShowSequence_withStartTime() throws Exception {
        boolean[] ranShowSequence = {false};
        SCFJMessage testGoSCFJMessageWithStartTime = new SCFJMessage.Builder().withCommand("GO").withStartTime(System.currentTimeMillis() + 5000L).build();


        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                ranShowSequence[0] = true;
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        executor.submit(new TestTask(testLEDShowElement, testGoSCFJMessageWithStartTime));
        TimeUnit.MILLISECONDS.sleep(1000);
        assertFalse(ranShowSequence[0]);
        TimeUnit.MILLISECONDS.sleep(5000);
        assertTrue(ranShowSequence[0]);

        shutdownExecutorOnLEDShowElement(testLEDShowElement);
    }

    @Test
    public void testIdleLoop() throws Exception {
        int[] idleLoopCounter = {0};
        SCFJMessage testIdleSCFJMessage = new SCFJMessage.Builder().withCommand("IDLE").build();

        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                int count = idleLoopCounter[0];
                count++;
                idleLoopCounter[0] = count;

                // Idle loop is designed to run continuously after constructor.
                // This is a way to control how many times idleLoop() gets called during this test for asserting.
                // This thread will end when element receives a new valid message.
                while (true) {
                    // wait for a new message.
                }
            }
        };

        TimeUnit.MILLISECONDS.sleep(1000);

        // idleLoop() will have been called once from the constructor.
        assertEquals(1, idleLoopCounter[0]);

        executor.submit(new TestTask(testLEDShowElement, testIdleSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);

        // idleLoop() will have been called twice after receiving the idle message.
        assertEquals(2, idleLoopCounter[0]);

        shutdownExecutorOnLEDShowElement(testLEDShowElement);
    }

    @Test
    public void testIdleLoop_withStartTime() throws Exception {
        int[] idleLoopCounter = {0};
        SCFJMessage testIdleSCFJMessageWithStartTime = new SCFJMessage.Builder().withCommand("IDLE").withStartTime(System.currentTimeMillis() + 5000L).build();

        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                int count = idleLoopCounter[0];
                count++;
                idleLoopCounter[0] = count;

                // Idle loop is designed to run continuously after constructor.
                // This is a way to control how many times idleLoop() gets called during this test for asserting.
                // This thread will end when element receives a new valid message.
                while (true) {
                    // wait for a new message.
                }
            }
        };

        TimeUnit.MILLISECONDS.sleep(1000);

        // idleLoop() will have been called once from the constructor.
        assertEquals(1, idleLoopCounter[0]);

        executor.submit(new TestTask(testLEDShowElement, testIdleSCFJMessageWithStartTime ));
        TimeUnit.MILLISECONDS.sleep(5000);

        // idleLoop() will have been called twice after receiving the idle message.
        assertEquals(2, idleLoopCounter[0]);

        shutdownExecutorOnLEDShowElement(testLEDShowElement);
    }

    @Test
    public void testShutdownProcedure() throws Exception {
        SCFJMessage testStopSCFJMessage = new SCFJMessage.Builder().withCommand("STOP").build();

        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        GpioController mockGpioController = mock(GpioController.class);

        Field field = LEDShowElement.class.getDeclaredField("gpioController");
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(testLEDShowElement, mockGpioController);

        executor.submit(new TestTask(testLEDShowElement, testStopSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(1000);
        verify(mockGpioController, times(1)).shutdown();
    }

    @Test
    public void testShutdownProcedure_withStartTime() throws Exception {
        SCFJMessage testStopSCFJMessage = new SCFJMessage.Builder().withCommand("STOP").withStartTime(System.currentTimeMillis() + 5000L).build();

        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        GpioController mockGpioController = mock(GpioController.class);

        Field field = LEDShowElement.class.getDeclaredField("gpioController");
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(testLEDShowElement, mockGpioController);

        executor.submit(new TestTask(testLEDShowElement, testStopSCFJMessage));
        TimeUnit.MILLISECONDS.sleep(6000);
        verify(mockGpioController, times(1)).shutdown();
    }

    @Test
    public void testTurnOn() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        testLEDShowElement.turnOn();
        assertTrue(testLEDShowElement.getLED().isHigh());
    }

    @Test
    public void testTurnOff() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        testLEDShowElement.turnOff();
        assertTrue(testLEDShowElement.getLED().isLow());
    }

    @Test
    public void testToggle() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        assertTrue(testLEDShowElement.getLED().isLow());
        testLEDShowElement.toggle();
        assertTrue(testLEDShowElement.getLED().isHigh());
        testLEDShowElement.toggle();
        assertTrue(testLEDShowElement.getLED().isLow());
    }

    @Test
    public void testPulse() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        GpioPinDigitalOutput mockGpioPinDigitalOutput = mock(GpioPinDigitalOutput.class);

        Field field = LEDShowElement.class.getDeclaredField("LED");
        field.setAccessible(true);
        Field modifiers = Field.class.getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(testLEDShowElement, mockGpioPinDigitalOutput);

        testLEDShowElement.pulse(2000L, true);
        verify(mockGpioPinDigitalOutput, times(1)).pulse(2000L, true);
        testLEDShowElement.pulse(2000L, false);
        verify(mockGpioPinDigitalOutput, times(1)).pulse(2000L, false);
        testLEDShowElement.pulse(2000L);
        verify(mockGpioPinDigitalOutput, times(1)).pulse(2000L);
        testLEDShowElement.pulse(2000L, TimeUnit.MILLISECONDS);
        verify(mockGpioPinDigitalOutput, times(1)).pulse(2000L, TimeUnit.MILLISECONDS);
        testLEDShowElement.pulse(2000L, TimeUnit.MINUTES);
        verify(mockGpioPinDigitalOutput, times(1)).pulse(2000L, TimeUnit.MINUTES);
        testLEDShowElement.pulse(2000L, TimeUnit.HOURS);
        verify(mockGpioPinDigitalOutput, times(1)).pulse(2000L, TimeUnit.HOURS);
    }

    @Test
    public void testGetState() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        assertEquals(0, testLEDShowElement.getState());
        testLEDShowElement.toggle();
        assertEquals(1, testLEDShowElement.getState());
    }

    @Test
    public void testGetPinState() throws Exception {
        LEDShowElement testLEDShowElement = new LEDShowElement(NAME, ID, mockMessageExchange, mockBrokerConnectionFactory, mockIOPin) {
            @Override
            protected void showSequence() throws InterruptedException {
                // do nothing.
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                // do nothing.
            }
        };

        assertEquals(PinState.LOW, testLEDShowElement.getPinState());
        testLEDShowElement.toggle();
        assertEquals(PinState.HIGH, testLEDShowElement.getPinState());
    }

    //------------------------------------ HELPER METHODS ------------------------------------//

    private static Method getHandleMessageMethod() {
        Method handleMessageMethod = null;
        try {
            handleMessageMethod = ShowElementBase.class.getDeclaredMethod("handleMessage", SCFJMessage.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        assert handleMessageMethod != null;
        handleMessageMethod.setAccessible(true);
        return handleMessageMethod;
    }

    private static void shutdownExecutorOnLEDShowElement(LEDShowElement ledShowElement) throws Exception {
        Field executorField = ShowElementBase.class.getDeclaredField("executor");
        executorField.setAccessible(true);
        ExecutorService executorService = (ExecutorService) executorField.get(ledShowElement);
        executorService.shutdownNow();
    }

    private class TestTask implements Runnable {

        private final LEDShowElement element;
        private final SCFJMessage message;

        public TestTask(LEDShowElement element, SCFJMessage message) {
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
