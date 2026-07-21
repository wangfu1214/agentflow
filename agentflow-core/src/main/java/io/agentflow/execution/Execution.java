package io.agentflow.execution;

import java.util.Objects;

/**
 * Runtime state of one agent execution.
 */
public final class Execution {

    private final String id;
    private final ExecutionDefinition definition;

    private ExecutionStatus status;

    public Execution(
            String id,
            ExecutionDefinition definition
    ) {
        this.id = requireNotBlank(id, "id");
        this.definition = Objects.requireNonNull(
                definition,
                "definition must not be null"
        );
        this.status = ExecutionStatus.CREATED;
    }

    public String id() {
        return id;
    }

    public ExecutionDefinition definition() {
        return definition;
    }

    public ExecutionStatus status() {
        return status;
    }

    public void start() {
        requireStatus(ExecutionStatus.CREATED);
        status = ExecutionStatus.RUNNING;
    }

    public void succeed() {
        requireStatus(ExecutionStatus.RUNNING);
        status = ExecutionStatus.SUCCEEDED;
    }

    public void fail() {
        requireStatus(ExecutionStatus.RUNNING);
        status = ExecutionStatus.FAILED;
    }

    public void cancel() {
        if (status != ExecutionStatus.CREATED
                && status != ExecutionStatus.RUNNING) {
            throw new IllegalStateException(
                    "execution cannot be cancelled from status " + status
            );
        }

        status = ExecutionStatus.CANCELLED;
    }

    private void requireStatus(ExecutionStatus expected) {
        if (status != expected) {
            throw new IllegalStateException(
                    "expected execution status "
                            + expected
                            + " but was "
                            + status
            );
        }
    }

    private static String requireNotBlank(
            String value,
            String fieldName
    ) {
        Objects.requireNonNull(
                value,
                fieldName + " must not be null"
        );

        if (value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );
        }

        return value;
    }
}
