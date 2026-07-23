package io.agentflow.execution.pipeline;

import io.agentflow.execution.ExecutionContext;

public interface ExecutionStep {

    void execute(ExecutionContext context);
}
