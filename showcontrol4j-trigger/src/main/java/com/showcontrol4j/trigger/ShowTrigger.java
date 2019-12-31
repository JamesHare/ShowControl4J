package com.showcontrol4j.trigger;

/**
 * Serves as an interface for a ShowTrigger. The ShowTrigger must define a triggerElements method
 * at the time of instantiation.
 *
 * @author James Hare
 */
public interface ShowTrigger {

    Long getId();

    String getName();

}
