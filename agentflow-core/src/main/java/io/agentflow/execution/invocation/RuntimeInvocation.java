package io.agentflow.execution.invocation;


/**
 * Common abstraction for one runtime invocation.
 *
 * It represents one operation executed
 * during an execution.
 */
public interface RuntimeInvocation {


    /**
     * Invocation identifier.
     */
    String id();



    /**
     * Execution identifier that owns this invocation.
     */
    String executionId();



    /**
     * Invocation type.
     */
    InvocationType type();



    /**
     * Current invocation lifecycle status.
     */
    RuntimeInvocationStatus status();

}