package io.agentflow.tool;

public interface ToolRegistry {

    void register(ToolBinding binding);

    ToolBinding get(String name);
}
