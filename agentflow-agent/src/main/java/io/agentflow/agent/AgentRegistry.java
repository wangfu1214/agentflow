package io.agentflow.agent;

import java.util.Collection;

public interface AgentRegistry {

    void register(AgentDefinition agentDefinition);

    AgentDefinition get(String id);

    Collection<AgentDefinition> list();
}
