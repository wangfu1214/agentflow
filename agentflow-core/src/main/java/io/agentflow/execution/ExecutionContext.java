package io.agentflow.execution;

import io.agentflow.execution.record.ExecutionRecord;

public interface ExecutionContext {

    Execution getExecution();

    ExecutionStatus getStatus();

    void setStatus(ExecutionStatus state);

    ExecutionRecord record();

    Object get(String key);

    void put(String key, Object value);
}
