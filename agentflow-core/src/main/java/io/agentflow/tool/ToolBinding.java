package io.agentflow.tool;

import java.util.Objects;

public final class ToolBinding {

    private final ToolDefinition definition;

    private final ToolInvoker invoker;


    public ToolBinding(ToolDefinition definition, ToolInvoker invoker) {
        this.definition =
                Objects.requireNonNull(definition, "definition must not be null");
        this.invoker =
                Objects.requireNonNull(invoker, "invoker must not be null");
    }

    public ToolDefinition definition() {
        return definition;
    }

    public ToolInvoker invoker() {
        return invoker;
    }
}
