package io.agentflow.execution;

import io.agentflow.model.ModelInvoker;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;

import java.util.Objects;

/**
 * Default execution engine that invokes one model and drives
 * the basic execution lifecycle.
 */
public class DefaultExecutionEngine implements ExecutionEngine {

    private final ModelInvoker modelInvoker;

    public DefaultExecutionEngine(
            ModelInvoker modelInvoker
    ) {
        this.modelInvoker = Objects.requireNonNull(
                modelInvoker,
                "modelInvoker must not be null"
        );
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
            ExecutionDefinition definition =
                    execution.definition();

            ModelRequest modelRequest =
                    new ModelRequest(
                            definition.systemPrompt(),
                            definition.input()
                    );

            ModelResponse modelResponse =
                    modelInvoker.invoke(modelRequest);

            if (modelResponse == null) {
                throw new IllegalStateException(
                        "modelInvoker returned null response"
                );
            }

            execution.succeed();

            return ExecutionResult.of(
                    modelResponse.content()
            );
        } catch (RuntimeException exception) {
            execution.fail();
            throw exception;
        }
    }
}
