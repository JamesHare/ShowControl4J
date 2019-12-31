package com.showcontrol4j.element;

/**
 * Serves as an interface for a ShowElement. The ShowElement must define a loop and idle method
 * at the time of instantiation.
 *
 * @author James Hare
 */
public interface ShowElement {

    Long getId();

    String getName();

}
