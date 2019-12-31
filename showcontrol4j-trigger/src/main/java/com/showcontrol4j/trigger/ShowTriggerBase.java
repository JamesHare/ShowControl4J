package com.showcontrol4j.trigger;

import com.rabbitmq.client.Channel;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageExchangeException;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.message.ShowCommand;
import com.showcontrol4j.message.encryption.MessageCipher;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class ShowTriggerBase implements ShowTrigger {

    protected final String NAME;
    protected final Long ID;
    private final MessageExchange messageExchange;
    private final BrokerConnectionFactory brokerConnectionFactory;
    private Channel channel;
    private final Long syncTimeout;

    /**
     * The constructor for a {@link ShowTriggerBase} object.
     *
     * @param messageExchange         a {@link MessageExchange} object. Must not be null.
     * @param brokerConnectionFactory a {@link BrokerConnectionFactory} object. Must not be null.
     */
    public ShowTriggerBase(String showTriggerName, Long showTriggerId, Long syncTimeout,
                           MessageExchange messageExchange, BrokerConnectionFactory brokerConnectionFactory) {
        if (messageExchange == null) {
            throw new NullMessageExchangeException();
        } else if (brokerConnectionFactory == null) {
            throw new NullBrokerConnectionFactoryException();
        }
        this.NAME = showTriggerName;
        this.ID = showTriggerId;
        this.messageExchange = messageExchange;
        this.brokerConnectionFactory = brokerConnectionFactory;
        this.syncTimeout = syncTimeout;
        try {
            registerShowTrigger();
        } catch (IOException | TimeoutException e) {
            System.out.println("An error occurred while registering the show element. " + e);
        }
    }

    private void registerShowTrigger() throws IOException, TimeoutException {
        channel = brokerConnectionFactory.newConnection().createChannel();
        channel.exchangeDeclare(messageExchange.getName(), "fanout");
    }

    /**
     * A method that must be implemented to listen to the show trigger for events. Must be implemented by child
     * classes.
     */
    protected abstract void startListener();

    @Override
    public String getName() {
        return NAME != null ? NAME : "NOT SPECIFIED";
    }

    @Override
    public Long getId() {
        return ID != null ? ID : 0L;
    }

    protected void sendGoMessage() throws Exception {
        channel.basicPublish(messageExchange.getName(), "", null,
                MessageCipher.encryptMessageStringToString(ShowCommand.GO(syncTimeout != null ? syncTimeout : 0L)).getBytes());
    }

    protected void sendIdleMessage() throws Exception {
        channel.basicPublish(messageExchange.getName(), "", null,
                MessageCipher.encryptMessageStringToString(ShowCommand.IDLE(syncTimeout != null ? syncTimeout : 0L)).getBytes());
    }

    protected void sendStopMessage() throws Exception {
        channel.basicPublish(messageExchange.getName(), "", null,
                MessageCipher.encryptMessageStringToString(ShowCommand.STOP()).getBytes());
    }

}
