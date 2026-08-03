package io.agentflow.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgentRequestTest {

    @Test
    void shouldCreateRequestFromInput() {
        AgentRequest request = AgentRequest.of("Hello");

        assertEquals("Hello", request.input());
    }

    @Test
    void shouldRejectNullInput() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> AgentRequest.of(null));

        assertEquals(
                "input must not be null",
                exception.getMessage());
    }

    @Test
    void shouldRejectBlankInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AgentRequest.of("   "));

        assertEquals(
                "input must not be blank",
                exception.getMessage());
    }

    @Test
    void shouldPreserveOriginalInput() {
        AgentRequest request = AgentRequest.of("  Hello AgentFlow  ");

        assertEquals(
                "  Hello AgentFlow  ",
                request.input());
    }
}
