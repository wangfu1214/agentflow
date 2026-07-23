package io.agentflow.execution.result;

import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionResult;

public interface ExecutionResultHandler {

    ExecutionResult handle(ExecutionContext executionContext);
}
