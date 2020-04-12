package com.showcontrol4j.trigger.keyboard;

import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.trigger.ShowTriggerBase;

import java.util.Scanner;

public class KeyboardEventTrigger extends ShowTriggerBase {

    private final String TRIGGER_KEY;
    private final Scanner SCANNER;

    public KeyboardEventTrigger(String triggerKey, String showTriggerName, Long showTriggerId, Long syncTimeout,
                                MessageExchange messageExchange, BrokerConnectionFactory brokerConnectionFactory) {
        super(showTriggerName, showTriggerId, syncTimeout, messageExchange, brokerConnectionFactory);
        this.TRIGGER_KEY = triggerKey;
        SCANNER = new Scanner(System.in);
    }

    @Override
    public void startListener() {
        while (true) {
            try {
                String entry = SCANNER.next();
                if (entry.equalsIgnoreCase(TRIGGER_KEY)) {
                    sendGoMessage();
                } else if (entry.equalsIgnoreCase("STOP")) {
                    sendStopMessage();
                } else if (entry.equalsIgnoreCase("IDLE")) {
                    sendIdleMessage();
                }
            } catch (Exception e) {
                try {
                    sendStopMessage();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}