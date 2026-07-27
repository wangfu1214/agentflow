package io.agentflow.execution;

import io.agentflow.execution.environment.ExecutionEnvironment;
import io.agentflow.execution.lifecycle.ExecutionLifecycleCoordinator;

import java.util.Objects;

/**
 * Default execution engine that invokes one model and drives
 * the basic execution lifecycle.
 */
public class DefaultExecutionEngine implements ExecutionEngine {

    private final ExecutionEnvironment environment;

    public DefaultExecutionEngine(ExecutionEnvironment environment) {
        this.environment =
                Objects.requireNonNull(
                        environment,
                        "environment must not be null"
                );
    }

    @Override
    public ExecutionResult execute(Execution execution) {
        Objects.requireNonNull(
                execution,
                "execution must not be null");

        ExecutionContext context = environment.contextFactory().create(execution);

        ExecutionLifecycleCoordinator coordinator = new ExecutionLifecycleCoordinator(
                environment.lifecycle(),environment.interceptorChain(),
                environment.pipeline(),environment.resultHandler());

        return coordinator.execute(execution, context);
    }
}
