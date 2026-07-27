package io.agentflow.execution.result;

import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.execution.invocation.RuntimeInvocation;
import io.agentflow.model.ModelResponse;
import io.agentflow.model.invocation.ModelInvocation;

import java.util.Objects;

/**
 * Builds the final execution result from the completed
 * model invocation.
 */
public final class DefaultExecutionResultHandler
        implements ExecutionResultHandler {

    @Override
    public ExecutionResult handle(ExecutionContext context) {
        Objects.requireNonNull(context, "context must not be null");

        RuntimeInvocation invocation;

        try {

            invocation =
                    context.record()
                            .lastInvocation();

        } catch (IllegalStateException exception) {

            throw new IllegalStateException(
                    "modelInvocation not found",
                    exception
            );

        }

        if (!(invocation instanceof ModelInvocation modelInvocation)) {

            throw new IllegalStateException(
                    "last invocation is not model invocation"
            );

        }


        ModelResponse response =
                modelInvocation.response();


        return ExecutionResult.of(
                response.content()
        );
    }
}