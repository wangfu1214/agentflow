package io.agentflow.agent;


import org.junit.jupiter.api.Test;


import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class DefaultAgentRegistryTest {


    private AgentDefinition createDefinition() {

        return new AgentDefinition(
                "agent-001",
                "Test Agent",
                "test agent",
                "system prompt",
                List.of());
    }


    @Test
    void shouldRegisterAndGetAgent() {

        AgentRegistry registry =
                new DefaultAgentRegistry();

        AgentDefinition definition = createDefinition();

        registry.register(definition);

        AgentDefinition result =
                registry.get("agent-001");

        assertEquals(definition, result);
    }


    @Test
    void shouldListAgents() {
        AgentRegistry registry =
                new DefaultAgentRegistry();

        registry.register(createDefinition());

        assertEquals(1, registry.list().size());
    }

    @Test
    void shouldRejectUnknownAgent() {

        AgentRegistry registry =
                new DefaultAgentRegistry();

        assertThrows(
                IllegalArgumentException.class,
                () -> registry.get("unknown"));
    }

}