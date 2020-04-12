package com.jamesmhare.application;

import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.trigger.keyboard.KeyboardEventTrigger;

public class TriggerTest {

    public static void main(String[] args) {

        BrokerConnectionFactory broker = new BrokerConnectionFactory.Builder().withHostname("192.168.1.10").withCredentials("admin", "test").build();
        MessageExchange queue = new MessageExchange.Builder().withName("test").build();

        KeyboardEventTrigger keyboardEventTrigger = new KeyboardEventTrigger("go", "Test-Trigger", 1L, 10000L, queue, broker);
        System.out.println("STARTING" + keyboardEventTrigger.toString());
        keyboardEventTrigger.startListener();

    }

}
