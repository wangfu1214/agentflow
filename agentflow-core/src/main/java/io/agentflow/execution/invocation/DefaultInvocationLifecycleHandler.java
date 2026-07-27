package io.agentflow.execution.invocation;

import io.agentflow.model.invocation.ModelInvocation;
import io.agentflow.tool.invocation.ToolInvocation;

import java.util.Objects;

public class DefaultInvocationLifecycleHandler implements InvocationLifecycleHandler {

    @Override
    public void start(RuntimeInvocation invocation) {
        Objects.requireNonNull(invocation, "invocation must not be null");
        if(invocation instanceof ModelInvocation modelInvocation){
            modelInvocation.start();
            return;
        }
        if(invocation instanceof ToolInvocation toolInvocation){
            toolInvocation.start();
            return;
        }
        throw new IllegalArgumentException("unsupported invocation type: " + invocation.getClass());
    }

    @Override
    public void succeed(RuntimeInvocation invocation, Object result) {
        Objects.requireNonNull(invocation, "invocation must not be null");
        if(invocation instanceof ModelInvocation modelInvocation){
            modelInvocation.succeed(
                    (io.agentflow.model.ModelResponse) result);
            return;
        }
        if(invocation instanceof ToolInvocation toolInvocation){
            toolInvocation.succeed(result);
            return;
        }
        throw new IllegalArgumentException("unsupported invocation type: " + invocation.getClass());
    }

    @Override
    public void fail(RuntimeInvocation invocation, RuntimeException exception) {
        Objects.requireNonNull(invocation, "invocation must not be null");
        if(invocation instanceof ModelInvocation modelInvocation){
            modelInvocation.fail(exception);
            return;
        }
        if(invocation instanceof ToolInvocation toolInvocation){
            toolInvocation.fail(exception);
            return;
        }
        throw new IllegalArgumentException(
                "unsupported invocation type: " + invocation.getClass());
    }
}
