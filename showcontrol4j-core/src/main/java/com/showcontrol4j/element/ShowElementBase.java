package com.showcontrol4j.element;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.showcontrol4j.broker.BrokerConnectionFactory;
import com.showcontrol4j.exception.NullBrokerConnectionFactoryException;
import com.showcontrol4j.exception.NullMessageQueueException;
import com.showcontrol4j.message.SCFJMessage;
import com.showcontrol4j.message.encryption.MessageCipher;
import com.showcontrol4j.queue.MessageQueue;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Serves as a standard implementation of the {@link ShowElement} interface. It is recommended that
 * all show elements extend the ShowElementBase class and implement data members and function members
 * to interact with that particular show element. The loop and idle methods should remain abstract so
 * that they can be implemented at the time of instantiation.
 *
 * @author James Hare
 */
public abstract class ShowElementBase implements ShowElement {

    private MessageQueue messageQueue;
    private BrokerConnectionFactory brokerConnectionFactory;

    /**
     * The constructor for a {@link ShowElementBase} object.
     * @param messageQueue a {@link MessageQueue} object. Must not be null.
     * @param brokerConnectionFactory a {@link BrokerConnectionFactory} object. Must not be null.
     */
    public ShowElementBase(MessageQueue messageQueue, BrokerConnectionFactory brokerConnectionFactory) {
        if (messageQueue == null) {
            throw new NullMessageQueueException();
        } else if (brokerConnectionFactory == null) {
            throw new NullBrokerConnectionFactoryException();
        }
        this.messageQueue = messageQueue;
        this.brokerConnectionFactory = brokerConnectionFactory;
        try {
            registerShowElement();
        } catch (IOException | TimeoutException e) {
            System.out.println("An error occurred while registering the show element. " + e);
        }
    }

    /**
     * The abstract show element loop method. Must remain abstract and be implemented at the time
     * of instantiation.
     */
    public abstract void loop();

    /**
     * The abstract show element idle method. Must remain abstract and be implemented at the time
     * of instantiation.
     */
    public abstract void idle();

    private void registerShowElement() throws IOException, TimeoutException {
        Connection connection = brokerConnectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(messageQueue.getName(), true, false, false, null);
        channel.basicQos(1);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            SCFJMessage message;
            try {
                message = MessageCipher.decryptStringToMessage(new String(delivery.getBody(), "UTF-8"));
            } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException |
                    NoSuchPaddingException | InvalidAlgorithmParameterException e) {
                System.out.println("An error occurred while decrypting the message." + e);
                message = new SCFJMessage.Builder().withCommand("ERROR").withStartTime(0L).build();
            }
            handleMessage(message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(messageQueue.getName(), false, deliverCallback, consumerTag -> {
        });
    }

    private void handleMessage(SCFJMessage message) {
        if (message.getCommand().equalsIgnoreCase("ERROR")) {
            System.out.println("An error occured while handling the show command.");
        } else if (message.getCommand().equalsIgnoreCase("GO")) {
            runLoop(message);
        } else if (message.getCommand().equalsIgnoreCase("STOP")) {
            runIdle(message);
        } else if (message.getCommand().equalsIgnoreCase("IDLE")) {
            runIdle(message);
        }
        idle();
    }

    private void runIdle(SCFJMessage message) {
        while (message.getStartTime().longValue() < System.currentTimeMillis()) {
            try {
                pause(100);
            } catch (InterruptedException e) {
                System.out.println("Sleeping was interrupted. " + e);;
            }
        }
        idle();
    }

    private void runLoop(SCFJMessage message) {
        while (message.getStartTime().longValue() < System.currentTimeMillis()) {
            try {
                pause(100);
            } catch (InterruptedException e) {
                System.out.println("Sleeping was interrupted. " + e);;
            }
        }
        loop();
    }

    /**
     * Pauses the show element thread for a specified about of time.
     * @param milliseconds the amount of time to pause the show element thread.
     * @throws InterruptedException
     */
    public final void pause(int milliseconds) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(milliseconds);
    }

}
