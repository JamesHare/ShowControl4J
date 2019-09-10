package com.showcontrol4j.element.led;

import com.pi4j.io.gpio.*;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.element.ShowElementBase;
import com.showcontrol4j.exception.NullShowElementNameException;
import com.showcontrol4j.exception.NullPinException;
import com.showcontrol4j.queue.MessageQueue;

import java.util.concurrent.TimeUnit;

/**
 * Serves as an LED Show Element. The loop and idle methods should remain abstract so that they can
 * be implemented at the time of instantiation.
 *
 * @author James Hare
 */
public abstract class LEDShowElement extends ShowElementBase {

    private final GpioPinDigitalOutput LED;
    private final String LED_ELEMENT_NAME;

    /**
     * Constructor for an LED Show Element.
     * @param messageQueue A {@link MessageQueue} object. Cannot be null.
     * @param brokerConnectionFactory A {@link BrokerConnectionFactory} object. Cannot be null.
     * @param ledName A name for the LED Show Element. Cannot be null.
     * @param pin A {@link Pin} to attach the LED Show Element to the microcontroller. Cannot be null.
     */
    public LEDShowElement(final MessageQueue messageQueue, final BrokerConnectionFactory brokerConnectionFactory, final String ledName, final Pin pin) {
        super(messageQueue, brokerConnectionFactory);
        if (ledName == null) {
            throw new NullShowElementNameException();
        } else if (pin == null) {
            throw new NullPinException();
        }
        this.LED_ELEMENT_NAME = ledName;
        LED = GpioFactory.getInstance().provisionDigitalOutputPin(pin, LED_ELEMENT_NAME, PinState.LOW);
        LED.setShutdownOptions(true, PinState.LOW);
    }

    /**
     * This is a comment.
     */
    public abstract void loop();

    public abstract void idle();

    /**
     * Sets the LED to high.
     */
    public void turnOn() {
        LED.high();
    }

    /**
     * Sets the LED to low.
     */
    public void turnOff() {
        LED.low();
    }

    /**
     * Toggles the LED state. If it is currently high, it will be set to low. If it is currently
     * low, it will be set to high.
     */
    public void toggle() {
        if (LED.isHigh()) {
            LED.low();
        } else {
            LED.high();
        }
    }

    /**
     * Pulses the LED for a given amount of milliseconds.
     * @param milliseconds the amount of time to pulse in milliseconds.
     * @param blockThread if the thread should be blocked from other calls.
     */
    public void pulse(long milliseconds, boolean blockThread) {
        LED.pulse(milliseconds, blockThread);
    }

    /**
     * Pulses the LED for a given amount of milliseconds.
     * @param milliseconds the amount of time to pulse in milliseconds.
     */
    public void pulse(long milliseconds) {
        LED.pulse(milliseconds);
    }

    /**
     * Pulses the LED for a given amount of time.
     * @param duration the duration of the pulse period.
     * @param timeUnit the time unit of the duration.
     */
    public void pulse(long duration, TimeUnit timeUnit) {
        LED.pulse(duration, timeUnit);
    }

    /**
     * Returns the pin state as an integer.
     * @return the pin state as an integer.
     */
    public int getState() {
        return LED.isHigh() ? 1 : 0;
    }

    /**
     * Returns the pin state as a {@link PinState} object.
     * @return the pin state as a {@link PinState} object.
     */
    public PinState getPinState() {
        return LED.isHigh() ? PinState.HIGH : PinState.LOW;
    }

    /**
     * Returns the name of the LED Show Element.
     * @return the name of the LED Show Element.
     */
    public String getLEDElementName() {
        return LED_ELEMENT_NAME;
    }

    /**
     * Returns the LED {@link GpioPinDigitalOutput} of the LED Show Element.
     * @return the LED {@link GpioPinDigitalOutput} of the LED Show Element.
     */
    public GpioPinDigitalOutput getLED() {
        return LED;
    }

}
