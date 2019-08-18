package com.showcontrol4j.broker;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.showcontrol4j.exception.NullHostBrokerException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Serves as a wrapper for the {@link ConnectionFactory} RabbitMQ object.
 *
 * @author James Hare
 */
public class BrokerConnectionFactory {

    private ConnectionFactory connectionFactory;
    private String hostname;

    private BrokerConnectionFactory(Builder builder) {
        if (builder.hostname == null) {
            throw new NullHostBrokerException();
        }
        hostname = builder.hostname;
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostname);
    }

    /**
     * Returns the hostname of the Broker Connection Factory.
     * @return {@link String} the hostname of the Broker Connection Factory.
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Returns a new connection from the Broker Connection Facotry.
     * @return {@link Connection} a new connection from the Broker Connection Facotry.
     * @throws IOException
     * @throws TimeoutException
     */
    public Connection newConnection() throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    /**
     * Serves as a static builder class to build a {@link BrokerConnectionFactory} object.
     */
    public static class Builder {

        private String hostname;

        /**
         * Constructor
         */
        public Builder(){}

        /**
         * Sets the hostname of the {@link BrokerConnectionFactory}.
         * @param hostname the hostname of the {@link BrokerConnectionFactory}. Must not be null.
         * @return the Builder object.
         */
        public Builder withHostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        /**
         * Builds the {@link BrokerConnectionFactory} object with the builder.
         * @return the {@link BrokerConnectionFactory} object.
         */
        public BrokerConnectionFactory build() {
            return new BrokerConnectionFactory(this);
        }

    }

}
