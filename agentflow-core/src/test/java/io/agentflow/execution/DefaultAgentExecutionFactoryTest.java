package io.agentflow.execution;

import io.agentflow.agent.Agent;
import io.agentflow.client.DefaultExecutionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultAgentExecutionFactoryTest {

    @Test
    public void shouldCreateExecutionFromExecutionDefinition(){

        ExecutionFactory factory = new DefaultExecutionFactory(new UuidExecutionIdGenerator());

        Agent agent =
                Agent.builder()
                        .name("assistant")
                        .systemPrompt("help")
                        .requiredTools(List.of("order-query"))
                        .build();

        ExecutionDefinition definition = new ExecutionDefinition(
                "agent-1", "test", "hello", agent.requiredTools());

        Execution execution =
                factory.create(definition);

        assertEquals(
                definition,
                execution.definition()
        );
    }

    @Test
    void shouldSnapshotRequiredTools() {
        Agent agent =
                Agent.builder()
                        .name("assistant")
                        .systemPrompt("help")
                        .requiredTools(List.of("order-query"))
                        .build();
        ExecutionFactory factory = new DefaultExecutionFactory(new UuidExecutionIdGenerator());
        ExecutionDefinition definition = new ExecutionDefinition(
                "agent-1", "test", "hello", agent.requiredTools());
        Execution execution = factory.create(definition);
        assertEquals(
                agent.requiredTools(),
                execution.definition().requiredTools());
    }
}
