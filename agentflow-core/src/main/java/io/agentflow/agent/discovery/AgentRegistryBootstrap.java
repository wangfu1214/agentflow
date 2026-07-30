package io.agentflow.agent.discovery;

import io.agentflow.agent.AgentRegistry;

import java.util.Collection;

public interface AgentRegistryBootstrap {

    AgentRegistry initialize(Collection<Class<?>> agentClasses);
}
