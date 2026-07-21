package io.agentflow.ai.agent.resolver;

import io.agentflow.ai.agent.Agent;
import io.agentflow.ai.agent.registry.AgentRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgentResolver {

    private final AgentRegistry agentRegistry;

    public Agent resolve(String name) {
        Agent agent = agentRegistry.get(name);

        if (agent == null) {
            throw new IllegalStateException("Agent not found: " + name);
        }

        if (!agent.isEnabled()) {
            throw new IllegalStateException("Agent is disable: " + name);
        }
        return agent;
    }
}
