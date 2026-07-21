package io.agentflow.execution;


/**
 * Generates identifiers for new executions.
 */
@FunctionalInterface
public interface ExecutionIdGenerator {

    String generate();
}
