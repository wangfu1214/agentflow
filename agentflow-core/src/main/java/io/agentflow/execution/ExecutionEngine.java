package io.agentflow.execution;

/**
 * Drives the lifecycle of an execution.
 */
public interface ExecutionEngine {

    ExecutionResult execute(Execution execution);
}