package com.showcontrol4j.broker;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.showcontrol4j.exception.NullBrokerHostException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Serves as a wrapper for the {@link ConnectionFactory} RabbitMQ object.
 *
 * @author James Hare
 */
public class BrokerConnectionFactory {

    private final ConnectionFactory CONNECTION_FACTORY;
    private final String HOSTNAME;

    private BrokerConnectionFactory(Builder builder) {
        if (builder.HOSTNAME == null) {
            throw new NullBrokerHostException();
        }
        HOSTNAME = builder.HOSTNAME;
        CONNECTION_FACTORY = new ConnectionFactory();
        CONNECTION_FACTORY.setHost(HOSTNAME);
    }

    /**
     * Returns the hostname of the Broker Connection Factory.
     *
     * @return {@link String} the hostname of the Broker Connection Factory.
     */
    public String getHostname() {
        return HOSTNAME;
    }

    /**
     * Returns a new connection from the Broker Connection Factory.
     *
     * @return {@link Connection} a new connection from the Broker Connection Factory.
     * @throws IOException
     * @throws TimeoutException
     */
    public Connection newConnection() throws IOException, TimeoutException {
        return CONNECTION_FACTORY.newConnection();
    }

    /**
     * Serves as a static builder class to build a {@link BrokerConnectionFactory} object.
     */
    public static class Builder {

        private String HOSTNAME;

        /**
         * Constructor
         */
        public Builder() {
        }

        /**
         * Sets the hostname of the {@link BrokerConnectionFactory}.
         *
         * @param hostname the hostname of the {@link BrokerConnectionFactory}. Must not be null.
         * @return the Builder object.
         */
        public Builder withHostname(String hostname) {
            this.HOSTNAME = hostname;
            return this;
        }

        /**
         * Builds the {@link BrokerConnectionFactory} object with the builder.
         *
         * @return the {@link BrokerConnectionFactory} object.
         */
        public BrokerConnectionFactory build() {
            return new BrokerConnectionFactory(this);
        }

    }

}
