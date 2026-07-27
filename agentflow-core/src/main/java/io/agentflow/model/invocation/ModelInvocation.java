package io.agentflow.model.invocation;

import io.agentflow.execution.invocation.InvocationType;
import io.agentflow.execution.invocation.RuntimeInvocation;
import io.agentflow.execution.invocation.RuntimeInvocationStatus;
import io.agentflow.model.ModelRequest;
import io.agentflow.model.ModelResponse;

import java.util.Objects;

/**
 * Runtime entity representing one model invocation
 * within an execution.
 */
public final class ModelInvocation implements RuntimeInvocation {

    private final String id;

    private final String executionId;

    private final ModelRequest request;

    private RuntimeInvocationStatus status;

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
        this.status = RuntimeInvocationStatus.CREATED;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String executionId() {
        return executionId;
    }

    public ModelRequest request() {
        return request;
    }

    @Override
    public RuntimeInvocationStatus status() {
        return status;
    }

    public ModelResponse response() {
        return response;
    }

    public RuntimeException failure() {
        return failure;
    }

    public void start() {
        requireStatus(RuntimeInvocationStatus.CREATED);
        status = RuntimeInvocationStatus.RUNNING;
    }

    @Override
    public InvocationType type(){

        return InvocationType.MODEL;

    }

    public void succeed(ModelResponse response) {
        requireStatus(RuntimeInvocationStatus.RUNNING);

        this.response = Objects.requireNonNull(
                response,
                "response must not be null"
        );
        this.status = RuntimeInvocationStatus.SUCCEEDED;
    }

    public void fail(RuntimeException failure) {
        requireStatus(RuntimeInvocationStatus.RUNNING);

        this.failure = Objects.requireNonNull(
                failure,
                "failure must not be null"
        );
        this.status = RuntimeInvocationStatus.FAILED;
    }

    private void requireStatus(
            RuntimeInvocationStatus expected
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