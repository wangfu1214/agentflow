package io.agentflow.agent.discovery;

import io.agentflow.agent.AgentRegistry;
import io.agentflow.agent.DefaultAgentRegistry;
import io.agentflow.agent.annotation.AgentDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultAgentDefinitionRegistrarTest {


    @AgentDefinition(
            name = "assistant",
            systemPrompt = "help"
    )
    static class Assistant {
    }

    @Test
    void shouldRegisterAnnotatedAgents(){

        AgentRegistry registry = new DefaultAgentRegistry();

        AgentDefinitionRegistrar registrar = new DefaultAgentDefinitionRegistrar(new DefaultAgentDefinitionScanner());

        registrar.register(
                List.of(Assistant.class),
                registry
        );

        assertEquals(
                "assistant",
                registry.get("assistant").name()
        );
    }
}
