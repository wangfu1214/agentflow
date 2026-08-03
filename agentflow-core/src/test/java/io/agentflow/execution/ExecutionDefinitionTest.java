package io.agentflow.execution;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExecutionDefinitionTest {

    @Test
    void shouldCreateDefinition() {
        ExecutionDefinition definition =
                new ExecutionDefinition(
                        "assistant",
                        "You are helpful.",
                        "Hello",
                        List.of());

        assertEquals("assistant", definition.agentName());
        assertEquals(
                "You are helpful.",
                definition.systemPrompt());
        assertEquals("Hello", definition.input());
    }

    @Test
    void shouldUseValueEquality() {
        ExecutionDefinition first =
                new ExecutionDefinition(
                        "assistant",
                        "You are helpful.",
                        "Hello",
                        List.of());

        ExecutionDefinition second =
                new ExecutionDefinition(
                        "assistant",
                        "You are helpful.",
                        "Hello",
                        List.of());

        assertEquals(first, second);
    }

    @Test
    void shouldRejectBlankInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ExecutionDefinition(
                        "assistant",
                        "You are helpful.",
                        "  ",
                        List.of()));

        assertEquals(
                "input must not be blank",
                exception.getMessage());
    }
}
