package io.agentflow.model.invocation;

import java.util.UUID;

/**
 * Generates model invocation identifiers using UUID.
 */
public class UuidModelInvocationIdGenerator implements ModelInvocationIdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
