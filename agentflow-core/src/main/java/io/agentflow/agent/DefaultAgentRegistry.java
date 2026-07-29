package io.agentflow.agent;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultAgentRegistry implements AgentRegistry {

    private final Map<String, Agent> agents = new ConcurrentHashMap<>();

    @Override
    public void register(Agent agent) {
        agents.put(agent.name(), agent);
    }

    @Override
    public Agent get(String id) {
        Agent agent = agents.get(id);
        if(agent == null) {
            throw new IllegalArgumentException("agent not found: " + id);
        }
        return agent;
    }

    @Override
    public Collection<Agent> list() {
        return agents.values();
    }
}
