package io.agentflow.agent.discovery;

import io.agentflow.agent.AgentRegistry;

import java.util.Collection;

public interface AgentDefinitionRegistrar {

    void register(
            Collection<Class<?>> classes,
            AgentRegistry registry);
}
