package io.agentflow.model.invocation;

/**
 * Generates identifiers for model invocations.
 */
@FunctionalInterface
public interface ModelInvocationIdGenerator {

    String generate();
}
