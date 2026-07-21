package io.agentflow.client;

import io.agentflow.agent.Agent;
import io.agentflow.execution.Execution;
import io.agentflow.execution.ExecutionDefinition;
import io.agentflow.execution.ExecutionFactory;
import io.agentflow.execution.ExecutionIdGenerator;

import java.util.Objects;

/**
 * Default application-layer factory that converts public API models
 * into an execution snapshot and runtime execution.
 */
public final class DefaultExecutionFactory implements ExecutionFactory {
    private final ExecutionIdGenerator idGenerator;

    public DefaultExecutionFactory(
            ExecutionIdGenerator idGenerator
    ) {
        this.idGenerator = Objects.requireNonNull(
                idGenerator,
                "idGenerator must not be null"
        );
    }

    @Override
    public Execution create(
            Agent agent,
            AgentRequest request
    ) {
        Objects.requireNonNull(
                agent,
                "agent must not be null"
        );
        Objects.requireNonNull(
                request,
                "request must not be null"
        );

        ExecutionDefinition definition =
                new ExecutionDefinition(
                        agent.name(),
                        agent.systemPrompt(),
                        request.input()
                );

        return new Execution(
                idGenerator.generate(),
                definition
        );
    }
}
