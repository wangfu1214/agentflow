package io.agentflow.agent;

import io.agentflow.tool.ToolBinding;

import java.util.List;

public interface AgentToolResolver {

    List<ToolBinding> resolve(Agent agent);
}
