package io.agentflow.execution;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExecutionResultTest {

    @Test
    void shouldCreateExecutionResult() {
        ExecutionResult result =
                ExecutionResult.of("Hello AgentFlow");

        assertEquals(
                "Hello AgentFlow",
                result.content()
        );
    }

    @Test
    void shouldAllowEmptyContent() {
        ExecutionResult result =
                ExecutionResult.of("");

        assertEquals("", result.content());
    }

    @Test
    void shouldRejectNullContent() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ExecutionResult.of(null)
        );

        assertEquals(
                "content must not be null",
                exception.getMessage()
        );
    }
}