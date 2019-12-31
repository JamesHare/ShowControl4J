package com.showcontrol4j.element;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageExchangeException;
import com.showcontrol4j.exchange.MessageExchange;
import com.showcontrol4j.message.SCFJMessage;
import com.showcontrol4j.message.encryption.MessageCipher;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.*;

/**
 * Serves as a standard implementation of the {@link ShowElement} interface. It is recommended that
 * all show elements extend the ShowElementBase class and implement data members and function members
 * to interact with that particular show element. The loop and idle methods should remain abstract so
 * that they can be implemented at the time of instantiation.
 *
 * @author James Hare
 */
public abstract class ShowElementBase implements ShowElement {

    protected final Long ID;
    protected final String NAME;
    private final MessageExchange messageExchange;
    private final BrokerConnectionFactory brokerConnectionFactory;
    private final ExecutorService executor;
    private Future runningFuture;

    /**
     * The constructor for a {@link ShowElementBase} object.
     *
     * @param messageExchange         a {@link MessageExchange} object. Must not be null.
     * @param brokerConnectionFactory a {@link BrokerConnectionFactory} object. Must not be null.
     */
    public ShowElementBase(String elementName, Long elementId, MessageExchange messageExchange, BrokerConnectionFactory brokerConnectionFactory) {
        if (messageExchange == null) {
            throw new NullMessageExchangeException();
        } else if (brokerConnectionFactory == null) {
            throw new NullBrokerConnectionFactoryException();
        }
        this.NAME = elementName;
        this.ID = elementId;
        this.messageExchange = messageExchange;
        this.brokerConnectionFactory = brokerConnectionFactory;
        executor = Executors.newFixedThreadPool(5);
        try {
            registerShowElement();
        } catch (IOException | TimeoutException e) {
            System.out.println("An error occurred while registering the show element. " + e);
        }
    }

    private void registerShowElement() throws IOException, TimeoutException {
        Channel channel = brokerConnectionFactory.newConnection().createChannel();
        channel.exchangeDeclare(messageExchange.getName(), "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, messageExchange.getName(), "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            System.out.println("Received message");
            SCFJMessage message;
            try {
                message = MessageCipher.decryptStringToMessage(new String(delivery.getBody(), StandardCharsets.UTF_8));
                System.out.println("The following message has been delivered " + message);
            } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                    NoSuchPaddingException | InvalidAlgorithmParameterException e) {
                System.out.println("An error occurred while decrypting the message." + e);
                message = new SCFJMessage.Builder().withCommand("ERROR").withStartTime(System.currentTimeMillis()).build();
            }
            handleMessage(message);
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }

    private void handleMessage(SCFJMessage message) {
        if (runningFuture != null) {
            runningFuture.cancel(true);
        }
        runningFuture = executor.submit(new MessageTask(message));
    }

    private void analyzeMessage(SCFJMessage message) {
        if (message.getCommand().equalsIgnoreCase("STOP")) {
            runStop();
        } else if (message.getCommand().equalsIgnoreCase("GO")) {
            runLoop(message);
        } else if (message.getCommand().equalsIgnoreCase("IDLE")) {
            runIdle(message);
        } else if (message.getCommand().equalsIgnoreCase("ERROR")) {
            System.out.println("An error occurred while handling the show command.");
            runIdle(message);
        }
    }

    private void runLoop(SCFJMessage message) {
        while (message.getStartTime() > System.currentTimeMillis()) {
            try {
                pause(100);
            } catch (InterruptedException e) {
                System.out.println("Sleeping was interrupted. " + e);
            }
        }
        try {
            showSequence();
            runIdle(new SCFJMessage.Builder().withCommand("IDLE").withStartTime(System.currentTimeMillis()).build());
        } catch (InterruptedException e) {
            System.out.println("Trace - New show command received. Thread is complete.");
        }
    }

    private void runIdle(SCFJMessage message) {
        while (message.getStartTime() > System.currentTimeMillis()) {
            try {
                pause(100);
            } catch (InterruptedException e) {
                System.out.println("Sleeping was interrupted. " + e);
            }
        }
        try {
            while (true) {
                idleLoop();
            }
        } catch (InterruptedException e) {
            System.out.println("Trace - New show command received. Thread is complete.");
        }
    }

    private void runStop() {
        runningFuture = null;
        executor.shutdownNow();
        shutdownProcedure();
    }

    /**
     * Pauses the show element thread for a specified about of time.
     *
     * @param milliseconds the amount of time to pause the show element thread.
     * @throws InterruptedException
     */
    protected final void pause(int milliseconds) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(milliseconds);
    }

    private class MessageTask implements Runnable {

        private final SCFJMessage message;

        public MessageTask(SCFJMessage message) {
            this.message = message;
        }

        @Override
        public void run() {
            analyzeMessage(message);
        }
    }

    /**
     * The abstract show element loop method. Must remain abstract and be implemented at the time
     * of instantiation.
     */
    protected abstract void showSequence() throws InterruptedException;

    /**
     * The abstract show element idle method. Must remain abstract and be implemented at the time
     * of instantiation.
     */
    protected abstract void idleLoop() throws InterruptedException;

    /**
     * The abstract show element shutdown method. Must be implemented by child classes before the
     * time of instantiation.
     */
    protected abstract void shutdownProcedure();

    /**
     * Gets the id of the Show Element.
     *
     * @return the id of the Show Element.
     */
    @Override
    public Long getId() {
        return ID != null ? ID : 0;
    }

    /**
     * Gets the name of the Show Element.
     *
     * @return the name of the Show Element.
     */
    @Override
    public String getName() {
        return NAME != null ? NAME : "NOT SPECIFIED";
    }

    /**
     * Gets the name of the message exchange that the show element is listening to.
     *
     * @return message exchange name.
     */
    public String getMessageExchangeName() {
        return messageExchange.getName();
    }

    /**
     * Gets the hostname of the broker that the show element is connected to.
     *
     * @return broker hostname.
     */
    public String getBrokerHostname() {
        return brokerConnectionFactory.getHostname();
    }

    @Override
    public String toString() {
        return "ShowElement = [ name = " + NAME + " id = " + ID.toString() + ", broker hostname = "
                + brokerConnectionFactory.getHostname() + ", registered queue = " + messageExchange.getName() + " ]";
    }

}
