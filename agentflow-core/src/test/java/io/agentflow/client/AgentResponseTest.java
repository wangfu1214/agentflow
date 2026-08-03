package io.agentflow.client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgentResponseTest {

    @Test
    void shouldCreateResponse() {
        AgentResponse response = AgentResponse.of(
                "execution-001",
                "Hello AgentFlow");

        assertEquals(
                "execution-001",
                response.executionId());
        assertEquals(
                "Hello AgentFlow",
                response.content());
    }

    @Test
    void shouldRejectNullExecutionId() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> AgentResponse.of(null, "Hello"));

        assertEquals(
                "executionId must not be null",
                exception.getMessage());
    }

    @Test
    void shouldRejectBlankExecutionId() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> AgentResponse.of("   ", "Hello"));

        assertEquals(
                "executionId must not be blank",
                exception.getMessage());
    }

    @Test
    void shouldRejectNullContent() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> AgentResponse.of("execution-001", null));

        assertEquals(
                "content must not be null",
                exception.getMessage());
    }

    @Test
    void shouldAllowEmptyContent() {
        AgentResponse response = AgentResponse.of("execution-001", "");
        assertEquals("", response.content());
    }
}
