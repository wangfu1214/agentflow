package io.agentflow.execution;

import java.util.UUID;

/**
 * Generates execution identifiers using UUID.
 */
public class UuidExecutionIdGenerator implements ExecutionIdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
