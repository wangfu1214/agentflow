package io.agentflow.agent.discovery;

import io.agentflow.agent.Agent;

import java.util.Collection;

public interface AgentDefinitionScanner {

    Collection<Agent> scan(Collection<Class<?>> classes);
}
