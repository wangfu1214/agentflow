package io.agentflow.execution;

import io.agentflow.execution.pipeline.ExecutionPipeline;
import io.agentflow.execution.result.ExecutionResultHandler;
import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;

import java.util.Objects;

/**
 * Default execution engine that invokes one model and drives
 * the basic execution lifecycle.
 */
public class DefaultExecutionEngine implements ExecutionEngine {

    private final ExecutionPipeline pipeline;

    private final ExecutionResultHandler resultHandler;

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline,
            ExecutionResultHandler resultHandler
    ) {
        this.pipeline = pipeline;
        this.resultHandler = resultHandler;
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
            ExecutionContext context = new DefaultExecutionContext(execution);

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
