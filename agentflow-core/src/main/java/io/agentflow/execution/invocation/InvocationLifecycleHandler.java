package io.agentflow.execution.invocation;

public interface InvocationLifecycleHandler {

    void start(RuntimeInvocation invocation);

    void succeed(RuntimeInvocation invocation, Object result);

    void fail(RuntimeInvocation invocation, RuntimeException exception);
}
