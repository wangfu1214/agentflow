package io.agentflow.agent.annotation;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


class AgentDefinitionTest {

    @AgentDefinition(
            name = "assistant",
            systemPrompt = "You are helpful")
    static class AssistantAgent {

    }

    @Test
    void shouldReadAgentDefinitionAnnotation() {
        AgentDefinition annotation =
                AssistantAgent.class
                        .getAnnotation(
                                AgentDefinition.class);
        assertNotNull(annotation);
        assertEquals(
                "assistant",
                annotation.name());
        assertEquals(
                "You are helpful",
                annotation.systemPrompt());
    }

}