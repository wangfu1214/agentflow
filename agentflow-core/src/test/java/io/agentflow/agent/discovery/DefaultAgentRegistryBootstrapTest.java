package io.agentflow.agent.discovery;


import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.annotation.AgentDefinition;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DefaultAgentRegistryBootstrapTest {

    @AgentDefinition(
            name = "assistant",
            systemPrompt = "You are helpful"
    )
    static class AssistantAgent {

    }

    @Test
    void shouldCreateRegistryFromAgentClasses() {

        AgentDefinitionScanner scanner = new DefaultAgentDefinitionScanner();

        AgentDefinitionRegistrar registrar = new DefaultAgentDefinitionRegistrar(scanner);

        AgentRegistryBootstrap bootstrap = new DefaultAgentRegistryBootstrap(registrar);

        AgentRegistry registry =
                bootstrap.initialize(
                        List.of(AssistantAgent.class));

        Agent agent = registry.get("assistant");
        assertEquals("assistant", agent.name());
        assertEquals("You are helpful", agent.systemPrompt());
    }

}