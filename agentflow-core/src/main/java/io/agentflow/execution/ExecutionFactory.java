package io.agentflow.execution;

import io.agentflow.agent.Agent;
import io.agentflow.client.AgentRequest;

/**
 * Creates an execution from a public agent definition and request.
 */
public interface ExecutionFactory {

    Execution create(ExecutionDefinition definition);
}
