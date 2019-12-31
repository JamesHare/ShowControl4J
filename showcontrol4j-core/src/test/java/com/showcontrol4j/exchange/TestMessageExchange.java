package com.showcontrol4j.exchange;

import com.showcontrol4j.exception.NullMessageExchangeNameException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test for the {@link MessageExchange} class.
 *
 * @author James Hare
 */
public class TestMessageExchange {

    private final String NAME = "test";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructor() {
        MessageExchange testMessageExchange = new MessageExchange.Builder().withName(NAME).build();
        assertThat(testMessageExchange, instanceOf(MessageExchange.class));
    }

    @Test
    public void testConstructor_nullName() {
        exception.expect(NullMessageExchangeNameException.class);
        exception.expectMessage("Message Queue name cannot be null.");
        MessageExchange testMessageExchange = new MessageExchange.Builder().build();
    }

    @Test
    public void testGetName() {
        MessageExchange testMessageExchange = new MessageExchange.Builder().withName(NAME).build();
        assertEquals(NAME, testMessageExchange.getName());
    }

}
