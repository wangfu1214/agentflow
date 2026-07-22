package io.agentflow.execution;

import io.agentflow.client.AgentRequest;

public interface ExecutionContext {

    Execution getExecution();

    ExecutionStatus getStatus();

    void setStatus(ExecutionStatus state);

    Object get(String key);

    void put(String key, Object value);
}
