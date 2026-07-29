package io.agentflow.client;

import io.agentflow.agent.Agent;
import io.agentflow.agent.AgentExecutionFactory;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionEngine;
import io.agentflow.execution.ExecutionResult;

import java.util.Objects;

public class DefaultAgentFlow implements AgentFlow {

    private final AgentRegistry registry;

    private final AgentExecutionFactory factory;

    private final ExecutionEngine engine;

    public DefaultAgentFlow(AgentRegistry registry, AgentExecutionFactory factory, ExecutionEngine engine) {
        this.registry = Objects.requireNonNull(registry);
        this.factory = Objects.requireNonNull(factory);
        this.engine = Objects.requireNonNull(engine);
    }

    @Override
    public AgentResponse execute(String agentName, AgentRequest request) {
        Agent agent = registry.get(agentName);
        Execution execution = factory.create(agent, request);
        ExecutionResult result = engine.execute(execution);
        return AgentResponse.of(execution.id(), result.content());
    }
}
