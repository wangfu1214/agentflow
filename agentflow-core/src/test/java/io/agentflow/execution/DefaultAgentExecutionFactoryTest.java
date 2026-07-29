package io.agentflow.execution;

import io.agentflow.client.DefaultExecutionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultAgentExecutionFactoryTest {

    @Test
    public void shouldCreateExecutionFromExecutionDefinition(){

        ExecutionFactory factory = new DefaultExecutionFactory(new UuidExecutionIdGenerator());

        ExecutionDefinition definition = new ExecutionDefinition(
                "agent-1", "test", "hello");

        Execution execution =
                factory.create(definition);

        assertEquals(
                definition,
                execution.definition()
        );
    }
}
