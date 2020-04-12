package com.jamesmhare.application;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.element.ShowElementBase;
import com.showcontrol4j.element.raspberrypi.led.LEDShowElement;
import com.showcontrol4j.exchange.MessageExchange;

public class ElementTest {

    public static void main(String[] args) {

        BrokerConnectionFactory broker = new BrokerConnectionFactory.Builder().withHostname("192.168.1.10").withCredentials("admin", "test").build();
        MessageExchange exchange = new MessageExchange.Builder().withName("test").build();

        ShowElementBase element = new LEDShowElement("Test-LED-Element", 2L, exchange, broker, RaspiPin.GPIO_04) {
            @Override
            protected void showSequence() throws InterruptedException {
                for (int i = 0; i < 10; i++) {
                    turnOn();
                    pause(500);
                    turnOff();
                    pause(500);
                }
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                turnOn();
                pause(2000);
                turnOff();
                pause(2000);
            }
        };
        System.out.println("STARTING ELEMENT " + element.getName());

    }

}
