package io.agentflow.agent;

import io.agentflow.client.AgentRequest;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.execution.ExecutionFactory;

import java.util.List;
import java.util.Objects;

public final class DefaultAgentExecutionFactory implements AgentExecutionFactory {

    private final ExecutionFactory factory;

    public DefaultAgentExecutionFactory(ExecutionFactory factory) {
        this.factory =
                Objects.requireNonNull(factory, "executionFactory must not be null");
    }

    @Override
    public Execution create(Agent agent, AgentRequest request) {
        Objects.requireNonNull(agent, "agent must not be null");
        Objects.requireNonNull(request, "request must not be null");
        ExecutionDefinition definition =
                new ExecutionDefinition(
                        agent.name(),
                        agent.systemPrompt(),
                        request.input(),
                        agent.requiredTools());
        return factory.create(definition);
    }
}
