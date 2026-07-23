package io.agentflow.execution;

import io.agentflow.execution.pipeline.ExecutionPipeline;
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

    public DefaultExecutionEngine(
            ExecutionPipeline pipeline
    ) {
        this.pipeline = pipeline;
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
            ModelResponse response = (ModelResponse) context.get("response");
            if (response == null) {
                throw new IllegalStateException(
                        "modelInvoker returned null response"
                );
            }

            execution.succeed();

            return ExecutionResult.of(response.content());
        } catch (RuntimeException exception) {
            execution.fail();
            throw exception;
        }
    }
}
