package io.agentflow.client;

import io.agentflow.agent.AgentExecutionFactory;
import io.agentflow.agent.AgentRegistry;
import io.agentflow.execution.ExecutionEngine;

import java.util.Objects;

public class DefaultAgentFlowFactory implements AgentFlowFactory {

    private final AgentRegistry registry;

    private final AgentExecutionFactory executionFactory;

    private final ExecutionEngine engine;

    public DefaultAgentFlowFactory(AgentRegistry registry, AgentExecutionFactory factory, ExecutionEngine engine) {
        this.registry = Objects.requireNonNull(registry, "registry must not be null");
        this.executionFactory = Objects.requireNonNull(factory, "factory must not be null");
        this.engine = Objects.requireNonNull(engine, "engine must not be null");
    }

    @Override
    public AgentFlow create() {
        return new DefaultAgentFlow(registry, executionFactory, engine);
    }
}
