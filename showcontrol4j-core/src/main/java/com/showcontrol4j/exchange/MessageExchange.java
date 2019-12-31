package com.showcontrol4j.exchange;

import com.showcontrol4j.exception.NullMessageExchangeNameException;

/**
 * Serves as a class for a MessageQueue POJO.
 *
 * @author James Hare
 */
public class MessageExchange {

    private final String NAME;

    private MessageExchange(Builder builder) {
        if (builder.NAME == null) {
            throw new NullMessageExchangeNameException();
        }
        NAME = builder.NAME;
    }

    /**
     * Returns the name of the {@link MessageExchange}.
     *
     * @return {@link String} the name of the {@link MessageExchange}.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Serves as a static builder class for the {@link MessageExchange} object.
     */
    public static class Builder {

        private String NAME;

        public Builder() {
        }

        /**
         * Sets the name of the {@link MessageExchange}.
         *
         * @param name {@link String} the name of the {@link MessageExchange}.
         * @return the Builder object.
         */
        public Builder withName(String name) {
            this.NAME = name;
            return this;
        }

        /**
         * Builds the {@link MessageExchange} object.
         *
         * @return a {@link MessageExchange} object.
         */
        public MessageExchange build() {
            return new MessageExchange(this);
        }

    }

}
