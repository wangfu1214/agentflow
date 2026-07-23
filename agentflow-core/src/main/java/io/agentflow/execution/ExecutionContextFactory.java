package io.agentflow.execution;

public interface ExecutionContextFactory {

    ExecutionContext create(Execution execution);
}
