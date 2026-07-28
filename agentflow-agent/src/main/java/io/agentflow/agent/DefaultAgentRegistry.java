package io.agentflow.agent;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultAgentRegistry implements AgentRegistry {

    private final Map<String, AgentDefinition> agents = new ConcurrentHashMap<>();

    @Override
    public void register(AgentDefinition agentDefinition) {
        agents.put(agentDefinition.id(), agentDefinition);
    }

    @Override
    public AgentDefinition get(String id) {
        AgentDefinition definition = agents.get(id);
        if(definition == null) {
            throw new IllegalArgumentException("agent not found: " + id);
        }
        return definition;
    }

    @Override
    public Collection<AgentDefinition> list() {
        return agents.values();
    }
}
