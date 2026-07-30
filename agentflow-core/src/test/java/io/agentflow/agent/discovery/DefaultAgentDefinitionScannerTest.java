package io.agentflow.agent.discovery;

import io.agentflow.agent.Agent;
import io.agentflow.agent.annotation.AgentDefinition;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultAgentDefinitionScannerTest {
    @AgentDefinition(
            name = "assistant",
            systemPrompt = "You are helpful"
    )
    static class AssistantAgent {

    }

    @Test
    void shouldCreateAgentFromAnnotation() {

        AgentDefinitionScanner scanner =
                new DefaultAgentDefinitionScanner();

        List<Agent> agents =
                List.copyOf(
                        scanner.scan(
                                List.of(AssistantAgent.class)));

        assertEquals(1, agents.size());
        Agent agent = agents.get(0);
        assertEquals(
                "assistant",
                agent.name());
        assertEquals(
                "You are helpful",
                agent.systemPrompt());
    }

}