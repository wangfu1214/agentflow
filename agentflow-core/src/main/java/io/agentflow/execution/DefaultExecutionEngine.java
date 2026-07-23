package io.agentflow.execution;

import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;

import java.util.Objects;

/**
 * Default execution engine that invokes one model and drives
 * the basic execution lifecycle.
 */
public class DefaultExecutionEngine implements ExecutionEngine {

    private final ExecutionPipeline pipeline;

    private final ExecutionResultHandler resultHandler;

    private final ExecutionContextFactory contextFactory;

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler
    ) {
        this(pipeline, resultHandler, new DefaultExecutionContextFactory());
    }

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler,
            ExecutionContextFactory contextFactory
    ) {
        this.pipeline = pipeline;
        this.resultHandler = resultHandler;
        this.contextFactory = contextFactory;
    }

    @Override
    public ExecutionResult execute(
            Execution execution
    ) {
        Objects.requireNonNull(
                execution,
                "execution must not be null"
        );

        execution.start();

        try {
            ExecutionContext context = contextFactory.create(execution);

            pipeline.execute(context);

            ExecutionResult result = resultHandler.handler(context);

            execution.succeed();

            return result;
        } catch (RuntimeException exception) {
            execution.fail();
            throw exception;
        }
    }
}
