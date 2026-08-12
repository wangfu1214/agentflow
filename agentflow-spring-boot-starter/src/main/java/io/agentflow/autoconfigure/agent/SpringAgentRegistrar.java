package io.agentflow.autoconfigure.agent;

import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentRegistry;

import java.util.List;

public class SpringAgentRegistrar {

    private final AgentRegistry agentRegistry;

    public SpringAgentRegistrar(AgentRegistry agentRegistry) {
        this.agentRegistry = agentRegistry;
    }

    public void register(List<Agent> agents) {
        agents.forEach(agentRegistry::register);
    }
}
