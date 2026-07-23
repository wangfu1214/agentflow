package io.agentflow.model.invocation;

/**
 * Lifecycle status of one model invocation.
 */
public enum ModelInvocationStatus {

    CREATED,

    RUNNING,

    SUCCEEDED,

    FAILED
}