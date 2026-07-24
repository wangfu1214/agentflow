package io.agentflow.execution.result;

import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.model.ModelResponse;
import io.agentflow.model.invocation.ModelInvocation;
import io.agentflow.model.invocation.ModelInvocationStatus;

import java.util.Objects;

/**
 * Builds the final execution result from the completed
 * model invocation.
 */
public final class DefaultExecutionResultHandler
        implements ExecutionResultHandler {

    @Override
    public ExecutionResult handle(
            ExecutionContext context
    ) {
        Objects.requireNonNull(
                context,
                "context must not be null"
        );

        ModelInvocation invocation;

        try {

            invocation =
                    context.record()
                            .lastModelInvocation();

        } catch (IllegalStateException exception) {

            throw new IllegalStateException(
                    "modelInvocation not found",
                    exception
            );

        }

        if (invocation.status()
                != ModelInvocationStatus.SUCCEEDED) {
            throw new IllegalStateException(
                    "modelInvocation has not succeeded"
            );
        }

        ModelResponse response =
                invocation.response();

        return ExecutionResult.of(
                response.content()
        );
    }
}