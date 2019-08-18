package com.showcontrol4j.queue;

import com.showcontrol4j.exception.NullMessageQueueNameException;

/**
 * Serves as a class for a MessageQueue POJO.
 *
 * @author James Hare
 */
public class MessageQueue {

    private String messageQueueName;

    private MessageQueue(Builder builder) {
        if (builder.messageQueueName == null) {
            throw new NullMessageQueueNameException();
        }
        messageQueueName = builder.messageQueueName;
    }

    /**
     * Returns the name of the {@link MessageQueue}.
     * @return {@link String} the name of the {@link MessageQueue}.
     */
    public String getName() {
        return messageQueueName;
    }

    /**
     * Serves as a static builder class for the {@link MessageQueue} object.
     */
    public static class Builder {

        private String messageQueueName;

        /**
         * Constructor.
         */
        public Builder() {};

        /**
         * Sets the name of the {@link MessageQueue}.
         * @param messageQueueName {@link String} the name of the {@link MessageQueue}.
         * @return the Builder object.
         */
        public Builder withName(String messageQueueName) {
            this.messageQueueName = messageQueueName;
            return this;
        }

        /**
         * Builds the {@link MessageQueue} object.
         * @return a {@link MessageQueue} object.
         */
        public MessageQueue build() {
            return new MessageQueue(this);
        }

    }

}
