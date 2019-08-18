package com.showcontrol4j.message;

import com.showcontrol4j.exception.NullSCFJMessageCommandException;
import com.showcontrol4j.exception.NullSCFJMessageStartTimeException;

/**
 * Serves as a class for a SCFJMessage POJO.
 *
 * @author James Hare
 */
public class SCFJMessage {

    private String command;
    private Long startTime;

    private SCFJMessage(Builder builder) {
        if (builder.command == null) {
            throw new NullSCFJMessageCommandException();
        } else if (builder.startTime == null) {
            throw new NullSCFJMessageStartTimeException();
        }
        this.command = builder.command;
        this.startTime = builder.startTime;
    }

    /**
     * Returns the command of the SCFJMessage.
     * @return {@link String} the command of the SCFJMessage.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the start time of the SCFJMessage.
     * @return {@link Long} the start time of the SCFJMessage.
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * Returns the SCFJMessage object as a String.
     * @return {@link String} the SCFJMessage object as a String.
     */
    @Override
    public String toString() {
        return command + ":" + startTime;
    }

    /**
     * Serves as a static builder class for the {@link SCFJMessage} object.
     */
    public static class Builder {

        private String command;
        private Long startTime;

        /**
         * Constructor
         */
        public Builder() {}

        /**
         * Sets the command of the {@link SCFJMessage}.
         * @param command {@link String} the command of the {@link SCFJMessage}.
         * @return the Builder object.
         */
        public Builder withCommand(String command) {
            this.command = command;
            return this;
        }

        /**
         * Sets the start time of the {@link SCFJMessage}.
         * @param startTime {@link Long} the start time of the {@link SCFJMessage}.
         * @return the Builder object.
         */
        public Builder withStartTime(Long startTime) {
            this.startTime = startTime;
            return this;
        }

        /**
         * Builds the {@link SCFJMessage} object.
         * @return a {@link SCFJMessage} object.
         */
        public SCFJMessage build() {
            return new SCFJMessage(this);
        }

    }

}
