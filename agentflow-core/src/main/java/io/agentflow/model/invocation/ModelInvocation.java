package io.agentflow.model.invocation;

import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;

import java.util.Objects;

/**
 * Runtime entity representing one model invocation
 * within an execution.
 */
public final class ModelInvocation {

    private final String id;

    private final String executionId;

    private final ModelRequest request;

    private ModelInvocationStatus status;

    private ModelResponse response;

    private RuntimeException failure;

    public ModelInvocation(
            String id,
            String executionId,
            ModelRequest request
    ) {
        this.id = requireNotBlank(id, "id");
        this.executionId = requireNotBlank(
                executionId,
                "executionId"
        );
        this.request = Objects.requireNonNull(
                request,
                "request must not be null"
        );
        this.status = ModelInvocationStatus.CREATED;
    }

    public String id() {
        return id;
    }

    public String executionId() {
        return executionId;
    }

    public ModelRequest request() {
        return request;
    }

    public ModelInvocationStatus status() {
        return status;
    }

    public ModelResponse response() {
        return response;
    }

    public RuntimeException failure() {
        return failure;
    }

    public void start() {
        requireStatus(ModelInvocationStatus.CREATED);
        status = ModelInvocationStatus.RUNNING;
    }

    public void succeed(ModelResponse response) {
        requireStatus(ModelInvocationStatus.RUNNING);

        this.response = Objects.requireNonNull(
                response,
                "response must not be null"
        );
        this.status = ModelInvocationStatus.SUCCEEDED;
    }

    public void fail(RuntimeException failure) {
        requireStatus(ModelInvocationStatus.RUNNING);

        this.failure = Objects.requireNonNull(
                failure,
                "failure must not be null"
        );
        this.status = ModelInvocationStatus.FAILED;
    }

    private void requireStatus(
            ModelInvocationStatus expected
    ) {
        if (status != expected) {
            throw new IllegalStateException(
                    "expected model invocation status "
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