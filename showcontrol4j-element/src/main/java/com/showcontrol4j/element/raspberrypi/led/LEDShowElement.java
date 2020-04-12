package com.showcontrol4j.element.raspberrypi.led;

import com.pi4j.io.gpio.*;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.element.ShowElementBase;
import com.showcontrol4j.exception.NullPinException;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.message.SCFJMessage;

import java.util.concurrent.TimeUnit;

/**
 * Serves as an LED Show Element. The loop and idle methods should remain abstract so that they can
 * be implemented at the time of instantiation.
 *
 * @author James Hare
 */
public abstract class LEDShowElement extends ShowElementBase {

    private final GpioPinDigitalOutput LED;
    private final GpioController gpioController;

    /**
     * Constructor for an LED Show Element.
     *
     * @param messageExchange         A {@link MessageExchange} object. Cannot be null.
     * @param brokerConnectionFactory A {@link BrokerConnectionFactory} object. Cannot be null.
     * @param name                    A name for the LED Show Element. Cannot be null.
     * @param pin                     A {@link Pin} to attach the LED Show Element to the microcontroller. Cannot be null.
     */
    public LEDShowElement(String name, long id, MessageExchange messageExchange, BrokerConnectionFactory brokerConnectionFactory, Pin pin) {
        super(name, id, messageExchange, brokerConnectionFactory);
        if (pin == null) {
            throw new NullPinException();
        }
        gpioController = GpioFactory.getInstance();
        LED = gpioController.provisionDigitalOutputPin(pin, NAME, PinState.LOW);
        LED.setShutdownOptions(true, PinState.LOW);
        handleMessage(new SCFJMessage.Builder().withCommand("IDLE").build());
    }

    /**
     * The abstract show element loop method. Must be implemented at the time
     * of instantiation.
     */
    @Override
    protected abstract void showSequence() throws InterruptedException;

    /**
     * The abstract show element idle method. Must be implemented at the time
     * of instantiation.
     */
    @Override
    protected abstract void idleLoop() throws InterruptedException;

    /**
     * The abstract show element shutdown method.
     */
    @Override
    protected void shutdownProcedure() {
        gpioController.shutdown();
    }

    /**
     * Sets the LED to high.
     */
    protected void turnOn() {
        LED.high();
    }

    /**
     * Sets the LED to low.
     */
    protected void turnOff() {
        LED.low();
    }

    /**
     * Toggles the LED state. If it is currently high, it will be set to low. If it is currently
     * low, it will be set to high.
     */
    protected void toggle() {
        if (LED.isHigh()) {
            LED.low();
        } else {
            LED.high();
        }
    }

    /**
     * Pulses the LED for a given amount of milliseconds.
     *
     * @param milliseconds the amount of time to pulse in milliseconds.
     * @param blockThread  if the thread should be blocked from other calls.
     */
    protected void pulse(long milliseconds, boolean blockThread) {
        LED.pulse(milliseconds, blockThread);
    }

    /**
     * Pulses the LED for a given amount of milliseconds.
     *
     * @param milliseconds the amount of time to pulse in milliseconds.
     */
    protected void pulse(long milliseconds) {
        LED.pulse(milliseconds);
    }

    /**
     * Pulses the LED for a given amount of time.
     *
     * @param duration the duration of the pulse period.
     * @param timeUnit the time unit of the duration.
     */
    protected void pulse(long duration, TimeUnit timeUnit) {
        LED.pulse(duration, timeUnit);
    }

    /**
     * Returns the pin state as an integer.
     *
     * @return the pin state as an integer.
     */
    protected int getState() {
        return LED.isHigh() ? 1 : 0;
    }

    /**
     * Returns the pin state as a {@link PinState} object.
     *
     * @return the pin state as a {@link PinState} object.
     */
    protected PinState getPinState() {
        return LED.isHigh() ? PinState.HIGH : PinState.LOW;
    }

    /**
     * Returns the LED {@link GpioPinDigitalOutput} of the LED Show Element.
     *
     * @return the LED {@link GpioPinDigitalOutput} of the LED Show Element.
     */
    protected GpioPinDigitalOutput getLED() {
        return LED;
    }

}
