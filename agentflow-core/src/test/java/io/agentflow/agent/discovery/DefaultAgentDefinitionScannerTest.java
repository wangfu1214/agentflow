package io.agentflow.agent.discovery;

import io.agentflow.agent.Agent;
import io.agentflow.agent.annotation.AgentDefinition;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultAgentDefinitionScannerTest {
    @AgentDefinition(
            name = "assistant",
            systemPrompt = "You are helpful",
            tools = {"order-query"}
    )
    static class AssistantAgent {

    }

    @Test
    void shouldCreateAgentFromAnnotation() {

        AgentDefinitionScanner scanner = new DefaultAgentDefinitionScanner();

        List<Agent> agents = List.copyOf(scanner.scan(List.of(AssistantAgent.class)));

        assertEquals(1, agents.size());
        Agent agent = agents.get(0);
        assertEquals(
                "assistant",
                agent.name());
        assertEquals(
                "You are helpful",
                agent.systemPrompt());
    }

    @Test
    void shouldScanRequiredTools() {
        Agent agent =   Agent.builder()
                .name("assistant")
                .systemPrompt("help")
                .requiredTools(List.of("order-query"))
                .build();
        assertEquals("order-query", agent.requiredTools().get(0));
    }

}