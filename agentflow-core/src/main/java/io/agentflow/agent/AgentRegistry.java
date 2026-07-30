package io.agentflow.agent;

import java.util.Collection;

public interface AgentRegistry {

    void register(Agent agent);

    Agent get(String id);

    Collection<Agent> list();
}
