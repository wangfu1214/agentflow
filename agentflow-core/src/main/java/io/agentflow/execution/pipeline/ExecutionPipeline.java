package io.agentflow.execution.pipeline;

import io.agentflow.execution.ExecutionContext;

public interface ExecutionPipeline {

    void execute(ExecutionContext context);
}
