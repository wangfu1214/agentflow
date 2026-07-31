package io.agentflow.agent;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgentTest {

    @Test
    void shouldBuildAgent() {
        Agent agent = Agent.builder()
                .name("assistant")
                .systemPrompt("You are a helpful assistant.")
                .requiredTools(List.of("query-tool"))
                .build();

        assertEquals("assistant", agent.name());
        assertEquals(
                "You are a helpful assistant.",
                agent.systemPrompt()
        );
    }

    @Test
    void shouldRejectNullName() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> Agent.builder()
                        .systemPrompt("You are helpful.")
                        .build()
        );

        assertEquals(
                "name must not be null",
                exception.getMessage()
        );
    }

    @Test
    void shouldRejectBlankName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Agent.builder()
                        .name("   ")
                        .systemPrompt("You are helpful.")
                        .build());

        assertEquals(
                "name must not be blank",
                exception.getMessage());
    }

    @Test
    void shouldRejectNullSystemPrompt() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> Agent.builder()
                        .name("assistant")
                        .build());

        assertEquals(
                "systemPrompt must not be null",
                exception.getMessage());
    }

    @Test
    void shouldRejectBlankSystemPrompt() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Agent.builder()
                        .name("assistant")
                        .systemPrompt("   ")
                        .build());

        assertEquals(
                "systemPrompt must not be blank",
                exception.getMessage());
    }

    @Test
    void shouldPreserveOriginalValues() {
        Agent agent = Agent.builder()
                .name("  assistant  ")
                .systemPrompt(
                        """
                          You are a helpful assistant.
                          Answer clearly.
                        """)
                .requiredTools(List.of("query-tool"))
                .build();

        assertEquals("  assistant  ", agent.name());
        assertEquals(
                """
                  You are a helpful assistant.
                  Answer clearly.
                """, agent.systemPrompt());
    }

    @Test
    void shouldCreateAgentWithRequiredTools() {
        Agent agent =
                Agent.builder()
                        .name("assistant")
                        .systemPrompt("help")
                        .requiredTools(List.of("order-query"))
                        .build();
        assertEquals(
                List.of("order-query"),
                agent.requiredTools());
    }
}
