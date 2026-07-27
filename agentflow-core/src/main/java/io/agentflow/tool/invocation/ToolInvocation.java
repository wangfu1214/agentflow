package io.agentflow.tool.invocation;

import io.agentflow.execution.invocation.InvocationType;
import io.agentflow.execution.invocation.RuntimeInvocation;
import io.agentflow.execution.invocation.RuntimeInvocationStatus;

import java.util.Map;
import java.util.Objects;


/**
 * Runtime entity representing one tool invocation.
 */
public final class ToolInvocation implements RuntimeInvocation {


    private final String id;


    private final String executionId;


    private final String toolName;


    private final Map<String,Object> arguments;


    private RuntimeInvocationStatus status;


    private Object result;


    private Exception failure;



    public ToolInvocation(String id, String executionId, String toolName, Map<String,Object> arguments) {
        this.id = requireNotBlank(id, "id");
        this.executionId = requireNotBlank(executionId, "executionId");
        this.toolName = requireNotBlank(toolName, "toolName");
        this.arguments = Map.copyOf(
                Objects.requireNonNull(arguments, "arguments must not be null"));
        this.status = RuntimeInvocationStatus.CREATED;
    }

    public String id() {
        return id;
    }

    public String executionId() {
        return executionId;
    }

    @Override
    public InvocationType type() {
        return InvocationType.TOOL;
    }

    public String toolName() {
        return toolName;
    }

    public Map<String,Object> arguments() {
        return arguments;
    }

    @Override
    public RuntimeInvocationStatus status() {
        return status;
    }

    public Object result() {
        return result;
    }

    public Exception failure() {
        return failure;
    }

    @Override
    public void start() {

        requireStatus(RuntimeInvocationStatus.CREATED);

        status = RuntimeInvocationStatus.RUNNING;
    }

    @Override
    public void succeed(Object result) {

        requireStatus(RuntimeInvocationStatus.RUNNING);

        this.result = result;

        this.status = RuntimeInvocationStatus.SUCCEEDED;
    }

    @Override
    public void fail(Exception failure) {

        requireStatus(RuntimeInvocationStatus.RUNNING);
        this.failure =
                Objects.requireNonNull(failure,
                        "failure must not be null");
        this.status = RuntimeInvocationStatus.FAILED;
    }

    private void requireStatus(RuntimeInvocationStatus expected) {

        if(status != expected){
            throw new IllegalStateException(
                    "expected tool invocation status "
                            + expected
                            + " but was "
                            + status);
        }
    }

    private static String requireNotBlank(String value, String fieldName) {

        Objects.requireNonNull(value, fieldName + " must not be null");

        if(value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank");
        }
        return value;
    }
}