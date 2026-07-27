package io.agentflow.tool;

import java.util.Map;

/**
 * Executes a tool.
 */
@FunctionalInterface
public interface ToolInvoker {

    Object invoke(
            ToolDefinition definition,
            Map<String,Object> arguments
    );
}
