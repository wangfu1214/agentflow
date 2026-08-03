package io.agentflow.agent;

import io.agentflow.tool.ToolBinding;
import io.agentflow.tool.ToolRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultAgentToolResolver implements AgentToolResolver {

    private final ToolRegistry toolRegistry;

    public DefaultAgentToolResolver(ToolRegistry toolRegistry) {
        this.toolRegistry = Objects.requireNonNull(toolRegistry, "toolRegistry must not be null");
    }

    @Override
    public List<ToolBinding> resolve(Agent agent) {
        Objects.requireNonNull(agent, "agent must not be null");
        List<ToolBinding> bindings = new ArrayList<>();
        for (String tool : agent.requiredTools()) {
            ToolBinding binding = toolRegistry.get(tool);
            bindings.add(binding);
        }
        return List.copyOf(bindings);
    }
}
