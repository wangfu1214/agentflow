package io.agentflow.agent;

import io.agentflow.client.AgentRequest;
import io.agentflow.execution.Execution;

public interface AgentExecutionFactory {

    Execution create(Agent agent, AgentRequest request);
}
