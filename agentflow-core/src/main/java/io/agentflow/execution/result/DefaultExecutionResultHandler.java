package io.agentflow.execution.result;

import io.agentflow.execution.ExecutionContext;
import io.agentflow.execution.ExecutionResult;
import io.agentflow.execution.invocation.RuntimeInvocation;
import io.agentflow.model.ModelResponse;
import io.agentflow.model.invocation.ModelInvocation;
import io.agentflow.tool.invocation.ToolInvocation;

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
            throw new IllegalStateException("modelInvocation not found", exception);
        }

        if (invocation instanceof ModelInvocation modelInvocation) {
            ModelResponse response =
                    Objects.requireNonNull(
                            modelInvocation.response(), "model response must not be null");
            return ExecutionResult.of(response.content());
        }
        if (invocation instanceof ToolInvocation toolInvocation) {
            Object result =
                    Objects.requireNonNull(
                            toolInvocation.result(), "tool result must not be null");
            return ExecutionResult.of(String.valueOf(result));
        }

        throw new IllegalStateException(
                "unsupported invocation type: " + invocation.getClass());
    }
}