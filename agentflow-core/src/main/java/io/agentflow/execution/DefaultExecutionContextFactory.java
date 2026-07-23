package io.agentflow.execution;

public class DefaultExecutionContextFactory implements ExecutionContextFactory {

    @Override
    public ExecutionContext create(Execution execution) {
        return new DefaultExecutionContext(execution);
    }
}
