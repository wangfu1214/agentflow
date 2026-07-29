package io.agentflow.agent;

import java.util.Collection;

public interface AgentRegistry {

    void register(Agent agentDefinition);

    Agent get(String id);

    Collection<Agent> list();
}
