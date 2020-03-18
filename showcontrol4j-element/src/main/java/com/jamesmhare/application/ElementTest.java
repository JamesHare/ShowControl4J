package com.jamesmhare.application;

import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.element.ShowElementBase;
import com.showcontrol4j.exchange.MessageExchange;

public class ElementTest {

    public static void main(String[] args) {

        BrokerConnectionFactory broker = new BrokerConnectionFactory.Builder().withHostname("localhost").build();
        MessageExchange exchange = new MessageExchange.Builder().withName("test").build();

        ShowElementBase element = new ShowElementBase("test", 1L, exchange, broker) {
            @Override
            protected void showSequence() {
                System.out.println("Start Running LOOP");
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("End Running LOOP");
            }

            @Override
            protected void idleLoop() throws InterruptedException {
                System.out.println("Running IDLE");
                Thread.sleep(10000);
                System.out.println("End Running IDLE");
            }

            @Override
            protected void shutdownProcedure() {
                System.out.println("Running STOP");
            }
        };
        System.out.println("STARTING ELEMENT " + element.getName());

    }

}
