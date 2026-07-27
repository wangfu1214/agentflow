package io.agentflow.tool.invocation;

import java.util.UUID;

public class UuidToolInvocationIdGenerator implements ToolInvocationIdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
