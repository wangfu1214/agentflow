package io.agentflow.execution;

import java.util.Objects;

/**
 * Result produced by a successfully completed execution.
 *
 * @param content textual execution output
 */
public record ExecutionResult(String content) {

    public ExecutionResult {
        Objects.requireNonNull(
                content,
                "content must not be null"
        );
    }

    public static ExecutionResult of(String content) {
        return new ExecutionResult(content);
    }
}
