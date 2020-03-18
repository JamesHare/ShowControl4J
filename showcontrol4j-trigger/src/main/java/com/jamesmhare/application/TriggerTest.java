package com.jamesmhare.application;

import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.trigger.keyboard.KeyboardEventTrigger;

public class TriggerTest {

    public static void main(String[] args) {

        BrokerConnectionFactory broker = new BrokerConnectionFactory.Builder().withHostname("localhost").build();
        MessageExchange queue = new MessageExchange.Builder().withName("test").build();

        KeyboardEventTrigger keyboardEventTrigger = new KeyboardEventTrigger("go", "Test", 1L, 15000L, queue, broker);
        System.out.println("STARTING" + keyboardEventTrigger.toString());

    }

}
