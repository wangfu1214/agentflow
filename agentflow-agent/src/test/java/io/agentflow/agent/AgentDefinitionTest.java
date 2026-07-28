package io.agentflow.agent;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AgentDefinitionTest {

    @Test
    void shouldCreateAgentDefinition() {
        AgentDefinition definition =
                new AgentDefinition("customer-service",
                        "Customer Service",
                        "Handle customer questions",
                        "You are a customer service agent",
                        List.of("order-query", "refund"));

        assertEquals("customer-service", definition.id());
        assertEquals("Customer Service", definition.name());
        assertEquals("Handle customer questions", definition.description());
        assertEquals("You are a customer service agent", definition.systemPrompt());
        assertEquals(List.of("order-query", "refund"), definition.requiredTools());
    }

    @Test
    void shouldRejectBlankId() {

        assertThrows(
                IllegalArgumentException.class,
                () -> new AgentDefinition(
                        "",
                        "name",
                        "description",
                        "prompt",
                        List.of()
                ));
    }



    @Test
    void shouldKeepRequiredToolsImmutable() {
        AgentDefinition definition =
                new AgentDefinition(
                        "agent-001",
                        "agent",
                        "description",
                        "prompt",
                        List.of("tool-a"));
        assertThrows(
                UnsupportedOperationException.class,
                () -> definition.requiredTools().add("tool-b"));
    }

}