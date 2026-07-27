package io.agentflow.tool.invocation;

@FunctionalInterface
public interface ToolInvocationIdGenerator {

    String generate();
}
